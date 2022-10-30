import { Injectable } from '@angular/core';
import Cookies from 'js-cookie';

@Injectable({
  providedIn: 'root'
})
export class CookiesServiceService {

  private jsCookies = Cookies

  public constructor() { }
}
