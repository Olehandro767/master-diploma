import { Component, OnInit } from '@angular/core';
import { LanguageService } from '../service/language/language.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  public constructor(
    public readonly languageService: LanguageService,
  ) { }

  public ngOnInit(): void { }
}
