import { Component, OnInit } from '@angular/core';
import { FetchAjaxService } from 'src/app/service/ajax/ajax.service';
import { CachingService } from 'src/app/service/caching/caching.service';
import { LanguageService } from 'src/app/service/language/language.service';
import { MainPageContextService } from 'src/app/service/main-page-context/main-page-context.service';
import { newActivity } from 'src/app/types';

@Component({
  selector: 'app-tg-activity-editor-create-new',
  templateUrl: './tg-activity-editor-create-new.component.html',
  styleUrls: ['./tg-activity-editor-create-new.component.css']
})
export class TgActivityEditorCreateNewComponent implements OnInit {

  public activityName: string = ''
  public activityContent: string = ''

  public constructor(
    public readonly languageService: LanguageService,
    public readonly mainPageContext: MainPageContextService,
    private readonly _cachingService: CachingService,
    private readonly _ajax: FetchAjaxService,
  ) { }

  public ngOnInit(): void {
    this._ajax.telegramBotGetExampleActivityContent({
      succes: response => {
        if (this.activityContent === '') {
          this.activityContent = response.response
        }
        if (this.activityName === '') {
          this.activityName = 'example-name'
        }
      },
      onError: () => { },
    })
  }

  public accept(): void {
    let body: newActivity = {
      activityName: this.activityName,
      activityContent: this.activityContent,
    }
    this._ajax.telegramBotCreateActivity(body, {
      succes: () => {
        let expiredDate = new Date()
        expiredDate.setMinutes(expiredDate.getMinutes() + 15)
        this._cachingService.setExpiredUseKey(expiredDate, 'ACTIVITIES_NAMES', [])
        this.mainPageContext.mainComponentContext.update()
        this.mainPageContext.mainComponentContext.pageName = ''
      },
      onError: () => { },
    })
  }
}