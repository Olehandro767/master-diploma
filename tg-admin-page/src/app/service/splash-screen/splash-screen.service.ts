import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SplashScreenService {

  private readonly _splashScreenElement: HTMLElement = document.getElementById("splash-screen")!
  private readonly _transitionSeconds: number = 0.5

  public get splashScreenElement(): HTMLElement {
    return this._splashScreenElement
  }

  public show(): void {
    const style = this._splashScreenElement.style
    style.top = `0px`
    style.transition = `${this._transitionSeconds}s`
  }

  public close(): void {
    const style = this._splashScreenElement.style
    style.top = `-${window.innerHeight + 100}px`
    style.transition = `${this._transitionSeconds}s`
  }
}