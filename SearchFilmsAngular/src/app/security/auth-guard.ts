import { CanActivateFn, Router } from '@angular/router';

export const RouteGuard: CanActivateFn = (route, state) => {

  const localToken = sessionStorage.getItem('token');
  const localUsername = sessionStorage.getItem('username');
  const localEmail = sessionStorage.getItem('email');
  const router = new Router;

  if(localUsername == null || localToken == null || localToken == "null"
     || localUsername == "aUsed" || localEmail == "aUSed"
     || localUsername == "null" || localEmail == "null"){
    return false;
  }
  return true;
};
