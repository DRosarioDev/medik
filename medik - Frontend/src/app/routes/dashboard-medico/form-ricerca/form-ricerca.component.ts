import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { C } from 'src/app/service/c';
import { DaoPazienteService } from 'src/app/service/dao/dao-paziente.service';
import { MessaggiService } from 'src/app/service/messaggi.service';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-form-ricerca',
  templateUrl: './form-ricerca.component.html',
  styleUrls: ['./form-ricerca.component.css']
})
export class FormRicercaComponent {
  
  formGroup: FormGroup = new FormGroup({
    cognomePaziente: new FormControl("", [Validators.required]),
  });

  constructor(
    private  messaggi: MessaggiService,
    private daoPazienti: DaoPazienteService,
    private modello: ModelloService
  ){}



  async onSubmit(){
    try{
      const pazienti = await this.daoPazienti.findByCognomePaziente(this.cognomePazienteFormControl.value);
      if(pazienti.length == 0){
        this.messaggi.mostraMessaggioErrore("Nessun paziente trovato con il nome inserito");
      }else{
        this.modello.putBean(C.PAZIENTI, pazienti);
      }
    }catch(ex){
      console.log(ex);
      this.messaggi.mostraMessaggioErrore("Errore durante la ricerca del paziente");
    }
  }


  get cognomePazienteFormControl(): FormControl{
    return this.formGroup.get("cognomePaziente") as FormControl;
  }
}
