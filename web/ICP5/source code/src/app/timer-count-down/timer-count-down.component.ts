
import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { Subscription, interval } from 'rxjs';
export interface DialogData {
  timerDate: Date;
}
@Component({
  selector: 'app-timer-count-down',
  templateUrl: './timer-count-down.component.html',
  styleUrls: ['./timer-count-down.component.scss']
})

export class TimerCountDownComponent implements OnInit, OnDestroy {


    private subscription: Subscription;
    minDate: Date;
    public dateNow = new Date();
    public dDay = new Date('Jan 01 2022 00:00:00');
    milliSecondsInASecond = 1000;
    hoursInADay = 24;
    minutesInAnHour = 60;
    SecondsInAMinute  = 60;

    public timeDifference;
    public secondsToDday;
    public minutesToDday;
    public hoursToDday;
    public daysToDday;
    public dateToggle = false;

    
    private getTimeDifference () {
        this.timeDifference = this.dDay.getTime() - new  Date().getTime();
        this.allocateTimeUnits(this.timeDifference);
    }

  private allocateTimeUnits (timeDifference) {
        this.secondsToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond) % this.SecondsInAMinute);
        this.minutesToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * this.minutesInAnHour) % this.SecondsInAMinute);
        this.hoursToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * this.minutesInAnHour * this.SecondsInAMinute) % this.hoursInADay);
        this.daysToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * this.minutesInAnHour * this.SecondsInAMinute * this.hoursInADay));
  }
  updateTimer(){
    this.subscription.unsubscribe();
    this.dDay= new Date(this.dDay.toString())
    
    this.subscription = interval(1000)
           .subscribe(x => { this.getTimeDifference(); });
  }

    ngOnInit() {
       this.subscription = interval(1000)
           .subscribe(x => { this.getTimeDifference(); });
    }

   ngOnDestroy() {
      this.subscription.unsubscribe();
   }
   openDateDialog() {
      this.dateToggle = !this.dateToggle;
      if(this.dateToggle){
        this.minDate = new Date()
      }
    };
  }