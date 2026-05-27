import { Component } from '@angular/core';
import { C } from 'src/app/service/c';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent {

  constructor(private modello: ModelloService){
  }

  get loading(): boolean{
    return this.modello.getBean(C.CARICAMENTO) === true;
  }

}
