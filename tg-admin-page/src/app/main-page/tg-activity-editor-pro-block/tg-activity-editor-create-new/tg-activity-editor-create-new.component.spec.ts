import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TgActivityEditorCreateNewComponent } from './tg-activity-editor-create-new.component';

describe('TgActivityEditorCreateNewComponent', () => {
  let component: TgActivityEditorCreateNewComponent;
  let fixture: ComponentFixture<TgActivityEditorCreateNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TgActivityEditorCreateNewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TgActivityEditorCreateNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
