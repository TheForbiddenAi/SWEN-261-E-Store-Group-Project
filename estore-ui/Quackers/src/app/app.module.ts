import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule, } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { Router } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AdminTestComponent } from './admin-test/admin-test.component';
import { CustomerTestComponent } from './customer-test/customer-test.component';

import { HttpClientModule} from '@angular/common/http';
import { ChangePageTestComponent } from './change-page-test/change-page-test.component';
import { CatalogComponent } from './catalog/catalog.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { DucksearchComponent } from './ducksearch/ducksearch.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminTestComponent,
    CustomerTestComponent,
    ChangePageTestComponent,
    CatalogComponent,
    NotificationsComponent,
    DucksearchComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
