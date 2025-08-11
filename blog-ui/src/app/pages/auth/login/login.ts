import { Component, inject } from '@angular/core';
import { LoginRequest } from '../../../models/auth/login.request';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  private authService = inject(AuthService);

  loginRequest: LoginRequest = {
    email: '',
    password: ''
  }
  onSubmit() {
    console.log(this.loginRequest);
  }
}
