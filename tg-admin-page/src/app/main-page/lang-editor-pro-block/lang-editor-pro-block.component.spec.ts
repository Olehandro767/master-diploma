import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LangEditorProBlockComponent } from './lang-editor-pro-block.component';

describe('LangEditorProBlockComponent', () => {
  let component: LangEditorProBlockComponent;
  let fixture: ComponentFixture<LangEditorProBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LangEditorProBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LangEditorProBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
