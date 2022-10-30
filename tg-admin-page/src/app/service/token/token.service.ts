import { Injectable } from '@angular/core';
import { CookiesService } from '../cookies/cookies.service';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  public constructor(private _cookiesService: CookiesService) { }

  public setToken(token: string): void {
    this._cookiesService.set('token', token)
  }

  public getToken(): string {
    return (this.tokenExists()) ? this._cookiesService.getByKey('token')!! : ''
  }

  public tokenExists(): boolean {
    return this._cookiesService.getByKey('token') != null
  }
}
