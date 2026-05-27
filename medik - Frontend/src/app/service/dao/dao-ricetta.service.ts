import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Ricetta } from 'src/app/model/ricetta';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DaoRicettaService {

  constructor(
    private http: HttpClient
  ) { }

  async findByIdPaziente(idPaziente: number): Promise<Ricetta[]>{
    let apiUrl = `${environment.backendUrl}/medici/${idPaziente}/ricette`; 
    return lastValueFrom(this.http.get<Ricetta[]>(apiUrl));
  }

  async aggiungiNuovaRicetta(idPaziente: number, descrizione: string, urgenza: string): Promise<Ricetta[]>{
    let apiUrl = `${environment.backendUrl}/medici/pazienti/${idPaziente}/nuova-ricetta`; 
    return lastValueFrom(this.http.post<Ricetta[]>(apiUrl, {descrizione, urgenza }));
  }



}
