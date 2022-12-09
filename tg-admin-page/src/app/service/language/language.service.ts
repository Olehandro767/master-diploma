import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  public langDictionary = {
    loginPageLoginField: "Логін",
    loginPagePasswordField: "Пароль",
    loginPageWrongPasswordMessage: "Не правильний пароль",
    mainPageButtonTitleLangEditorPro: "Мовний пакет для боту",
    mainPageButtonTitleTgActivityEditorPro: "Управління Telegram ботом",
    mainPageButtonTitlePostAnAd: "Опублікувати оголошення",
    mainPage_announcementPageButtonTitleAccept: "Прийняти",
    activity_field: 'Назва телеграм меню',
    content_field: 'Контент',
    are_you_shure: 'Ви впевнені?',
  }
}