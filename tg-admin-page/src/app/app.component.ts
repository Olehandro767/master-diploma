import { Component, OnInit } from '@angular/core';
import { ACTIVITY_NAMES } from './activity-names';
import { FetchAjaxService } from './service/ajax/ajax.service';
import { ApplicationContextService } from './service/application-context/application-context.service';
import { TokenService } from './service/token/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public constructor(
    private readonly _tokenService: TokenService,
    private readonly _ajax: FetchAjaxService,
    public readonly applicationContext: ApplicationContextService,
  ) { }

  public ngOnInit(): void {
    if (this._tokenService.tokenExists()) {
      this._ajax.checkSession({
        succes: (newToken) => {
          this._tokenService.setToken('ONTU ' + newToken)
          this._ajax.token = 'ONTU ' + newToken
          this.applicationContext.applicationActivity = ACTIVITY_NAMES.main
        },
        onError: (error) => {
          this.applicationContext.applicationActivity = ACTIVITY_NAMES.login
        },
      })
    } else {
      this.applicationContext.applicationActivity = ACTIVITY_NAMES.login
    }
  }
}