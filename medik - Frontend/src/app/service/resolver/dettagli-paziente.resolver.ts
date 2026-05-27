import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
} from '@angular/router';
import { DaoRicettaService } from '../dao/dao-ricetta.service';
import { DaoPazienteService } from '../dao/dao-paziente.service';
import { ModelloService } from '../modello.service';
import { MessaggiService } from '../messaggi.service';
import { C } from '../c';
import { DaoAppuntamentoService } from '../dao/dao-appuntamento.service';
import { Utente } from 'src/app/model/utente';
import { ERuolo } from '../e-ruolo';

@Injectable({
  providedIn: 'root'
})
export class DettagliPazienteResolver implements Resolve<void> {

  constructor(
    private router: Router,
    private daoPaziente: DaoPazienteService,
    private daoRicetta: DaoRicettaService,
    private daoAppuntamento: DaoAppuntamentoService,
    private modello: ModelloService,
    private messaggi: MessaggiService
  ) { }

  async resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<void> {

    let idPaziente = route.params['idPaziente'];
    let utente = this.modello.getPersistentBean(C.USER_LOGIN) as Utente;
    let ricette = [];

    try {
      if (idPaziente == undefined) {
        idPaziente = utente.id;
      }
      if (utente.ruolo === ERuolo.MEDICO) {
        const paziente = await this.daoPaziente.findByIdPaziente(idPaziente!);
        console.log(paziente);
        ricette = await this.daoRicetta.findByIdPaziente(idPaziente!);
        console.log(ricette);
        try {
          const appuntamenti = await this.daoAppuntamento.findAll();
          const ora = new Date();
          const prossimo = appuntamenti
            .filter(a => new Date(a.dataInizio) >= ora)
            .sort((a, b) => new Date(a.dataInizio).getTime() - new Date(b.dataInizio).getTime())[0];
          if (prossimo) {
            this.modello.putBean(C.PROSSIMO_APPUNTAMENTO, prossimo);
          }
        } catch (ex) {
          console.log(ex);
        }

        this.modello.putBean(C.RICETTE, ricette);
        this.modello.putBean(C.PAZIENTE_SELEZIONATO, paziente);
      }else if (utente.ruolo === ERuolo.PAZIENTE) {try {
          const appuntamenti = await this.daoAppuntamento.getAppuntamentoByUser();
          const ora = new Date();
          const prossimo = appuntamenti
            .filter(a => new Date(a.dataInizio) >= ora)
            .sort((a, b) => new Date(a.dataInizio).getTime() - new Date(b.dataInizio).getTime())[0];
          
          if (prossimo) {
            this.modello.putBean(C.PROSSIMO_APPUNTAMENTO, prossimo ?? null);
            console.log(prossimo);
            
          }
        } catch (ex) {
          console.log(ex);
        }
        ricette = await this.daoPaziente.findRicetteByIdPaziente();
        const medico = await this.daoPaziente.findMedico();
        this.modello.putBean(C.MEDICO, medico);
        console.log(medico);
        this.modello.putBean(C.RICETTE, ricette); 
      }


    } catch (error) {
      console.error('Errore durante il recupero delle ricette del paziente', error);
      this.messaggi.mostraMessaggioErrore('Errore durante il recupero delle ricette del paziente');
      this.router.navigate(['/home']);

    }

  }

}
