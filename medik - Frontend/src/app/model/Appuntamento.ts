export class Appuntamento {
    public id?: number;


    constructor(readonly titolo: string, readonly dataInizio: Date, readonly dataFine: Date) {}
}