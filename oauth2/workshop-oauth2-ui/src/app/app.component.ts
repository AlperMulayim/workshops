import { Component } from '@angular/core';
import { MsalService } from '@azure/msal-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'workshop-oauth2-ui';
  constructor(private msalService : MsalService){}

  ngOnInit() {
    
  }
  
  login() {
    this.msalService.loginRedirect();
  }
}
