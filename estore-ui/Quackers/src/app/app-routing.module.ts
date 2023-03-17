import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerTestComponent } from './customer-test/customer-test.component';
import { InventoryManagementComponent } from './inventory-management/inventory-management.component';
import { LoginComponent } from './login/login.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ProductCreateComponent } from './product-create-modify/product-create-modify.component';
import { ChangePageTestComponent } from './change-page-test/change-page-test.component';

const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full"},
  { path: 'inventory', component: InventoryManagementComponent },
  { path: 'inventory/product', component: ProductCreateComponent},
  { path: 'inventory/product/:id', component: ProductCreateComponent},
  { path: 'customerPage', component: CustomerTestComponent },
  { path: "login", component : LoginComponent},
  { path: "cart", component : ShoppingCartComponent},
  { path: "changedPage", component : ChangePageTestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}