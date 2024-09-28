import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { Observable, Subject } from 'rxjs';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Message } from '../interfaces/message';

/**
 * This class allows a user to send messages to a user support, and for a user support to respond.
 * use : npm install @stomp/stompjs sockjs-client
 *       npm install @types/socket.io-client
 */
@Injectable({
  providedIn: 'root',
})
export class MessagingService {
  private stompClient: any;
  private messageSubject = new Subject<any>();

  constructor(private http: HttpClient) {}

  /**
   * Method used to get all the users
   * @returns an array of users
   * This method is needed for this version of our code to check the list of
   * connected users, and verify that there is at least one with the role "support"
   */
  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>('http://localhost:8080/users');
  }

  /**
   * Creation of a socket to the backend endpoint to establish connexion
   */
  connect() {
    const socket = new SockJS('http://localhost:8080/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, (frame: string) => {
      console.log('Connected: ' + frame);

      this.stompClient.subscribe('/chat/messages', (message: any) => {
        if (message.body) {
          this.messageSubject.next(JSON.parse(message.body));
        }
      });
    });
  }

  /**
   * Terminate the connexion between the user and the backend
   */
  disconnect() {
    this.stompClient.disconnect(() => {
      console.log('déconnecté');
    });
  }

  /**
   * Check that the connexion is established and send the message to the backend
   * @param message
   */
  sendMessage(message: Message) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(
        '/app/chat.sendMessage',
        {},
        JSON.stringify(message)
      );
    } else {
      console.error('STOMP client is not connected. Message not sent.');
    }
  }

  /**
   * Messages send and received during the current connexion are returned.
   * /!\ not the previous messages from older connexion.
   * @returns
   */
  getMessages() {
    return this.messageSubject.asObservable();
  }
}
