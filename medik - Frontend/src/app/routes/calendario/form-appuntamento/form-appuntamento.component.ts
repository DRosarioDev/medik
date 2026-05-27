import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Medico } from 'src/app/model/medico';
import { C } from 'src/app/service/c';
import { DaoAppuntamentoService } from 'src/app/service/dao/dao-appuntamento.service';
import { MessaggiService } from 'src/app/service/messaggi.service';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-form-appuntamento',
  templateUrl: './form-appuntamento.component.html',
  styleUrls: ['./form-appuntamento.component.css']
})
export class FormAppuntamentoComponent implements OnInit {

  orari: string[] = [];
  loading = true;
  idCheck: boolean = false;


  formGroup: FormGroup = new FormGroup({
    titolo: new FormControl('', [Validators.required]),
    orario: new FormControl('', [])
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { data: Date, id: number },
    private router: Router,
    private modello: ModelloService,
    private messaggi: MessaggiService,
    private daoAppuntamento: DaoAppuntamentoService,
    private dialogRef: MatDialogRef<FormAppuntamentoComponent>,
  ) { }

  async ngOnInit() {
    try {
      if (this.data.id) {
        const appuntamento = await this.daoAppuntamento.getAppuntamentoById(this.data.id);
        this.idCheck = true;
        console.log(appuntamento);

      }
      // TODO Semplificazione logica da LocalDateTime a LocalTime
      if (this.data.data && !this.idCheck) {
        console.log('Data ricevuta dal calendario:', this.data.data);
        const disponibilita = await this.daoAppuntamento.getDisponibilitaMedico(this.data.data);
        for (let i = 0; i < disponibilita.length; i++) {
          const date = new Date(disponibilita[i]);
          const hours = date.getHours().toString().padStart(2, '0');
          const minutes = date.getMinutes().toString().padStart(2, '0');
          this.orari.push(`${hours}:${minutes}`);
          console.log(this.orari);
        }
      }
    } catch (ex) {
      console.log(ex);
      this.messaggi.mostraMessaggioErrore('Errore nel caricamento degli orari disponibili');
    } finally {
      this.loading = false;
    }
  }

  async onSubmit() {
    if (this.formGroup.get('orario')?.value === '') return;
    try {
      const utente = this.modello.getPersistentBean(C.MEDICO) as Medico;
      if (!utente) {
        this.messaggi.mostraMessaggioErrore('Utente non loggato');
        return;
      }
      const data = this.data.data;
      const orarioSelezionato = this.formGroup.get('orario')!.value;
      const ore = orarioSelezionato.split(':')[0];
      const minuti = orarioSelezionato.split(':')[1];

      const dataInizio = new Date(data);
      dataInizio.setHours(parseInt(ore), parseInt(minuti), 0);
      console.log("Data Inizio: " + dataInizio);

      const dataFine = new Date(data);
      dataFine.setHours(parseInt(ore) + 1, parseInt(minuti), 0);

      const appuntamento = {
        titolo: this.titolo.value,
        dataInizio: this.formatData(dataInizio),
        dataFine: this.formatData(dataFine)
      };

      console.log("Data Fine: " + dataFine);
      console.log("Appuntamento: " + JSON.stringify(appuntamento));
      await this.daoAppuntamento.creaAppuntamento(appuntamento);
      this.dialogRef.close(true);

    } catch (ex) {
      console.log(ex);
      this.messaggi.mostraMessaggioErrore('Errore durante l\'aggiunta del nuovo appuntamento');
    }
  }

  async onModifica() {
    console.log("this.formGroup.invalid" + this.formGroup.invalid);
    if (this.formGroup.get('titolo')!.invalid) return;
    try {

      await this.daoAppuntamento.modificaAppuntamento(this.data.id, this.titolo.value);
      this.dialogRef.close(true);

    } catch (ex) {
      console.log(ex);
      this.messaggi.mostraMessaggioErrore('Errore durante la modifica dell\'appuntamento');
    }
  }

  async onElimina() {
    try {
      await this.daoAppuntamento.eliminaAppuntamento(this.data.id);
      this.dialogRef.close(true);

    } catch (ex) {
      console.log(ex);
      this.messaggi.mostraMessaggioErrore('Errore durante l\'eliminazione dell\'appuntamento');
    }
  }

  get titolo(): FormControl {
    return this.formGroup.get('titolo') as FormControl;
  }

  get dataInizio(): FormControl {
    return this.formGroup.get('dataInizio') as FormControl;
  }

  get dataFine(): FormControl {
    return this.formGroup.get('dataFine') as FormControl;
  }

  // Conversione della data nel formato corretto
  formatData(d: Date): string {
    const anno = d.getFullYear();
    const mese = (d.getMonth() + 1).toString().padStart(2, '0');
    const giorno = (d.getDate()).toString().padStart(2, '0');
    const ore = d.getHours().toString().padStart(2, '0');
    const minuti = d.getMinutes().toString().padStart(2, '0');
    return anno + '-' + mese + '-' + giorno + 'T' + ore + ':' + minuti;
  }

}