import { TestBed } from '@angular/core/testing';

import { MainPageContextService } from './main-page-context.service';

describe('MainPageContextService', () => {
  let service: MainPageContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MainPageContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
