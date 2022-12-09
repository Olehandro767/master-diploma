import { Component, OnInit } from '@angular/core';
import { FetchAjaxService } from 'src/app/service/ajax/ajax.service';
import { CachingService } from 'src/app/service/caching/caching.service';
import { LanguageService } from 'src/app/service/language/language.service';
import { MainPageContextService } from 'src/app/service/main-page-context/main-page-context.service';

@Component({
  selector: 'app-lang-editor-pro-block',
  templateUrl: './lang-editor-pro-block.component.html',
  styleUrls: ['./lang-editor-pro-block.component.css']
})
export class LangEditorProBlockComponent implements OnInit {

  public languageNames: string[] = []
  private _languageNamesIsChecked: boolean = false

  public constructor(
    private readonly _ajax: FetchAjaxService,
    private readonly _cachingService: CachingService,
    public readonly mainPageContext: MainPageContextService,
    public readonly languageService: LanguageService,
  ) {
    let localLanguageNames = _cachingService.getByKey<string[]>('LANGUAGE_NAMES')
    this.languageNames = (localLanguageNames != null) ? localLanguageNames : []
    mainPageContext.mainComponentContext = {
      languagePackName: '',
      pageName: '',
      update: () => {
        this.getAll()
      }
    }
  }

  private getAll(): void {
    this._ajax.telegramBotGetAllLanguagePacks({
      succes: response => {
        this.languageNames = response.response.names
        let expiredDate = new Date()
        expiredDate.setMinutes(expiredDate.getMinutes() + 15)
        this._cachingService.setExpiredUseKey(expiredDate, 'LANGUAGE_NAMES', this.languageNames)
      },
      onError: () => { },
    })
  }

  public ngOnInit(): void {
    if (!this._languageNamesIsChecked && this.languageNames.length === 0) {
      this.getAll()
    }
  }

  public openEditor(languageName: string): void {
    this.mainPageContext.mainComponentContext.pageName = 'update language pack'
    this.mainPageContext.mainComponentContext.languagePackName = languageName
  }

  public remove(languageName: string): void {
    if (confirm(this.languageService.langDictionary.are_you_shure)) {
      this._ajax.telegramBotDeleteLanguagePack(languageName, {
        succes: () => {
          this.getAll()
        },
        onError: () => { },
      })
    }
  }
}