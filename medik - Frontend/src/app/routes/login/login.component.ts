import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { C } from 'src/app/service/c';
import { DaoUserService } from 'src/app/service/dao/dao-user.service';
import { ERuolo } from 'src/app/service/e-ruolo';
import { MessaggiService } from 'src/app/service/messaggi.service';
import { ModelloService } from 'src/app/service/modello.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  formGroup: FormGroup = new FormGroup({
    email: new FormControl("", [Validators.email, Validators.required]),
    password: new FormControl("", [Validators.required])
  });

  constructor(
    private modello: ModelloService,
    private daoUser: DaoUserService,
    private messaggi: MessaggiService,  
    private router: Router
  ){}

  async onSubmit(): Promise<void>{
    try{
      const utente = await this.daoUser.login(this.emailFormControl.value, this.passwordFormControl.value)
      console.log(this.emailFormControl.value);
      console.log(this.passwordFormControl.value);
      
      this.modello.putPersistentBean(C.USER_LOGIN, utente);
      if(utente.ruolo === ERuolo.MEDICO){
        console.log("Log Medico");
        
        this.modello.putPersistentBean(C.MEDICO, utente);
        this.router.navigate(["/pazienti"]);
      } else if(utente.ruolo == ERuolo.PAZIENTE){
        this.modello.putPersistentBean(C.PAZIENTE, utente);
        this.router.navigate(["/paziente"]);
        console.log("Log Paziente");
      }
    }catch(ex){
      console.log(ex);
      this.messaggi.mostraMessaggioErrore("Email o password errati!");
    }
  } 

  get emailFormControl(): FormControl{
    return this.formGroup.get("email") as FormControl;
  }

  get passwordFormControl(): FormControl{
    return this.formGroup.get("password") as FormControl;
  }
  
}
