import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageDialogComponent } from './components/message-dialog/message-dialog.component';
import { LoaderComponent } from './loader/loader.component';
import { AppMessageService } from './services/app-message.service';



@NgModule({
  declarations: [
    MessageDialogComponent,
    LoaderComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    AppMessageService
  ],
  exports: [
    LoaderComponent
  ]
})
export class CoreModule { }
