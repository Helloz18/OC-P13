import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessagingService } from '../service/messaging.service';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { CommonModule } from '@angular/common';
import { Chat } from '../interfaces/chat';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit{


  messageForm!: FormGroup;
  submitted = false;
  message: string = "";
  username : string = "";

  messages : Chat[] = [];

  constructor(private formBuilder: FormBuilder, private messageService: MessagingService, private router : Router, private loginService: LoginService) {}

  ngOnInit(): void {
    this.username = this.loginService.username;
    this.messageForm = this.formBuilder.group({
      message: ['']
    });
  }

  onSubmit() {
    console.log(this.messageForm.value.message + " " + this.username);
    }
}
