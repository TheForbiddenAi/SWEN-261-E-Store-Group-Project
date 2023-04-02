import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Account } from '../account';
import { AccountService } from '../account.service';
import { Duck } from '../duck';
import { ProductService } from '../product.service';
import { SessionService } from '../session.service';
import { SnackBarService } from '../snackbar.service';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ProductCreateComponent } from '../product-create-modify/product-create-modify.component';

@Component({
  selector: 'app-inventory-management',
  templateUrl: './inventory-management.component.html',
  styleUrls: ['./inventory-management.component.css']
})
export class InventoryManagementComponent implements OnInit {
  private _account: Account | undefined = undefined;
  ducks: Duck[] = [];
  ducksToDisplay: Duck[] = [];



  constructor(private _router: Router,
    private _productService: ProductService,
    private _snackBarService: SnackBarService,
    private _accountService: AccountService,
    private _sessionService: SessionService,
    private _dialog: MatDialog,
  ) { }

  /**
   * Loads the ducks array when the page is opened
   */
  ngOnInit(): void {
    if (!this._sessionService.session) {
      this.validateAuthorization();
      return;
    }

    // Waits for account to be retrieved before doing anything else
    this._accountService.getAccount(this._sessionService.session.id).subscribe(account => {
      this._account = account;
      this.validateAuthorization();
      this.getDucks();
    });
  }

  /**
   * Updates the ducks being displayed on screen
   * 
   * @param ducks The new array of ducks
   */
  updateDisplayDucks(ducks: Observable<Duck[]>) {
    ducks.subscribe(duckArr => this.ducksToDisplay = duckArr)
  }

  /**
   * Gets the path to the base image for a duck 
   * 
   * @param duck The duck
   * @returns The path to the image
   */
  getDuckColorImage(duck: Duck): string {
    if (duck.size == "EXTRA_LARGE") return "";

    const color = duck.color.toLowerCase();
    const colorFile = color.charAt(0).toUpperCase() + color.slice(1);
    return `/assets/duck-colors/${duck.size}/${colorFile}.png`;
  }

  /**
   * Gets the path to a given accessory's image
   * 
   * @param accessoryName The name of the accessory
   * @param duck The duck 
   * @returns The path to the image
   */
  getAccessoryImage(accessoryName: string, duck: Duck): string {
    const outfit: any = duck.outfit;
    if (outfit[accessoryName + "UID"] == 0) return "";

    return `/assets/duck-${accessoryName}/${outfit[accessoryName + "UID"]}.png`;
  }

  /**
   * Gets the css class for a given duck accessory
   * 
   * @param accessoryName The name of the accessory
   * @param duck The duck
   * @returns The name of the css class for the accessory
   */
  getCSSClass(accessoryName: string, duck: Duck): string {
    const outfit: any = duck.outfit;
    return `duck-${accessoryName}-${outfit[accessoryName + "UID"]}-${duck.size.toLowerCase()}`;
  }

  /**
   * Gets the ducks from the product service
   */
  getDucks(): void {
    this._productService.getDucks().subscribe(ducks => {
      this.ducks = ducks;
      this.ducksToDisplay = ducks;
    });
  }

  /**
   * Sends the user to the product modification screen for a given duck
   * 
   * @param id The id of the duck
   */
  goToDuckModification(duck: Duck | null): void {
    const dialogRef = this._dialog.open(ProductCreateComponent, { data: duck });
    dialogRef.afterClosed().subscribe((obj) => {
      document.body.style.overflowY = 'visible';
      if (obj == null) return;

      const newDuck = <Duck>obj;
      const index = this.ducksToDisplay.findIndex((duck) => duck.id == newDuck.id);
      if (index == -1) {
        this.ducksToDisplay.push(newDuck);
        return;
      }

      this.ducksToDisplay[index] = newDuck;

    })
    document.body.style.overflowY = 'hidden';
  }

  /**
  * Validates that a user is an admin
  * If not, they are sent back to the login page
  */
  private validateAuthorization(): void {
    if (!this._account?.adminStatus) {
      this._snackBarService.openErrorSnackbar(`You are not authorized to view ${this._router.url}!`)
      this._router.navigate(['/']);
    }
  }

  /**
   * Deletes a given duck
   * 
   * @param duck The duck being deleted
   */
  deleteDuck(duck: Duck): void {
    this.ducks = this.ducks.filter(a_duck => a_duck != duck);
    this._productService.deleteDuck(duck.id).subscribe(httpResponse => {
      switch (httpResponse.status) {
        case 200:
          this._snackBarService.openSuccessSnackbar(`Successfully deleted the duck with the id ${duck.id}.`);
          this.ducksToDisplay = this.ducksToDisplay.filter(duckInArr => duckInArr.id != duck.id);
          break;
        case 404:
          this._snackBarService.openErrorSnackbar(`Failed to delete the duck with the id ${duck.id} because it does not exist!`);
          break;
        default:
          this._snackBarService.openErrorSnackbar(`Failed to delete the duck with the id ${duck.id} because something went wrong.`);
          console.error(httpResponse.statusText);
      }
    });
  }

  /**
   * Gets the price of a duck in the form of $x.xx
   * 
   * @param duck The duck that the cart price is being retrieved for
   * @returns The calculated price to two decimals as a string
   */
  getDuckPrice(duck: Duck): string {
    return `$${(duck.price).toFixed(2)}`;
  }
}
