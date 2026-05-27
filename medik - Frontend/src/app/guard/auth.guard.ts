import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Utente } from '../model/utente';
import { C } from '../service/c';
import { ModelloService } from '../service/modello.service';
import { ERuolo } from '../service/e-ruolo';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private modello: ModelloService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const utenteLogin = this.modello.getPersistentBean<Utente>(C.USER_LOGIN);
    
    if (!utenteLogin || !utenteLogin.authToken) {
      this.router.navigate(['/login']);
      return false;
    }

    const ruoloRichiesto = route.data['ruolo'] as ERuolo;
    if (ruoloRichiesto && utenteLogin.ruolo !== ruoloRichiesto) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }

}
