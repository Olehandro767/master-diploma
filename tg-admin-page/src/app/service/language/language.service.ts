import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  public langDictionary = {
    loginPageLoginField: "Логін",
    loginPagePasswordField: "Пароль",
    loginPageWrongPasswordMessage: "Не правильний пароль",
    mainPageButtonTitleLangEditorPro: "Мовний пакет для боту (PRO)",
    mainPageButtonTitleTgActivityEditorPro: "Управління Telegram ботом (PRO)",
    mainPageButtonTitlePostAnAd: "Опублікувати оголошення",
    mainPage_announcementPageButtonTitleAccept: "Прийняти",
  }
}