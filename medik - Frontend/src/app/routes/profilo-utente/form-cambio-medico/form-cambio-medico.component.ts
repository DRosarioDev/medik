import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DaoPazienteService } from 'src/app/service/dao/dao-paziente.service';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-form-cambio-medico',
  templateUrl: './form-cambio-medico.component.html',
  styleUrls: ['./form-cambio-medico.component.css']
})
export class FormCambioMedicoComponent implements OnInit {
  
  mediciDisponibili: any[] = [];
  loading = true;

  formGroup: FormGroup = new FormGroup({
    medico: new FormControl('', [Validators.required]) 
  });

  constructor(
    private router: Router,
    private modello: ModelloService,
    private daoPaziente: DaoPazienteService,
    private dialogRef: MatDialogRef<FormCambioMedicoComponent> 
  ) { }

  async ngOnInit(): Promise<void> {
    try {
      this.mediciDisponibili = await this.daoPaziente.findDottoriDisponibili();
    } catch(ex) {
      console.log(ex);
    } finally {
      this.loading = false;
    }
  }

  async onSubmit() {
    if (this.formGroup.valid) {
      try {
        if (!this.medico.value) {
          throw new Error("Seleziona un medico");
        }
        await this.daoPaziente.cambiaMedico(Number(this.medico.value));
        this.dialogRef.close();
        this.router.navigate(['/paziente']);
      } catch (ex) {
        console.log(ex);
      }
    }
  }

  get medico(): FormControl {
    return this.formGroup.get('medico') as FormControl;
  }


}