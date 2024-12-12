import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { ConfigGatherComponent } from './components/config-gather/config-gather.component';
import { ThreadManagementComponent } from './components/thread-management/thread-management.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

const routes: Routes = [
  { path: 'config', component: ConfigGatherComponent },
  { path: 'threads', component: ThreadManagementComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: '/config', pathMatch: 'full' }
];

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    AppComponent,
    ThreadManagementComponent,
    ConfigGatherComponent,
    DashboardComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
