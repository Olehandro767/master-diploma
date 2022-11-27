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

export type newActivity = {
    activityName: string,
    activityContent: string,
}

export type updatedActivity = {
    activityName: string,
    newContent: string,
}

export type activityNames = {
    names: string[]
}

export type newLanguagePack = {
    languageCode: string,
    content: string,
}

export type languageNames = {
    names: string[]
}

export type updatedLanguagePack = {
    languageCode: string,
    newContent: string,
}