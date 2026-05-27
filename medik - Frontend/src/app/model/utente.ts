export class Utente {

  public id?: number;

  constructor(readonly nome: string, 
    readonly cognome: string, 
    readonly email: string, 
    readonly ruolo: string, 
    readonly cf: string,
    readonly dataNascita: Date, 
    readonly authToken: string
  ){}

}
