import { TestBed } from '@angular/core/testing';

import { MyFormService } from './my-form.service';

describe('MyFormService', () => {
  let service: MyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
