import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessagingService } from '../service/messaging.service';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { CommonModule } from '@angular/common';
import { Chat } from '../interfaces/chat';
import { User } from '../interfaces/user';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit{

  users: User[] = [];
  supportConnected: User[] = [];

  messageForm!: FormGroup;
  submitted = false;
  message: string = "";
  username : string = "";

  messages : Chat[] = [];

  constructor(private formBuilder: FormBuilder, private messageService: MessagingService, private router : Router, private loginService: LoginService) {}

  ngOnInit(): void {
    this.userList();
    this.username = this.loginService.username;
    this.messageForm = this.formBuilder.group({
      message: ['']
    });
  }

  onSubmit() {
    console.log(this.messageForm.value.message + " " + this.username);
    }

    userList() {
      this.messageService.getAllUsers().subscribe({
        next:(data) => {
          this.users = data;
          console.log(data);
          this.supportConnected = this.users.filter((user) => user.role == "support" ).filter((result) => result.status==true);
        }
      })
    }
}
