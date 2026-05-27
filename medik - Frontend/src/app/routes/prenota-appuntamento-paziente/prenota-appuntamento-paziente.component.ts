import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { Router } from '@angular/router';
import { C } from 'src/app/service/c';
import { DaoAppuntamentoService } from 'src/app/service/dao/dao-appuntamento.service';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-prenota-appuntamento-paziente',
  templateUrl: './prenota-appuntamento-paziente.component.html',
  styleUrls: ['./prenota-appuntamento-paziente.component.css'],
})
export class PrenotaAppuntamentoPazienteComponent {

  dataSelezionata: Date | null = null;
  orarioSelezionato: Date | null = null;
  selected: boolean = false;
  customDataSelezionata: boolean = false;

  orariProssimaData: Date[] = [];
  orariCustomData: Date[] = [];

  formGroup: FormGroup = new FormGroup({
    data: new FormControl("", [Validators.required]),
  });

  constructor(
    private modello: ModelloService,
    private daoAppuntamento: DaoAppuntamentoService,
    private router: Router
  ) { }

  get appuntamento(): Date[] {
    return this.modello.getBean(C.ORARI_PRENOTAZIONE)!;
  }

  get dataFormControl(): FormControl {
    return this.formGroup.get("data") as FormControl;
  }

  onSelect(orario: Date): void {
    this.orarioSelezionato = orario;
    this.selected = true;
    this.customDataSelezionata = false;
    this.modello.putBean(C.DATA_SELEZIONATA, orario);
  }

  onSelectCustom(orario: Date): void {
    this.orarioSelezionato = orario;
    this.selected = false;
    this.customDataSelezionata = true;
    this.modello.putBean(C.DATA_SELEZIONATA, orario);
  }


async onSubmit(): Promise<void> {
  try {
    if (!this.orarioSelezionato) {
      console.warn("Nessun orario selezionato.");
      return;
    }

    const dataInizio = new Date(this.orarioSelezionato);
    const dataFine = new Date(dataInizio);
    dataFine.setHours(dataFine.getHours() + 1);

    await this.daoAppuntamento.creaAppuntamento({
      dataInizio: this.formatData(dataInizio),
      dataFine: this.formatData(dataFine)
    });

    console.log("Appuntamento prenotato con successo!");
    this.router.navigate(['/paziente']);
    this.modello.removeBean(C.DATA_SELEZIONATA);
    this.orarioSelezionato = null;
    this.selected = false;
    this.customDataSelezionata = false;

  } catch (ex) {
    console.error("Errore durante la prenotazione:", ex);
  }
}

  get oggi(): string {
    return new Date().toISOString().split('T')[0];
  }

  async onDataChange(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    const data = new Date(input.value);

    try {
      const disponibilita: string[] = await this.daoAppuntamento.getDisponibilitaMedico(data);

      this.orariCustomData = disponibilita.map(stringaData => new Date(stringaData));
      this.orarioSelezionato = null;
      this.customDataSelezionata = true;

    } catch (ex) {
      console.error("Errore nel caricamento disponibilità:", ex);
    }
  }
  private formatData(d: Date): string {
    const anno = d.getFullYear();
    const mese = (d.getMonth() + 1).toString().padStart(2, '0');
    const giorno = d.getDate().toString().padStart(2, '0');
    const ore = d.getHours().toString().padStart(2, '0');
    const minuti = d.getMinutes().toString().padStart(2, '0');
    return `${anno}-${mese}-${giorno}T${ore}:${minuti}`;
  }
}
