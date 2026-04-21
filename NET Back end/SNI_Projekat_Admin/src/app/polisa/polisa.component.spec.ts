import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PolisaComponent } from './polisa.component';

describe('PolisaComponent', () => {
  let component: PolisaComponent;
  let fixture: ComponentFixture<PolisaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PolisaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PolisaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
