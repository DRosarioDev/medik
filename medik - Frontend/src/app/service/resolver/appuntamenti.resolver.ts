import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { ModelloService } from '../modello.service';
import { DaoPazienteService } from '../dao/dao-paziente.service';
import { DaoAppuntamentoService } from '../dao/dao-appuntamento.service';
import { C } from '../c';

@Injectable({
  providedIn: 'root'
})
export class AppuntamentiResolver implements Resolve<void> {

  constructor(
    private modello: ModelloService,
    private daoPaziente: DaoPazienteService,
    private daoAppuntamento: DaoAppuntamentoService
  ) {}


  async resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<void> {


    try{
      const appuntamenti = await this.daoAppuntamento.findAll();
      this.modello.putBean(C.APPUNTAMENTI, appuntamenti);
    }catch(error){
      console.error('Errore durante il recupero degli appuntamenti:', error);
      this.modello.putBean(C.APPUNTAMENTI, []);
    }

  }
}
