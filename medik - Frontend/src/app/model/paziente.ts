export class Paziente{

    public id?: number;

    constructor(
        readonly nome: string,
        readonly cognome: string,
        readonly dataNascita: Date,
        readonly cf: string,
        readonly email: string
    ){}


}