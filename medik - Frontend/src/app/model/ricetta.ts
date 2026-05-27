
export class Ricetta {
    id?: number;

    constructor(
        readonly urgenza: string,
        readonly data: Date,
        readonly descrizione: string,
        readonly nomeMedico: string,
        readonly cognomeMedico: string
    ){}

}