import { responseJson } from "src/app/types"
import { TokenService } from "../token/token.service"

export abstract class AbstractAjax {

    public constructor(private readonly _tokenService: TokenService) { }

    protected _token: string = this._tokenService.getToken()
    protected readonly SERVER_API_VERSION = 'v1.0'

    public set token(value: string) {
        this._token = value
    }

    protected get SERVER_API_URL(): string {
        return (location.origin.includes('127.0.0.1') || location.origin.includes('localhost'))
            ? `http://127.0.0.1:8888/api/${this.SERVER_API_VERSION}` : location.origin + `/api/${this.SERVER_API_VERSION}`
    }

    protected getDefaultHeaders(): any {
        return {
            'Content-Type': 'application/json',
        }
    }

    protected getHeadersWithToken(headers: any): any {
        headers['Authorization'] = this._token
        return headers
    }

    protected prepareResponseBody(response: any, status: number): responseJson<Promise<any>> {
        return {
            response: response,
            status: status
        }
    }
}