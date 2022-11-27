import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TgActivityEditorUpdateComponent } from './tg-activity-editor-update.component';

describe('TgActivityEditorUpdateComponent', () => {
  let component: TgActivityEditorUpdateComponent;
  let fixture: ComponentFixture<TgActivityEditorUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TgActivityEditorUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TgActivityEditorUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
