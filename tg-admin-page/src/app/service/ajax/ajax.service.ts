import { Injectable } from '@angular/core';
import { activityNames, ajaxFunc, languageNames, newActivity, newLanguagePack, responseJson, tokenResponse, updatedActivity, updatedLanguagePack } from 'src/app/types';
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

  private doFuncsWithoutResponse(response: Response, funcs: ajaxFunc<any>): void {
    if (response.status >= 400) {
      funcs.onError({ response: null, status: response.status })
    } else {
      funcs.succes({ response: null, status: response.status })
    }
  }

  private checkStatusAndReturnText(response: Response, funcs: ajaxFunc<any>): responseJson<Promise<any>> | null {
    if (response.status >= 400) {
      funcs.onError({ response: response, status: response.status })
      return null
    }

    return this.prepareResponseBody(response.text(), response.status)
  }

  private checkStatus(response: Response, funcs: ajaxFunc<any>): responseJson<Promise<any>> | null {
    if (response.status >= 400) {
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

  public telegramBotGetExampleActivityContent(funcs: ajaxFunc<string>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/activity/get-example-content`, {
      method: 'GET',
      headers: this.getHeadersWithToken({})
    }).then(response => this.checkStatusAndReturnText(response, funcs)).then(response => {
      if (response != null) {
        response.response.then(text => {
          funcs.succes({ response: text, status: response.status })
        })
      }
    })
  }

  public telegramBotCreateActivity(activity: newActivity, funcs: ajaxFunc<null>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/activity/create`, {
      method: 'PUT',
      headers: this.getHeadersWithToken(this.getDefaultHeaders()),
      body: JSON.stringify(activity)
    }).then(response => this.doFuncsWithoutResponse(response, funcs))
  }

  public telegramBotGetAllActivities(funcs: ajaxFunc<activityNames>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/activity/get-all`, {
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

  public telegramBotGetActivityContent(activityName: string, funcs: ajaxFunc<string>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/activity/get-content?activityName=${activityName}`, {
      method: 'GET',
      headers: this.getHeadersWithToken({})
    }).then(response => this.checkStatusAndReturnText(response, funcs)).then(response => {
      if (response != null) {
        response.response.then(text => {
          funcs.succes({ response: text, status: response.status })
        })
      }
    })
  }

  public telegramBotUpdateActivity(activity: updatedActivity, funcs: ajaxFunc<null>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/activity/update`, {
      method: 'PUT',
      headers: this.getHeadersWithToken(this.getDefaultHeaders()),
      body: JSON.stringify(activity)
    }).then(response => this.doFuncsWithoutResponse(response, funcs))
  }

  public telegramBotDeleteActivity(activityName: string, funcs: ajaxFunc<null>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/activity/delete?activityName=${activityName}`, {
      method: 'DELETE',
      headers: this.getHeadersWithToken({})
    }).then(response => this.doFuncsWithoutResponse(response, funcs))
  }

  public telegramBotCreateLanguagePack(languagePack: newLanguagePack, funcs: ajaxFunc<null>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/language/create`, {
      method: 'PUT',
      headers: this.getHeadersWithToken(this.getDefaultHeaders()),
      body: JSON.stringify(languagePack)
    }).then(response => this.doFuncsWithoutResponse(response, funcs))
  }

  public telegramBotGetAllLanguagePacks(funcs: ajaxFunc<languageNames>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/language/get-all`, {
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

  public telegramBotGetLanguagePackContent(languagePackName: string, funcs: ajaxFunc<string>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/language/get-content?languagePackName=${languagePackName}`, {
      method: 'GET',
      headers: this.getHeadersWithToken({})
    }).then(response => this.checkStatusAndReturnText(response, funcs)).then(response => {
      if (response != null) {
        response.response.then(text => {
          funcs.succes({ response: text, status: response.status })
        })
      }
    })
  }

  public telegramBotUpdateLanguagePack(languagePack: updatedLanguagePack, funcs: ajaxFunc<null>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/language/update`, {
      method: 'PUT',
      headers: this.getHeadersWithToken(this.getDefaultHeaders()),
      body: JSON.stringify(languagePack)
    }).then(response => this.doFuncsWithoutResponse(response, funcs))
  }

  public telegramBotDeleteLanguagePack(languagePackName: string, funcs: ajaxFunc<null>): void {
    fetch(`${this.SERVER_API_URL}/telegram-bot/language/delete?languagePackName=${languagePackName}`, {
      method: 'DELETE',
      headers: this.getHeadersWithToken({})
    }).then(response => this.doFuncsWithoutResponse(response, funcs))
  }
}