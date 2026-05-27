import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Medico } from 'src/app/model/medico';
import { Paziente } from 'src/app/model/paziente';
import { Utente } from 'src/app/model/utente';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {

  constructor(
    private modello: ModelloService,
    private router: Router
  ) { }

  get medico(): Medico | undefined {
    return this.modello.getPersistentBean<Medico>(C.MEDICO);
  }

  get paziente(): Paziente {
    return this.modello.getPersistentBean<Paziente>(C.PAZIENTE)!;
  }

    get isHome(): boolean {
    return this.router.url === '/home' || this.router.url === '/';
  }

}
