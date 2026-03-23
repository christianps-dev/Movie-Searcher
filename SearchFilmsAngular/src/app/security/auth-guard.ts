import { inject, Injectable } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const RouteGuard: CanActivateFn = (route, state) => {

  const localToken = sessionStorage.getItem('token');
  const localUsername = sessionStorage.getItem('username');
  const router = new Router;

  if(localUsername == null || localToken == null){
   router.navigate(["/login"])
    return false;
  }
  return true;
};
