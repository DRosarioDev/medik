import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Utente } from 'src/app/model/utente';
import { C } from 'src/app/service/c';
import { ERuolo } from 'src/app/service/e-ruolo';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  @Input() public titolo?: string;
  ERuolo = ERuolo;
 
  constructor(
    private modello: ModelloService,
    private router: Router
  ) {}
 
  get utente(): Utente | undefined {
    return this.modello.getPersistentBean<Utente>(C.USER_LOGIN);
  }
 
  get isMedico(): boolean {
    return this.utente?.ruolo === this.ERuolo.MEDICO;
  }
 
  get isPaziente(): boolean {
    return this.utente?.ruolo === this.ERuolo.PAZIENTE;
  }

  get isHome(): boolean {
    return this.router.url === '/home' || this.router.url === '/';
  }
 
  logout(): void {
    this.modello.clear();
    this.router.navigate(['/login']);
  }


}
