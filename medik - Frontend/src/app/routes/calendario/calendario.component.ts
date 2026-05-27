import { Component, OnInit, ViewChild } from '@angular/core';
import { EventSettingsModel, DayService, WeekService, MonthService, WorkWeekService, PopupOpenEventArgs, ScheduleComponent } from '@syncfusion/ej2-angular-schedule';
import { DaoAppuntamentoService } from 'src/app/service/dao/dao-appuntamento.service';
import { FormAppuntamentoComponent } from './form-appuntamento/form-appuntamento.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
    selector: 'app-calendario',
    templateUrl: './calendario.component.html',
    styleUrls: ['./calendario.component.css'],
    providers: [DayService, WeekService, MonthService, WorkWeekService]
})
export class CalendarioComponent implements OnInit {

    oggi: Date = new Date();
    eventSettings: EventSettingsModel = {};
    views: string[] = ['Day', 'Month'];

    constructor(
        private daoAppuntamento: DaoAppuntamentoService,
        private dialog: MatDialog
    ) { }

    async ngOnInit() {
        try {
            const appuntamenti = await this.daoAppuntamento.findAll();
            this.eventSettings = {
                dataSource: appuntamenti,
                fields: {
                    id: 'id',
                    subject: { name: 'titolo' },
                    startTime: { name: 'dataInizio' },
                    endTime: { name: 'dataFine' }
                }
            };
        } catch (ex) {
            console.log(ex);
        }
    }
    @ViewChild('scheduleObj') scheduleObj!: ScheduleComponent;

    onPopupOpen(args: PopupOpenEventArgs) {
    if (args.type === 'QuickInfo' || args.type === 'Editor') {
        args.cancel = true;
        const dataSelezionata = args.data?.['StartTime'] || args.data?.['startTime'];;
        const id = args.data?.['id'];
        
        
        console.log('Data selezionata:', dataSelezionata);
        
        if (id) {
            console.log('Modifica appuntamento con id:', id);
            
            const dialogRef = this.dialog.open(FormAppuntamentoComponent, {
                data: { 
                    data: new Date(dataSelezionata),
                    id: id
                }
            });
            dialogRef.afterClosed().subscribe(() => {
                this.ngOnInit();
            });
        } else {
            console.log('Nuovo appuntamento per la data:', dataSelezionata);
            if (!dataSelezionata) return;
            const dialogRef = this.dialog.open(FormAppuntamentoComponent, {
                data: { data: new Date(dataSelezionata) }
            });
            dialogRef.afterClosed().subscribe(() => {
                this.ngOnInit();
            });
        }
    }
}
}