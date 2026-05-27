import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Paziente } from 'src/app/model/paziente';
import { lastValueFrom, tap } from 'rxjs';
import { Ricetta } from 'src/app/model/ricetta';
import { Medico } from 'src/app/model/medico';

@Injectable({
  providedIn: 'root'
})
export class DaoPazienteService {

  constructor(
    private httpClient: HttpClient
  ) { }

  async findByCognomePaziente(cognome: string): Promise<Paziente[]>{
    let apiURL = `${environment.backendUrl}/pazienti?cognome=${cognome}`;
    return lastValueFrom(this.httpClient.get<Paziente[]>(apiURL));
  }

  async findByIdPaziente(idPaziente: number): Promise<Paziente>{
    let apiURL = `${environment.backendUrl}/pazienti/${idPaziente}`;
    return lastValueFrom(this.httpClient.get<Paziente>(apiURL));
  }

  async findByAll(idMedico: number): Promise<Paziente[]>{
    let apiURL = `${environment.backendUrl}/medici/pazienti`;
    return lastValueFrom(this.httpClient.get<Paziente[]>(apiURL)); 
  }

  async findRicetteByIdPaziente(): Promise<Ricetta[]>{
    let apiURL = `${environment.backendUrl}/paziente/ricette`;
    return lastValueFrom(this.httpClient.get<Ricetta[]>(apiURL)); 
  }

  async findMedico(): Promise<Medico>{
    let apiURL = `${environment.backendUrl}/paziente/medico`;
    return lastValueFrom(this.httpClient.get<Medico>(apiURL)); 
  }

  async findDottoriDisponibili(): Promise<Medico[]>{
    let apiURL = `${environment.backendUrl}/paziente/medici-disponibili`;
    return lastValueFrom(this.httpClient.get<Medico[]>(apiURL)); 
  }

  async cambiaMedico(idMedico: number): Promise<void>{
    let apiURL = `${environment.backendUrl}/paziente/cambio-medico`;
    await lastValueFrom(this.httpClient.put(apiURL, idMedico));
  }

  login(email: string, password: string): Promise<Paziente> {
      let apiURL = environment.backendUrl + '/utenti/login';
      return lastValueFrom(
        this.httpClient.post<Paziente>(apiURL, { email: email, password: password })
          .pipe(
            tap(response => {
              console.log('Ricevuta risposta ', response);
              if (!response) throw new Error("Token di autorizzazione assente");
            })
          )
      );
    }
  

}
