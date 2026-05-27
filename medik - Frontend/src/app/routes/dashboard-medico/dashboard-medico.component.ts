import { Component } from '@angular/core';
import { Paziente } from 'src/app/model/paziente';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-dashboard-medico',
  templateUrl: './dashboard-medico.component.html',
  styleUrls: ['./dashboard-medico.component.css']
})
export class DashboardMedicoComponent {

  constructor(
    private modello: ModelloService
  ){}

  get pazienti(): Paziente[]{
    return this.modello.getBean(C.PAZIENTI)!;
  }



}
