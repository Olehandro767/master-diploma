import { TestBed } from '@angular/core/testing';

import { AjaxServiceService } from './ajax-service.service';

describe('AjaxServiceService', () => {
  let service: AjaxServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AjaxServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
