import { activityNames, ajaxFunc, languageNames, newActivity, newLanguagePack, tokenResponse, updatedActivity, updatedLanguagePack } from "src/app/types"

export interface IAjax {

    signIn(login: string, password: string, funcs: ajaxFunc<tokenResponse>): void

    checkSession(funcs: ajaxFunc<tokenResponse>): void

    /**
     * Telegram bot activity requests
     */
    telegramBotGetExampleActivityContent(funcs: ajaxFunc<string>): void

    telegramBotCreateActivity(activity: newActivity, funcs: ajaxFunc<null>): void

    telegramBotGetAllActivities(funcs: ajaxFunc<activityNames>): void

    telegramBotGetActivityContent(activityName: string, funcs: ajaxFunc<string>): void

    telegramBotUpdateActivity(activity: updatedActivity, funcs: ajaxFunc<null>): void

    telegramBotDeleteActivity(activityName: string, funcs: ajaxFunc<null>): void

    /**
     * Telegram bot language pack requests
     */
    telegramBotCreateLanguagePack(languagePack: newLanguagePack, funcs: ajaxFunc<null>): void

    telegramBotGetAllLanguagePacks(funcs: ajaxFunc<languageNames>): void

    telegramBotGetLanguagePackContent(languagePackName: string, funcs: ajaxFunc<string>): void

    telegramBotUpdateLanguagePack(languagePack: updatedLanguagePack, funcs: ajaxFunc<null>): void

    telegramBotDeleteLanguagePack(languagePackName: string, funcs: ajaxFunc<null>): void
}