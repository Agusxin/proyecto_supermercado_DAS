import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-splash',
  templateUrl: './splash.component.html',
  styleUrls: ['./splash.component.css']
})
export class SplashComponent {

  constructor(private router: Router){}

  ngOnInit(): void {
    setTimeout(() => {
      this.router.navigate(['/main/login']);
    }, 4000);
  }
}
