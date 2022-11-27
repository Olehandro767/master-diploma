import { Component, OnInit } from '@angular/core';
import { FetchAjaxService } from 'src/app/service/ajax/ajax.service';
import { CachingService } from 'src/app/service/caching/caching.service';
import { LanguageService } from 'src/app/service/language/language.service';
import { MainPageContextService } from 'src/app/service/main-page-context/main-page-context.service';

@Component({
  selector: 'app-language-editor-update',
  templateUrl: './language-editor-update.component.html',
  styleUrls: ['./language-editor-update.component.css']
})
export class LanguageEditorUpdateComponent implements OnInit {

  public languagePackContent: string = ''
  private _languagePackContentTemp: string = ''

  public constructor(
    private readonly _ajax: FetchAjaxService,
    private readonly _cachingService: CachingService,
    public readonly mainPageContext: MainPageContextService,
    public readonly languageService: LanguageService,
  ) { }

  public ngOnInit(): void {
    this._ajax.telegramBotGetLanguagePackContent(this.mainPageContext.mainComponentContext.languagePackName, {
      succes: response => {
        this.languagePackContent = response.response
        this._languagePackContentTemp = this.languagePackContent
      },
      onError: () => { },
    })
  }

  public accept(): void {
    if (this.languagePackContent !== this._languagePackContentTemp) {
      this._ajax.telegramBotUpdateLanguagePack({
        languageCode: this.mainPageContext.mainComponentContext.languagePackName,
        newContent: this.languagePackContent
      }, {
        succes: () => {
          this.mainPageContext.mainComponentContext.pageName = ''
          this._cachingService.setUseKey('LANGUAGE_NAMES', [])
        },
        onError: () => { },
      })
    }
  }
}