import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SuperResourcesService } from '../../api/resources/super-resources.service';
import { IUsuarioLogin } from '../../api/models/iusuario-login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  loginError: boolean = false;

  constructor(private fb: FormBuilder, private resourceService: SuperResourcesService, private router: Router ){}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      nombre_usuario: ['', Validators.required],
      clave: ['', Validators.required]
    });
  }

  onSubmit(): void {

    if(this.loginForm.valid){
      const loginData: IUsuarioLogin = this.loginForm.value as IUsuarioLogin;

      console.log("Data usuario:" + loginData.nombre_usuario);
      console.log("Data clave:" + loginData.clave);


      this.resourceService.login(loginData).subscribe({
        next: (response) => {
          this.router.navigate(['/main/listarPedidos']);
          this.loginError = false;
          console.log("Login exitoso", response);
        },
        error: (error) => {
          console.log("Error en el login", error);
          this.loginError = true;
        },
        complete: () => {
          console.log("Login completado");
        }
      });
    }else{
      console.log("Formulario invalido");
    }
  }
}
