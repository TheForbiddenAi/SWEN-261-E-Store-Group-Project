import { Component, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA, MatSnackBar, MatSnackBarRef } from '@angular/material/snack-bar';
import { SnackBarData } from './snackbar-data';

@Component({
  selector: 'app-snackbar-notification',
  templateUrl: './snackbar-notification.component.html',
  styleUrls: ['./snackbar-notification.component.css']
})
export class SnackbarNotificationComponent {

  constructor(
    private _snackbarRef: MatSnackBarRef<SnackbarNotificationComponent>,
    @Inject(MAT_SNACK_BAR_DATA) public data: SnackBarData
  ) { }

  /**
   * Closes the snackbar
   */
  close(): void {
    this._snackbarRef.dismiss();
  }

}
