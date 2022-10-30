import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnouncementBlockComponent } from './announcement-block.component';

describe('AnnouncementBlockComponent', () => {
  let component: AnnouncementBlockComponent;
  let fixture: ComponentFixture<AnnouncementBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnnouncementBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnouncementBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
