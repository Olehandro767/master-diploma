import { Injectable } from '@angular/core';
import { ajaxFunc, responseJson, tokenResponse } from 'src/app/types';
import { TokenService } from '../token/token.service';
import { AbstractAjax } from './ajax.abstract-class';
import { IAjax } from './ajax.interface';

@Injectable({
  providedIn: 'root'
})
export class FetchAjaxService extends AbstractAjax implements IAjax {

  public constructor(tokenService: TokenService) {
    super(tokenService);
  }

  private checkStatus(response: Response, funcs: ajaxFunc<any>): responseJson<Promise<any>> | null {
    if (response.status > 300) {
      funcs.onError({ response: response, status: response.status })
      return null
    }

    return this.prepareResponseBody(response.json(), response.status)
  }

  public signIn(login: string, password: string, funcs: ajaxFunc<tokenResponse>): void {
    fetch(`${this.SERVER_API_URL}/admin/sign-in`, {
      method: 'POST',
      headers: this.getDefaultHeaders(),
      body: JSON.stringify({
        login: login,
        password: password,
      })
    }).then(response => this.checkStatus(response, funcs))
      .then(response => {
        if (response != null) {
          response.response.then(json => {
            funcs.succes({ response: json as tokenResponse, status: response.status })
          })
        }
      })
  }

  public checkSession(funcs: ajaxFunc<tokenResponse>): void {
    fetch(`${this.SERVER_API_URL}/admin/check-session`, {
      method: 'GET',
      headers: this.getHeadersWithToken({})
    }).then(response => this.checkStatus(response, funcs))
      .then(response => {
        if (response != null) {
          response.response.then(json => {
            funcs.succes({ response: json, status: response.status })
          })
        }
      })
  }
}
