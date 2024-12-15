import { NgModule } from '@angular/core';
import {bootstrapApplication, BrowserModule} from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { TicketConfigurationComponent } from './components/ticket-configuration/ticket-configuration.component';
import { MovieLandingComponent } from './components/movie-landing/movie-landing.component';
import {provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'ticket-configuration', component: TicketConfigurationComponent },
  { path: '' +
      '', component: MovieLandingComponent }
];

bootstrapApplication(AppComponent);
provideHttpClient(withInterceptorsFromDi());

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routes),
    AppComponent,
    LoginComponent,
    RegisterComponent,
    TicketConfigurationComponent,
    MovieLandingComponent
  ],
  providers: [],
})
export class AppModule { }
