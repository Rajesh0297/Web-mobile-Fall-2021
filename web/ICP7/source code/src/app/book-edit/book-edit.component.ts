import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../api.service';

import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {
  bookObj={isbn:"hi"};
 
  constructor(private router: Router, private route: ActivatedRoute, private api: ApiService, private formBuilder: FormBuilder) {
    
  }

  ngOnInit() {
  this.api.getBook(this.route.snapshot.params['id'])
      .subscribe(data => {
        this.bookObj = data;   
      }); 
  }

 

  onSubmit() {
     let form={
       isbn: this.bookObj['isbn'],
       title: this.bookObj['title'],
       description: this.bookObj['description'],
       author: this.bookObj['author'],
       publisher: this.bookObj['publisher'],
       published_year: this.bookObj['published_year'],
     }
    this.api.updateBook(this.bookObj['_id'],form)
      .subscribe(res => {
        this.router.navigate(['/books']);
      }, (err) => {
        console.log(err);
      });
  }
}
