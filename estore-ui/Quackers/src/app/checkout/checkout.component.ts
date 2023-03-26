import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Account } from '../account';
import { AccountService } from '../account.service';
import { NotificationService } from '../notification.service';
import { SessionService } from '../session.service';
import { Cart } from '../shopping-cart';
import { CartService } from '../shopping-cart.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReceiptComponent } from '../receipt/receipt.component';
import { CheckoutData } from './checkout-data';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent {
  private _account: Account = this.checkoutData.account;
  private _cart: Cart = this.checkoutData.cart;
  test = new FormControl('', [Validators.required, Validators.email]);

  detailForm = this.formBuilder.group({
    email: '',
    firstName: '',
    lastName: '',
    address: '',
    apartment: '',
    city: '',
    zipCode: 0,
    cardNumber: '',
    cvv: '',
    expiration: ''
  });

  constructor(public dialogRef: MatDialogRef<CheckoutComponent>,
    private cartService: CartService,
    private notificationService: NotificationService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public checkoutData: CheckoutData) { }

  onSubmit(): void {
    if (!this.detailForm.valid) {
      this.handleInvalidForm();
      //return;
    }

    this.cartService.validateCart(this._account.id).subscribe(response => {
      const status = response.status;
      const body: Cart = response.body;

      switch (status) {
        case 200:
          if (body == null) {
            this.openReceiptPrompt();
            //this.handleValidCart();
            return;
          }

          this.handleInvalidCart(body);
          break;
        case 404:
          this.notificationService.add("Please add items to your shopping cart before attempting to checkout.", 3)
          break;
        case 500:
          this.notificationService.add("Uh Oh, something went wrong. Please try again.", 3);
          break;
      }
    })

  }

  goBack(): void {
    this.dialogRef.close();
  }

  private openReceiptPrompt(): void {
    this.dialog.open(ReceiptComponent, {
      height: 'auto',
      width: 'auto',
      data: { cart: this._cart! }
    })
  }

  /**
   * Handles cart checkout
   */
  private handleValidCart(): void {
    // TODO: Make pop that gives receipt
    this.cartService.checkoutCart(this._account.id).subscribe(response => {
      const status = response.status;
      console.log(status);
      switch (status) {
        // Theoretically shouldn't be possible due to validation before allowing checkout
        case 422:
          this.notificationService.add("Some of the items in your cart were no longer available. We are unable to checkout your case.", 3);
          return;
        // Theoretically shouldn't be possible due to validation before allowing checkout
        case 404:
          this.notificationService.add("Please add items to your shopping cart before attempting to checkout.", 3);
          return;
        case 500:
          this.notificationService.add("Uh Oh, something went wrong. Please try again.", 3);
          return;
      }

      // Success (for some reason status is undefined when it is 200. I do not know why)
      this.router.navigate(['catalog']);
      this.openReceiptPrompt();
    });
  }

  /**
   * Updates the invalid cart to a valid one and sends a message to the user letting them know. Also redirects the user back to the cart page
   * @param cart The validated cart
   */
  private handleInvalidCart(cart: Cart): void {
    this.cartService.updateCart(cart).subscribe(response => {
      this.router.navigate(['cart']);

      // Send success if update was a success, error otherwise
      if (response.status == 200) {
        this.notificationService.add("Some of the items in your cart were no longer available. We have adjusted your cart to remove these items.", 3);
        return;
      }

      this.notificationService.add("Uh Oh, we were unable to remove the invalid items from your cart.", 3);
    });
  }

  /**
   * Sends an error message detailing what form controls are invalid
   */
  private handleInvalidForm(): void {
    const controls = this.detailForm.controls;

    // Sets the type of name to the type of the attributes in <controls>
    let name: keyof typeof controls;
    for (name in controls) {
      let control = controls[name];

      if (!control.invalid) {
        continue;
      }

      switch (name) {
        case "cardNumber":
          this.notificationService.add(`${name} must be in the form XXXX XXXX XXXX XXXX.`, 3);
          continue;
        case "expiration":
          this.notificationService.add(`${name} must be in the form of MM/YYYY.`, 3);
          continue;
        case "cvv":
          this.notificationService.add(`${name} must be in the form of XXX.`, 3);
          continue;
      }

      if (typeof control.value === "number") {
        this.notificationService.add(`${name} must be greater than or equal to 0.`, 3);
        continue;
      }

      this.notificationService.add(`${name} is a required field.`, 3);
    }
  }

}
