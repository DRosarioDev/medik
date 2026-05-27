import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, map, tap } from 'rxjs';
import { Utente } from 'src/app/model/utente';
import { environment } from 'src/environments/environment';
import { ModelloService } from '../modello.service';

@Injectable({
  providedIn: 'root'
})
export class DaoUserService {

  constructor(private httpClient: HttpClient, private modello: ModelloService) { }

  login(email: string, password: string): Promise<Utente> {
    if (environment.backendStrategy === 'MOCK') {
      return this.gestisciLoginMock(email, password);
    }
    let apiURL = environment.backendUrl + '/utenti/login';
    return lastValueFrom(
      this.httpClient.post<Utente>(apiURL, { email: email, password: password })
        .pipe(
          tap(response => {
            console.log('Ricevuta risposta ', response);
            if (!response) throw new Error("Token di autorizzazione assente");
          })
        )
    );
  }

  //NECESSARIO SOLO PER L'IMPLEMENTAZIONE MOCK CON INMEMORYDBSERVICE
  private gestisciLoginMock(email: string, password: string): Promise<Utente> {
    let apiURL = environment.backendUrl + '/utenti';
    return lastValueFrom(this.httpClient.get<Utente[]>(apiURL).pipe(
      map(utenti => this.cercaUtente(utenti, email, password))
    ));
  }

  private cercaUtente(utenti: Utente[], email: string, password: string): Utente {
    let utente = utenti.find(u => u.email === email && (u as any).password === password);
    if (!utente) throw new Error('Credenziali scorrette');
    let utenteLogin = new Utente(utente.nome, utente.cognome, utente.email, utente.ruolo, utente.cf, utente.dataNascita,'token-' + email);
    utenteLogin.id = utente.id;
    return utenteLogin;
  }
}
