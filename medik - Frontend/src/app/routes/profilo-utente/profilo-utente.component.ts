import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PopupOpenEventArgs } from '@syncfusion/ej2-angular-schedule';
import { Medico } from 'src/app/model/medico';
import { Paziente } from 'src/app/model/paziente';
import { Utente } from 'src/app/model/utente';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';
import { FormCambioMedicoComponent } from './form-cambio-medico/form-cambio-medico.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-profilo-utente',
  templateUrl: './profilo-utente.component.html',
  styleUrls: ['./profilo-utente.component.css']
})
export class ProfiloUtenteComponent {

  constructor(
    private modello: ModelloService,
    private router: Router,
    private dialog: MatDialog
  ) {}
 
  get paziente(): Utente {
    return this.modello.getPersistentBean(C.PAZIENTE)!;
  }
 
  get medico(): Medico {
    return this.modello.getBean(C.MEDICO)!;
  }
 
  onCambioMedico() : void{
    
    const dialogRef = this.dialog.open(FormCambioMedicoComponent);
    dialogRef.afterClosed().subscribe(() => {});


  }


}
