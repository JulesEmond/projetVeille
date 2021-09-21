import { Component} from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { User } from './models/user';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  nomHeader: string;
  id: number;
  user: User;
  
  constructor(private router: Router, public service: UserService){
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart){
        console.log(event.url);
      }
    })
  }
}
