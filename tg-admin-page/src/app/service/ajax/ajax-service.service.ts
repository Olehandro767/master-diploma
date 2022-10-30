import { Injectable } from '@angular/core';
import { AbstractAjax } from './ajax.abstract-class';

@Injectable({
  providedIn: 'root'
})
export class AjaxServiceService extends AbstractAjax {

  public constructor() {
    super();
  }
}
