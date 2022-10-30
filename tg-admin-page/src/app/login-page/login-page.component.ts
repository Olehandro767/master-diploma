import { Component } from '@angular/core';
import { ACTIVITY_NAMES } from '../activity-names';
import { FetchAjaxService } from '../service/ajax/ajax.service';
import { ApplicationContextService } from '../service/application-context/application-context.service';
import { LanguageService } from '../service/language/language.service';
import { TokenService } from '../service/token/token.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  public login: string = ''
  public password: string = ''

  public constructor(
    private readonly _tokenService: TokenService,
    private readonly _ajax: FetchAjaxService,
    private readonly _applicationContext: ApplicationContextService,
    public readonly languageService: LanguageService,
  ) { }

  public onSubmit() {
    if (this.login != '' && this.password != '') {
      this._ajax.signIn(this.login, this.password, {
        succes: (response) => {
          this._tokenService.setToken('ONTU ' + response.response.token)
          this._ajax.token = 'ONTU ' + response.response.token
          this._applicationContext.applicationActivity = ACTIVITY_NAMES.main
        },
        onError: (error) => {
          if (error.status == 401) {
            alert(this.languageService.langDictionary.loginPageWrongPasswordMessage)
          }
        },
      })
    }
  }
}
