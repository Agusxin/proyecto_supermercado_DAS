import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { Loader, LoaderService } from '../services/loader.service';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent {

  private _subscription!: Subscription;
  private _loaded: boolean = false;

  constructor(private _service: LoaderService) { }

  ngOnInit(): void {
    this._subscription = this._service.loader$.subscribe((ref: Loader) => {
      this._loaded = ref.loaded;
    });
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  get loaded(): boolean {
    return this._loaded;
  }

}
