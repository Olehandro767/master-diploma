import { Component, OnInit } from '@angular/core';
import { LanguageService } from 'src/app/service/language/language.service';

@Component({
  selector: 'app-announcement-block',
  templateUrl: './announcement-block.component.html',
  styleUrls: ['./announcement-block.component.css']
})
export class AnnouncementBlockComponent implements OnInit {

  public constructor(
    public readonly languageService: LanguageService,
  ) { }

  public ngOnInit(): void {
  }
}
