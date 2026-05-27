import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Utente } from 'src/app/model/utente';
import { C } from 'src/app/service/c';
import { ERuolo } from 'src/app/service/e-ruolo';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

    ERuolo = ERuolo;
    
   
    constructor(
      private modello: ModelloService,
      private router: Router
    ) {}
   
    get utente(): Utente {
      return this.modello.getPersistentBean<Utente>(C.USER_LOGIN)!;
    }
   
    get isMedico(): boolean {
      return this.utente?.ruolo === this.ERuolo.MEDICO;
    }
   
    get isPaziente(): boolean {
      return this.utente?.ruolo === this.ERuolo.PAZIENTE;
    }

    onLogout(): void {
      this.modello.clear();
      this.router.navigate(['/login']);
    }

}
