import { TestBed } from '@angular/core/testing';

import { CookiesServiceService } from './cookies-service.service';

describe('CookiesServiceService', () => {
  let service: CookiesServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CookiesServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
