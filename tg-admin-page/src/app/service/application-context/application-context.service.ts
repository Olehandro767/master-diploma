import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApplicationContextService {

  private _applicationActivity: string = ''

  public constructor() { }

  public get applicationActivity(): string {
    return this._applicationActivity
  }

  public set applicationActivity(value: string) {
    this._applicationActivity = value
  }
}
