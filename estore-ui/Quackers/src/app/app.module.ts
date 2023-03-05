import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { Router } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { CustomerTestComponent } from './customer-test/customer-test.component';

import { HttpClientModule} from '@angular/common/http';
import { InventoryManagementComponent } from './inventory-management/inventory-management.component';
import { ProductModifyComponent } from './product-modify/product-modify.component';
import { ProductCreateComponent } from './product-create/product-create.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CustomerTestComponent,
    InventoryManagementComponent,
    ProductModifyComponent,
    ProductCreateComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
