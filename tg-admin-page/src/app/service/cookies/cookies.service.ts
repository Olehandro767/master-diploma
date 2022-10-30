import { Injectable } from '@angular/core';
import Cookies from 'js-cookie';

@Injectable({
  providedIn: 'root'
})
export class CookiesService {

  private jsCookies = Cookies

  public getByKey(key: string): string | null {
    let value = this.jsCookies.get(key)
    return (value != undefined) ? value : null
  }

  public set(key: string, value: string | number): void {
    this.jsCookies.set(key, `${value}`)
  }
}
