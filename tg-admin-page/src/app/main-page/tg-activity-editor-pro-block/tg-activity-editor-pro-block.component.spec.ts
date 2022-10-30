import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TgActivityEditorProBlockComponent } from './tg-activity-editor-pro-block.component';

describe('TgActivityEditorProBlockComponent', () => {
  let component: TgActivityEditorProBlockComponent;
  let fixture: ComponentFixture<TgActivityEditorProBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TgActivityEditorProBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TgActivityEditorProBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
