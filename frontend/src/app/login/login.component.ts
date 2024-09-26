import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
/**
 * Class to log in the application with a username already in database
 * Then, the connected user is redirect to the chat page
 */
export class LoginComponent implements OnInit{

  loginForm!: FormGroup;
  submitted = false;
  username: string = "";

  constructor(private formBuilder: FormBuilder, private loginService: LoginService, private router : Router) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit(): void {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }
    this.username = this.loginForm.value.username;
    this.loginService.login(this.loginForm.value.username).subscribe({
      next: () => {
        this.loginService.username = this.username;
        this.router.navigateByUrl('/chat');
      }
    });
 }

}
