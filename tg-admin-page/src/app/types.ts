export type responseJson<T> = {
    response: T,
    status: number
}

export type tokenResponse = {
    token: string
}

export type ajaxFunc<T> = {
    succes: (arg1: responseJson<T>) => void,
    onError: (arg1: responseJson<any>) => void
}