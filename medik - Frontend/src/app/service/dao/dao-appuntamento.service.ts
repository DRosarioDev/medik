import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Appuntamento } from 'src/app/model/Appuntamento';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class DaoAppuntamentoService {
    constructor(private httpClient: HttpClient) { }

    async findAll(): Promise<any[]> {
        const appuntamenti = await lastValueFrom(
            this.httpClient.get<any[]>(`${environment.backendUrl}/appuntamenti`)
        );
        return appuntamenti.map(a => ({
            id: a.id,
            titolo: a.titolo,
            dataInizio: new Date(a.dataInizio),
            dataFine: new Date(a.dataFine)
        }));
    }


    async creaAppuntamento(appuntamento: any): Promise<any> {
        let apiURL = `${environment.backendUrl}/appuntamenti/nuovo`;
        console.log(apiURL);
        return lastValueFrom(this.httpClient.post<Appuntamento>(apiURL, appuntamento));
    }

    async getAppuntamentoById(id: number): Promise<any> {
        let apiURL = `${environment.backendUrl}/appuntamenti/${id}`;
        console.log(apiURL);
        return lastValueFrom(this.httpClient.get<Appuntamento>(apiURL));
    }

    async getAppuntamentoByUser(): Promise<Appuntamento[]> {
        let apiURL = `${environment.backendUrl}/appuntamenti`;
        console.log(apiURL);
        return lastValueFrom(this.httpClient.get<Appuntamento[]>(apiURL));
    }

    async getAppuntamentoVicino(): Promise<Date[]> {
        let apiURL = `${environment.backendUrl}/appuntamenti/prenotazione-vicina`;
        console.log(apiURL);
        return lastValueFrom(this.httpClient.get<Date[]>(apiURL));
    }

    async getDisponibilitaMedico(data: Date): Promise<string[]> {
        const anno = data.getFullYear();
        const mese = (data.getMonth() + 1).toString().padStart(2, '0');
        const giorno = data.getDate().toString().padStart(2, '0');
        const dataFormattata = anno + '-' + mese + '-' + giorno;
        let apiURL = `${environment.backendUrl}/appuntamenti/disponibilita?data=${dataFormattata}`;
        console.log(apiURL);
        return lastValueFrom(this.httpClient.get<string[]>(apiURL));
    }

    async modificaAppuntamento(id: number, titolo: string): Promise<void> {
        let apiURL = `${environment.backendUrl}/appuntamenti/modifica/${id}`;
        console.log(apiURL);
        return lastValueFrom(this.httpClient.put<void>(apiURL, titolo));
    }

    async eliminaAppuntamento(id: number): Promise<void> {
        let apiURL = `${environment.backendUrl}/appuntamenti/elimina/${id}`;
        console.log(apiURL);
        await lastValueFrom(this.httpClient.delete<void>(apiURL));
    }

}

