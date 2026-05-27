import { Component } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Paziente } from 'src/app/model/paziente';
import { C } from 'src/app/service/c';
import { DaoRicettaService } from 'src/app/service/dao/dao-ricetta.service';
import { MessaggiService } from 'src/app/service/messaggi.service';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-form-nuova-ricetta',
  templateUrl: './form-nuova-ricetta.component.html',
  styleUrls: ['./form-nuova-ricetta.component.css']
})
export class FormNuovaRicettaComponent {

  formGroup: FormGroup = new FormGroup({
    descrizione: new FormControl('', [Validators.required]),
    urgenza: new FormControl('', [Validators.required]),
  });

  constructor(
    private modello: ModelloService,
    private daoRicetta: DaoRicettaService,
    private messaggi: MessaggiService,
    private router: Router,
    private dialogRef: MatDialogRef<FormNuovaRicettaComponent>
  ) { }


  async onSubmit() {
    if (this.formGroup.invalid) return;
    try {
      const paziente = this.modello.getBean(C.PAZIENTE_SELEZIONATO) as Paziente;

      if (!paziente.id) {
        this.messaggi.mostraMessaggioErrore('ID paziente non valido');
        return;
      }

      await this.daoRicetta.aggiungiNuovaRicetta(paziente.id, this.descrizioneFormControl.value, this.urgenzaFormControl.value);
      console.log("Ricetta aggiunta correttamente!");
      this.dialogRef.close(true);


    } catch (ex) {
      console.log(ex);
      this.messaggi.mostraMessaggioErrore('Errore durante l\'aggiunta della nuova ricetta');
    }

  }


  get descrizioneFormControl(): FormControl {
    return this.formGroup.get('descrizione') as FormControl;
  }

  get urgenzaFormControl(): FormControl {
    return this.formGroup.get('urgenza') as FormControl;
  }



}
