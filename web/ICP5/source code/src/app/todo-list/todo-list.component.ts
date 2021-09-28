import {Component, Inject} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MatTableDataSource, MAT_DIALOG_DATA } from '@angular/material';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}
export interface DialogData {
  animal: string;
  name: string;
}


/**
 * @title Basic use of `<table mat-table>`
 */
@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent {
  constructor(public dialog: MatDialog){

  }
  count=0;
  ELEMENT_DATA= [
    {position: 1, name: 'Rajesh', weight: "Algorithms", symbol: new Date(),count:0},

  ];
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol','actions'];
  dataSource = new MatTableDataSource(this.ELEMENT_DATA);
  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '350px',
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if(result!="noo"){
        
      this.count = this.count + 1;
      
      this.ELEMENT_DATA.push({position:result.number,name:result.name,weight:result.subject,symbol:result.dueDate,count:this.count});
      this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
    }
    });
    // this.ELEMENT_DATA.
  
  }
   
deleteval(val){
 var a= this.ELEMENT_DATA.findIndex(v=>v.count==val.count)
 this.ELEMENT_DATA.splice(a,1)
 this.dataSource = new MatTableDataSource(this.ELEMENT_DATA);
// console.log(a)
}
  
}
@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: 'dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {
  serviceform: FormGroup = new FormGroup({
    number: new FormControl('',[Validators.required, Validators.pattern('[0-9]+')]),
    name: new FormControl('',  [Validators.required, Validators.minLength(4)]),
    subject: new FormControl('', [Validators.required, Validators.minLength(2)]),
    dueDate: new FormControl('',Validators.required),
  });
  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}
onSubmit(){
  this.dialogRef.close(this.serviceform.value)
}
  onNoClick(): void {
    this.dialogRef.close("noo");
  }

}