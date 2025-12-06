import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GymSlotsDialogComponent } from './gym-slots-dialog.component';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';

describe('GymSlotsDialogComponent', () => {
  let component: GymSlotsDialogComponent;
  let fixture: ComponentFixture<GymSlotsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        GymSlotsDialogComponent,
        HttpClientTestingModule,
        MatDialogModule,
        MatSnackBarModule
      ],
      providers: [
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: {} }
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GymSlotsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
