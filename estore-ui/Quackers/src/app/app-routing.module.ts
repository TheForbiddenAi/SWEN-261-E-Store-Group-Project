import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminTestComponent } from './admin-test/admin-test.component';
import { CustomerTestComponent } from './customer-test/customer-test.component';
import { LoginComponent } from './login/login.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

const routes: Routes = [
  { path: "", redirectTo: "/customerPage", pathMatch: "full"},
  { path: 'adminPage', component: AdminTestComponent },
  { path: 'customerPage', component: CustomerTestComponent },
  { path: "login", component : LoginComponent},
  { path: 'shoppingCartPage', component: ShoppingCartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}