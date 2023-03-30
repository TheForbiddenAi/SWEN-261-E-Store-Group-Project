import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CatalogComponent } from './catalog/catalog.component';
import { DuckDetailComponent } from './duck-detail/duck-detail.component';
import { InventoryManagementComponent } from './inventory-management/inventory-management.component';
import { LoginComponent } from './login/login.component';
import { ProductCreateComponent } from './product-create-modify/product-create-modify.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';

const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },
  { path: 'inventory', component: InventoryManagementComponent },
  { path: 'inventory/product', component: ProductCreateComponent },
  { path: 'inventory/product/:id', component: ProductCreateComponent },
  { path: "login", component: LoginComponent },
  { path: "catalog", component: CatalogComponent },
  { path: "catalog/:id", component: DuckDetailComponent },
  { path: "cart", component: ShoppingCartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }