import { ajaxFunc, tokenResponse } from "src/app/types"

export interface IAjax {

    signIn(login: string, password: string, funcs: ajaxFunc<tokenResponse>): void

    checkSession(funcs: ajaxFunc<tokenResponse>): void
}