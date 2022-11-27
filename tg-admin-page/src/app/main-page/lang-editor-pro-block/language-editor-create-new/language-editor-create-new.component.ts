import { Component, OnInit } from '@angular/core';
import { FetchAjaxService } from 'src/app/service/ajax/ajax.service';

@Component({
  selector: 'app-language-editor-create-new',
  templateUrl: './language-editor-create-new.component.html',
  styleUrls: ['./language-editor-create-new.component.css']
})
export class LanguageEditorCreateNewComponent implements OnInit {

  public constructor(
    private readonly _ajax: FetchAjaxService,
  ) { }

  public ngOnInit(): void { }

  public accept(): void { }
}