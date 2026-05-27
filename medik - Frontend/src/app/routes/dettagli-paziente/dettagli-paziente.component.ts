import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Paziente } from 'src/app/model/paziente';
import { Ricetta } from 'src/app/model/ricetta';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';
import { FormNuovaRicettaComponent } from './form-nuova-ricetta/form-nuova-ricetta.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dettagli-paziente',
  templateUrl: './dettagli-paziente.component.html',
  styleUrls: ['./dettagli-paziente.component.css']
})
export class DettagliPazienteComponent {

  constructor(
    private modello: ModelloService,
    private dialog: MatDialog,
    private router: Router
  ) { }


  openDialog(): void {
    const dialogRef = this.dialog.open(FormNuovaRicettaComponent, {
      data: { paziente: this.paziente },
      width: '1000px',
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      if (result) {
        await this.router.navigateByUrl('/', { skipLocationChange: true });
        this.router.navigate(['/pazienti', this.paziente.id]);
      }
    });
  }


  get paziente(): Paziente {
    return this.modello.getBean(C.PAZIENTE_SELEZIONATO)!;
  }

  get ricette(): Ricetta[] {
    return this.modello.getBean(C.RICETTE)!;
  }
}
