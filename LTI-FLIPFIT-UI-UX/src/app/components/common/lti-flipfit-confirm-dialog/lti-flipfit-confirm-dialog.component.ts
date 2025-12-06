import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-lti-flipfit-confirm-dialog',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatDialogModule],
  templateUrl: './lti-flipfit-confirm-dialog.component.html',
  styleUrl: './lti-flipfit-confirm-dialog.component.scss'
})
export class LtiFlipFitConfirmDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<LtiFlipFitConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title: string; message: string; confirmText?: string; cancelText?: string }
  ) {}

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
