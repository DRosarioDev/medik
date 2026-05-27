import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { finalize, Observable } from 'rxjs';
import { ModelloService } from '../modello.service';
import { C } from '../c';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {

  constructor(private modello: ModelloService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.modello.putBean(C.CARICAMENTO,true);
    return next.handle(request).pipe(
      finalize(() => this.modello.removeBean(C.CARICAMENTO))
    );
  }
}
