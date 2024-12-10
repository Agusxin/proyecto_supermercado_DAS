import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SplashComponent } from './main/splash/splash.component';


const routes: Routes = [
  { path: '', component: SplashComponent, pathMatch: 'full' },
  { path: 'main', loadChildren: () => import('./main/main.module').then(m => m.MainModule) },
  { path: '**', redirectTo: '/main'}
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
