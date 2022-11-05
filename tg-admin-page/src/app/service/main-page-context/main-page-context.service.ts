import { Injectable } from '@angular/core';

@Injectable()
export class MainPageContextService {

  private _mainComponentName: string = ''

  public constructor() { }

  public get mainComponentName(): string {
    return this._mainComponentName
  }

  public set mainComponentName(value: string) {
    this._mainComponentName = value
  }
}