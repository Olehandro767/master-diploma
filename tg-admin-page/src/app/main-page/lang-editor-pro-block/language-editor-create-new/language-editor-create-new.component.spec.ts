import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageEditorCreateNewComponent } from './language-editor-create-new.component';

describe('LanguageEditorCreateNewComponent', () => {
  let component: LanguageEditorCreateNewComponent;
  let fixture: ComponentFixture<LanguageEditorCreateNewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LanguageEditorCreateNewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageEditorCreateNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
