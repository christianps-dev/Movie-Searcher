import { Routes, CanActivateFn } from '@angular/router';
import { Home } from './components/home/home';
import { Favorites } from './components/favorites/favorites';
import { LoginPage } from './components/login-page/login-page';
import { RouteGuard } from './security/auth-guard';

export const routes: Routes = [
  {
    path: "home",
    component: Home,
    canActivate: [RouteGuard]
  },
  {
    path: "favorites",
    component: Favorites,
    canActivate: [RouteGuard]
  },
  {
    path: "login",
    component: LoginPage
  }
];
