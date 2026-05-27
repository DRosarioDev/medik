import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot
} from '@angular/router';
import { Medico } from 'src/app/model/medico';
import { C } from '../c';
import { DaoPazienteService } from '../dao/dao-paziente.service';
import { MessaggiService } from '../messaggi.service';
import { ModelloService } from '../modello.service';

@Injectable({
  providedIn: 'root'
})
export class PazientiResolver implements Resolve<void> {

  constructor(
    private modello: ModelloService,
    private daoPazienti: DaoPazienteService,
    private messaggi: MessaggiService
  ){}

  async resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<void> {
    
    try{
      const utente = this.modello.getPersistentBean<Medico>(C.MEDICO);
      if(!utente){
        throw new Error("Medico non autenticato");
      }
      const idMedico = utente.id!;
      const pazienti = await this.daoPazienti.findByAll(idMedico);
      this.modello.putBean(C.PAZIENTI, pazienti);

    }catch(ex){
      console.log(ex);
      this.messaggi.mostraMessaggioErrore('Errore durante il recupero dei pazienti');
    }

  }
}
