import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Appuntamento } from 'src/app/model/Appuntamento';
import { Paziente } from 'src/app/model/paziente';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-appuntamenti-paziente',
  templateUrl: './appuntamenti-paziente.component.html',
  styleUrls: ['./appuntamenti-paziente.component.css']
})
export class AppuntamentiPazienteComponent {

  constructor(
    private modello: ModelloService,
    private router: Router
  ){}

  get paziente(): Paziente{
    return this.modello.getPersistentBean(C.PAZIENTE)!;
  }

  get appuntamento(): Appuntamento{
    return this.modello.getPersistentBean(C.PROSSIMO_APPUNTAMENTO)!;
  }

  get appuntamenti(): Appuntamento[]{
    return this.modello.getPersistentBean(C.APPUNTAMENTI)!;
  }

  onPrenotaAppuntamento(): void {
    this.router.navigate(['/paziente/appuntamenti/prenota']);
  }

}
