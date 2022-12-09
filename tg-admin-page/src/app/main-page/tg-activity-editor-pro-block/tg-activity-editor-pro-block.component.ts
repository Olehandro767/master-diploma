import { Component, OnInit } from '@angular/core';
import { FetchAjaxService } from 'src/app/service/ajax/ajax.service';
import { CachingService } from 'src/app/service/caching/caching.service';
import { LanguageService } from 'src/app/service/language/language.service';
import { MainPageContextService } from 'src/app/service/main-page-context/main-page-context.service';

@Component({
  selector: 'app-tg-activity-editor-pro-block',
  templateUrl: './tg-activity-editor-pro-block.component.html',
  styleUrls: ['./tg-activity-editor-pro-block.component.css']
})
export class TgActivityEditorProBlockComponent implements OnInit {

  public createNewActivityPageIsOpen: boolean = false
  public activitiesIsChecked: boolean = false
  public activities: string[] = []
  private _deleteClicked = false

  public constructor(
    public readonly languageService: LanguageService,
    public readonly mainPageContext: MainPageContextService,
    private readonly _ajax: FetchAjaxService,
    private readonly _cachingService: CachingService,
  ) {
    let localActivities = _cachingService.getByKey<string[]>('ACTIVITIES_NAMES')
    this.activities = (localActivities != null) ? localActivities : []
    mainPageContext.mainComponentContext = {
      activityName: '',
      pageName: '',
      update: () => {
        this.getAllActivities()
      }
    }
  }

  private getAllActivities(): void {
    this._ajax.telegramBotGetAllActivities({
      succes: response => {
        this.activities = response.response.names
        this.activitiesIsChecked = true

        if (this.activities.length !== 0) {
          let expiredDate = new Date()
          expiredDate.setMinutes(expiredDate.getMinutes() + 15)
          this._cachingService.setExpiredUseKey(expiredDate, 'ACTIVITIES_NAMES', this.activities)
        }
      },
      onError: () => { },
    })
  }

  public ngOnInit(): void {
    if (!this.activitiesIsChecked && this.activities.length === 0) {
      this.getAllActivities()
    }
  }

  public remove(activityName: string): void {
    this._deleteClicked = true
    if (confirm(this.languageService.langDictionary.are_you_shure)) {
      this._ajax.telegramBotDeleteActivity(activityName, {
        succes: () => {
          this.getAllActivities()
          this._deleteClicked = false
        },
        onError: () => { },
      })
    }
  }

  public openEditor(activityName: string): void {
    if (!this._deleteClicked) {
      this.mainPageContext.mainComponentContext.activityName = activityName
      this.mainPageContext.mainComponentContext.pageName = 'update activity'
    }
  }
}
