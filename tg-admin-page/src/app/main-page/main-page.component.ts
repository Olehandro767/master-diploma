import { Component, OnInit } from '@angular/core';
import { MAIN_COMPONENT_NAMES } from '../activity-names';
import { LanguageService } from '../service/language/language.service';
import { MainPageContextService } from '../service/main-page-context/main-page-context.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
  providers: [MainPageContextService]
})
export class MainPageComponent implements OnInit {

  public componentNames = MAIN_COMPONENT_NAMES

  public constructor(
    public readonly languageService: LanguageService,
    public readonly mainPageContext: MainPageContextService,
  ) { }

  public ngOnInit(): void { }

  public clickOnSideMenuButton(componentName: string): void {
    this.mainPageContext.mainComponentName = componentName
  }
}