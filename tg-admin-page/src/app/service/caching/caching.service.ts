import { Injectable } from '@angular/core';
import { CookiesService } from '../cookies/cookies.service';

@Injectable({
  providedIn: 'root'
})
export class CachingService {

  public constructor(private readonly _cookieService: CookiesService) {
    _cookieService.set('TIMERS', '{}')
  }

  private getTimers(): any {
    let timers: any = this._cookieService.getByKey('TIMERS')

    if (timers != null) {
      timers = JSON.parse(timers)
    } else {
      timers = {}
    }

    return timers
  }

  public getByKey<T>(key: string): T | null {
    let data: string | null = this._cookieService.getByKey(key)
    let result: T | null = null
    let timer = this.getTimers()[key]

    if (timer == undefined || timer > new Date()) {
      if (data != null) {
        try {
          result = JSON.parse(data)
        } catch (_) {
          result = data as any
        }
      }
    }

    return result;
  }

  public setUseKey(key: string, data: any): void {
    let cachedData: string = ''
    const typeOfData = typeof data

    if (typeOfData == 'object') {
      cachedData = JSON.stringify(data)
    } else {
      cachedData = `${data}`
    }

    this._cookieService.set(key, cachedData)
  }

  public setExpiredUseKey(date: Date, key: string, data: any): void {
    const timers = this.getTimers()
    timers[key] = date
    this._cookieService.set('TIMERS', JSON.stringify(timers))
    this.setUseKey(key, data)
  }
}