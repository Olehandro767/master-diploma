import { Component, OnInit } from '@angular/core';
import { FetchAjaxService } from 'src/app/service/ajax/ajax.service';
import { LanguageService } from 'src/app/service/language/language.service';
import { MainPageContextService } from 'src/app/service/main-page-context/main-page-context.service';

@Component({
  selector: 'app-tg-activity-editor-update',
  templateUrl: './tg-activity-editor-update.component.html',
  styleUrls: ['./tg-activity-editor-update.component.css']
})
export class TgActivityEditorUpdateComponent implements OnInit {

  public activityContent: string = ''
  private _activityContentTemp: string = ''

  public constructor(
    public readonly mainPageContext: MainPageContextService,
    public readonly languageService: LanguageService,
    private readonly _ajax: FetchAjaxService,
  ) { }

  public ngOnInit(): void {
    this._ajax.telegramBotGetActivityContent(this.mainPageContext.mainComponentContext.activityName, {
      succes: response => {
        this.activityContent = response.response
        this._activityContentTemp = this.activityContent
      },
      onError: () => { },
    })
  }

  public accept(): void {
    if (this.activityContent !== this._activityContentTemp) {
      this._ajax.telegramBotUpdateActivity(
        {
          activityName: this.mainPageContext.mainComponentContext.activityName,
          newContent: this.activityContent,
        },
        {
          succes: () => {
            this.mainPageContext.mainComponentContext.pageName = ''
          },
          onError: () => { },
        })
    }
  }
}