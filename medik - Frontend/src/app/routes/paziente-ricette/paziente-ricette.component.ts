import { Component } from '@angular/core';
import { Medico } from 'src/app/model/medico';
import { Paziente } from 'src/app/model/paziente';
import { Ricetta } from 'src/app/model/ricetta';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-paziente-ricette',
  templateUrl: './paziente-ricette.component.html',
  styleUrls: ['./paziente-ricette.component.css']
})
export class PazienteRicetteComponent {

  constructor(
    private modello: ModelloService
  ) { }

get paziente(): Paziente {
    return this.modello.getPersistentBean(C.PAZIENTE)!;
  }

  get medico(): Medico {
    return this.modello.getBean(C.MEDICO)!;
  }

  get ricette(): Ricetta[] {
    return this.modello.getBean(C.RICETTE) ?? [];
  }

  get ricetteUrgenti(): number {
    return (this.ricette ?? []).filter(r => r.urgenza === 'ALTA').length;
  }

}
