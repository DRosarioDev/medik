import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaginaNonTrovataComponent } from './components/pagina-non-trovata/pagina-non-trovata.component';
import { AuthGuard } from './guard/auth.guard';
//import { LoginComponent } from './routes/login-medico.component/login-medico.component';
import { AppuntamentiPazienteComponent } from './routes/appuntamenti-paziente/appuntamenti-paziente.component';
import { CalendarioComponent } from './routes/calendario/calendario.component';
import { DashboardMedicoComponent } from './routes/dashboard-medico/dashboard-medico.component';
import { DashboardPazienteComponent } from './routes/dashboard-paziente/dashboard-pazientecomponent';
import { DettagliPazienteComponent } from './routes/dettagli-paziente/dettagli-paziente.component';
import { HomepageComponent } from './routes/homepage/homepage.component';
import { LoginComponent } from './routes/login/login.component';
import { PazienteRicetteComponent } from './routes/paziente-ricette/paziente-ricette.component';
import { PrenotaAppuntamentoPazienteComponent } from './routes/prenota-appuntamento-paziente/prenota-appuntamento-paziente.component';
import { ProfiloUtenteComponent } from './routes/profilo-utente/profilo-utente.component';
import { ERuolo } from './service/e-ruolo';
import { AppuntamentiResolver } from './service/resolver/appuntamenti.resolver';
import { DettagliPazienteResolver } from './service/resolver/dettagli-paziente.resolver';
import { OrariDisponibiliResolver } from './service/resolver/orari-disponibili.resolver';
import { PazientiResolver } from './service/resolver/pazienti.resolver';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'pazienti', component: DashboardMedicoComponent, resolve: {pazienti: PazientiResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.MEDICO} },
  { path: 'paziente', component: DashboardPazienteComponent, resolve: {paziente: DettagliPazienteResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.PAZIENTE} },
  { path: 'paziente/profilo', component: ProfiloUtenteComponent, resolve: {paziente: DettagliPazienteResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.PAZIENTE} },
  { path: 'paziente/ricette', component: PazienteRicetteComponent, resolve: {paziente: DettagliPazienteResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.PAZIENTE} },
  { path: 'paziente/appuntamenti', component: AppuntamentiPazienteComponent, resolve: {appuntamenti: AppuntamentiResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.PAZIENTE} },
  { path: 'paziente/appuntamenti/prenota', component: PrenotaAppuntamentoPazienteComponent, resolve: {appuntamento: OrariDisponibiliResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.PAZIENTE} },
  { path: 'pazienti/:idPaziente', component: DettagliPazienteComponent, resolve: {paziente: DettagliPazienteResolver}, canActivate: [AuthGuard], data: {ruolo: ERuolo.MEDICO} },
  { path: 'calendario', component: CalendarioComponent, canActivate: [AuthGuard], data: {ruolo: ERuolo.MEDICO} },
  { path: '**', component: PaginaNonTrovataComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
