import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Appuntamento } from 'src/app/model/Appuntamento';
import { Medico } from 'src/app/model/medico';
import { Paziente } from 'src/app/model/paziente';
import { Ricetta } from 'src/app/model/ricetta';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-dashboard-paziente',
  templateUrl: './dashboard-paziente.component.html',
  styleUrls: ['./dashboard-paziente.component.css']
})
export class DashboardPazienteComponent {

  constructor(
    private modello: ModelloService,
    private router: Router
  ) {}


  onLogout(): void {
    this.modello.removePersistentBean(C.PAZIENTE);
    this.router.navigate(['/login']);
  }

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

  get prossimoAppuntamento(): Appuntamento {
    return this.modello.getBean(C.PROSSIMO_APPUNTAMENTO)!;
  }
}