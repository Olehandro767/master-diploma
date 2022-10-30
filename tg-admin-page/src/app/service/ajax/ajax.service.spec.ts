import { TestBed } from '@angular/core/testing';

import { FetchAjaxService } from './ajax.service';

describe('AjaxService', () => {
  let service: FetchAjaxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FetchAjaxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
