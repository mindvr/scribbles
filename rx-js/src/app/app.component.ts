import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';
import {CounterComponent} from "./counter/counter.component";
import {combineLatest, of, Subject} from "rxjs";
import {mergeAll, withLatestFrom} from "rxjs/operators";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, CounterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'rx-js';

  console1: string[] = [];
  console2: string[] = [];
  console3: string[] = [];

  main$ = new Subject<number>();
  first$ = new Subject<number>();
  second$ = new Subject<number>();

  ngOnInit(): void {
    combineLatest([this.main$, this.first$, this.second$])
      .subscribe(next => this.console1.push(JSON.stringify(next)))

    this.main$.pipe(
      withLatestFrom(this.first$, this.second$),
    ).subscribe(next => this.console2.push(JSON.stringify(next)))

    of(this.main$, this.first$, this.second$).pipe(mergeAll())
      .subscribe(next => this.console3.push(JSON.stringify(next)))

  }


}
