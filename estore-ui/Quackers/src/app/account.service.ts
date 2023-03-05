import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';

import { Observable,of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators'

import { Account } from './account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiURL = 'http://localhost:8080';
  
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) { }

  /**
   * Gets data of the account specified by the user.
   * 
   * If they enter a wrong information combo, it will not allow the user to proceed.
   * 
   * @param username The name of the account
   * @param password The password of the account
   * @returns The account with all the user data to be displayed and http ok
   * Invalid login information for an existing account returns Http Conflict
   * If the account doesn't exist, returns Http Not Found
   */
  login(username : string, password : string): Observable<Account> {
    const url = '${this.apiURL}/login?username=${username}&password=${password}';
    const url2 = this.apiURL + '/login?username=' + username + '&password=' + password;
    console.log(url2);
    return this.http.get<Account>(url2).pipe(
      tap(_ => console.log('${username} logged in')), catchError(this.handleError<Account>('login')));
  }

  /**
   * Saves any changes made to the account when logging out to the database 
   * @param account The account with the updated information to save to the database.
   * @returns http ok when saved, if there was an error it returns an http conflict
   */
  logout(account : Account): Observable<any> {
    return this.http.put(this.apiURL, account, this.httpOptions).pipe(
      tap(_ => console.log('${account.username logged out}')), catchError(this.handleError<any>('logout')));
  }

   /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
