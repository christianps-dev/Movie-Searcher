import { Routes, CanActivateFn } from '@angular/router';
import { Home } from './components/home/home';
import { Favorites } from './components/favorites/favorites';
import { LoginPage } from './components/login-page/login-page';
import { RouteGuard } from './security/auth-guard';
import { SignupPage } from './components/signup-page/signup-page';

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
  },
  {
    path: "signup",
    component: SignupPage
  },
  {
    path: "",
    component: LoginPage
  }
];
