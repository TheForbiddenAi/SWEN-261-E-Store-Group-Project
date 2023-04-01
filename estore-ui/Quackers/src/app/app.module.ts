import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CatalogComponent } from './catalog/catalog.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { DuckDetailComponent } from './duck-detail/duck-detail.component';
import { DucksearchComponent } from './ducksearch/ducksearch.component';
import { InventoryManagementComponent } from './inventory-management/inventory-management.component';
import { LoginComponent } from './login/login.component';
import { ProductCreateComponent } from './product-create-modify/product-create-modify.component';
import { ReceiptComponent } from './receipt/receipt.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { SnackbarNotificationComponent } from './snackbar-notification/snackbar-notification.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ShoppingCartComponent,
    InventoryManagementComponent,
    ProductCreateComponent,
    CatalogComponent,
    DucksearchComponent,
    DuckDetailComponent,
    CheckoutComponent,
    ReceiptComponent,
    SnackbarNotificationComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
