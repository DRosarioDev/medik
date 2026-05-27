import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Medico } from 'src/app/model/medico';
import { C } from '../c';
import { ModelloService } from '../modello.service';
import { Utente } from 'src/app/model/utente';

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor {

  constructor(private modello: ModelloService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const utenteLogin = this.modello.getPersistentBean<Utente>(C.USER_LOGIN);
    
    if (utenteLogin) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${utenteLogin.authToken}`
        }
      });
    }
    return next.handle(request);
  }
}
