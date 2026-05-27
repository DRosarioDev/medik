import { InMemoryDbService } from "angular-in-memory-web-api";

export class InMemoryRepository extends InMemoryDbService {

  createDb() {
    console.log('Inizializzato db');
    let utenti = [
      { id: 1, nome: "Mario", cognome: "Rossi", email: "medico@a.it", password: "Medico!" },
      { id: 2, nome: "Giulia", cognome: "Bianchi", email: "utente@a.it", password: "Utente!" },
    ];

    let pazienti  = [
      { id: 1, nome: "Luca", cognome: "Verdi", dataNascita: "1990-01-01", codiceFiscale: "VRDLCA90A01H501X" },
      { id: 2, nome: "Anna", cognome: "Neri", dataNascita: "1985-05-15", codiceFiscale: "NRNANN85E55H501Y" },
    ];

    let ricette = [
      { id: 1, idPaziente: 1, data: "2024-01-01", descrizione: "Ricetta per mal di testa", urgenza: "Bassa" },
      { id: 2, idPaziente: 1, data: "2024-02-15", descrizione: "Ricetta per febbre alta", urgenza: "Alta" },
      { id: 3, idPaziente: 1, data: "2024-03-10", descrizione: "Ricetta per dolori muscolari", urgenza: "Media" },
    ];

    return { utenti, pazienti, ricette };
  }

}
