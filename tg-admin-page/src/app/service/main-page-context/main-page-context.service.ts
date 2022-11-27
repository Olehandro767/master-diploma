import { Injectable } from '@angular/core';

@Injectable()
export class MainPageContextService {

  private _mainComponentName: string = ''
  private _mainComponentContext: any = {}

  public constructor() { }

  public get mainComponentName(): string {
    return this._mainComponentName
  }

  public set mainComponentName(value: string) {
    this._mainComponentName = value
  }

  public get mainComponentContext(): any {
    return this._mainComponentContext
  }

  public set mainComponentContext(value: any) {
    this._mainComponentContext = value
  }
}