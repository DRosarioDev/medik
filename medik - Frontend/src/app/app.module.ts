import { NgModule } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserModule } from '@angular/platform-browser';

import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterModule } from '@angular/router';
import { DayService, MonthAgendaService, MonthService, RecurrenceEditorModule, ScheduleModule, WeekService, WorkWeekService } from '@syncfusion/ej2-angular-schedule';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { environment } from 'src/environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { LoadingComponent } from './components/loading/loading.component';
import { PaginaNonTrovataComponent } from './components/pagina-non-trovata/pagina-non-trovata.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { AppuntamentiPazienteComponent } from './routes/appuntamenti-paziente/appuntamenti-paziente.component';
import { CalendarioComponent } from './routes/calendario/calendario.component';
import { FormAppuntamentoComponent } from './routes/calendario/form-appuntamento/form-appuntamento.component';
import { DashboardMedicoComponent } from './routes/dashboard-medico/dashboard-medico.component';
import { FormRicercaComponent } from './routes/dashboard-medico/form-ricerca/form-ricerca.component';
import { DashboardPazienteComponent } from './routes/dashboard-paziente/dashboard-pazientecomponent';
import { DettagliPazienteComponent } from './routes/dettagli-paziente/dettagli-paziente.component';
import { FormNuovaRicettaComponent } from './routes/dettagli-paziente/form-nuova-ricetta/form-nuova-ricetta.component';
import { HomepageComponent } from './routes/homepage/homepage.component';
import { LoginComponent } from './routes/login/login.component';
import { PazienteRicetteComponent } from './routes/paziente-ricette/paziente-ricette.component';
import { PrenotaAppuntamentoPazienteComponent } from './routes/prenota-appuntamento-paziente/prenota-appuntamento-paziente.component';
import { FormCambioMedicoComponent } from './routes/profilo-utente/form-cambio-medico/form-cambio-medico.component';
import { ProfiloUtenteComponent } from './routes/profilo-utente/profilo-utente.component';
import { InMemoryRepository } from './service/dao/in-memory-repository';
import { AuthTokenInterceptor } from './service/interceptors/auth-token.interceptor';
import { ErrorInterceptor } from './service/interceptors/error.interceptor';
import { LoadingInterceptor } from './service/interceptors/loading.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    PaginaNonTrovataComponent,
    LoadingComponent,
    DashboardMedicoComponent,
    FormRicercaComponent,
    DettagliPazienteComponent,
    FormNuovaRicettaComponent,
    CalendarioComponent,
    FormAppuntamentoComponent,
    HomepageComponent,
    DashboardPazienteComponent,
    LoginComponent,
    ProfiloUtenteComponent,
    SidebarComponent,
    FormCambioMedicoComponent,
    PazienteRicetteComponent,
    AppuntamentiPazienteComponent,
    PrenotaAppuntamentoPazienteComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
		BrowserAnimationsModule,
    CommonModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDialogModule,
    ScheduleModule,
    RecurrenceEditorModule,
    RouterModule,
    environment.backendStrategy === 'MOCK' ? HttpClientInMemoryWebApiModule.forRoot(InMemoryRepository, {apiBase: '/api/v1'}) : []
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthTokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    DayService,
    WeekService,
    MonthService,
    WorkWeekService,
    MonthAgendaService
  
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
