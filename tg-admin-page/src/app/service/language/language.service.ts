import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  public langDictionary = {
    loginPageLoginField: "Логін",
    loginPagePasswordField: "Пароль",
    loginPageWrongPasswordMessage: "Не правильний пароль",
    mainPageButtonTitlePostAnAd: "Опублікувати оголошення",
  }
}