import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageEditorUpdateComponent } from './language-editor-update.component';

describe('LanguageEditorUpdateComponent', () => {
  let component: LanguageEditorUpdateComponent;
  let fixture: ComponentFixture<LanguageEditorUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LanguageEditorUpdateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageEditorUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
