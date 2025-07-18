import { Component, signal } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
@Component({
  selector: 'app-root',
  imports: [ MatToolbarModule,MatIconModule, MatIconButton],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('blog-ui');

}
