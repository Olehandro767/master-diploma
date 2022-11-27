import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { MainPageComponent } from './main-page/main-page.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AnnouncementBlockComponent } from './main-page/announcement-block/announcement-block.component';
import { LangEditorProBlockComponent } from './main-page/lang-editor-pro-block/lang-editor-pro-block.component';
import { TgActivityEditorProBlockComponent } from './main-page/tg-activity-editor-pro-block/tg-activity-editor-pro-block.component';
import { TgActivityEditorCreateNewComponent } from './main-page/tg-activity-editor-pro-block/tg-activity-editor-create-new/tg-activity-editor-create-new.component';
import { TgActivityEditorUpdateComponent } from './main-page/tg-activity-editor-pro-block/tg-activity-editor-update/tg-activity-editor-update.component';
import { LanguageEditorUpdateComponent } from './main-page/lang-editor-pro-block/language-editor-update/language-editor-update.component';
import { LanguageEditorCreateNewComponent } from './main-page/lang-editor-pro-block/language-editor-create-new/language-editor-create-new.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    MainPageComponent,
    AnnouncementBlockComponent,
    LangEditorProBlockComponent,
    TgActivityEditorProBlockComponent,
    TgActivityEditorCreateNewComponent,
    TgActivityEditorUpdateComponent,
    LanguageEditorUpdateComponent,
    LanguageEditorCreateNewComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
