import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { MessagingService } from '../service/messaging.service';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';
import { CommonModule } from '@angular/common';
import { Message } from '../interfaces/message';
import { User } from '../interfaces/user';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
})
export class ChatComponent implements OnInit {

  users: User[] = [];
  supportConnected!: User;
  connectedUser: User = this.loginService.connectedUser;
  username!: string;

  messageForm!: FormGroup;
  submitted = false;
  message: string = '';
  messages: Message[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessagingService,
    private router: Router,
    private loginService: LoginService
  ) {}

  /**
   * establishment of the connection and subscription to receive messages
   * directly when they are added to the list
   */
  ngOnInit(): void {
    this.userList();
    this.username = this.loginService.connectedUser.username;
    this.messageForm = this.formBuilder.group({
      message: [''],
    });
    this.messageService.connect();
    this.messageService.getMessages().subscribe((message: any) => {
      this.messages.push(message);
    });
  }

  /**
   * we check if the user is the support or client in order to
   * establish the connection betweend client and support and not 
   * between support and itself.
   * 
   */
  onSubmit() {
    let messageToSend;

    if (this.messages.length > 0 && this.username == 'support') {
      let receiverId = this.messages[0].senderId;
      let recieverName = this.messages[0].senderName;
      messageToSend = {
        senderId: this.connectedUser.id,
        senderName: this.connectedUser.username,
        receiverId: receiverId,
        receiverName: recieverName,
        content: this.messageForm.value.message,
        date: Date.now().toString(),
      };
    } else {
      messageToSend = {
        senderId: this.connectedUser.id,
        senderName: this.connectedUser.username,
        receiverId: this.supportConnected.id,
        receiverName: this.supportConnected.username,
        content: this.messageForm.value.message,
        date: Date.now().toString(),
      };
    }
    this.messageService.sendMessage(messageToSend);
    this.messageService.getMessages();
  }

  /**
   * To establish if a support exists among users and if he is connected 
   */
  userList() {
    this.messageService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.supportConnected = this.users
          .filter((user) => user.role == 'support')
          .filter((result) => result.status == true)[0];
      },
    });
  }

  /**
   * disconnect from the socket and return to main page
   */
  logout() {
    this.loginService.logout().subscribe({
      next: () => {
        this.messageService.disconnect();
        this.router.navigateByUrl('/');
      },
    });
  }
}
