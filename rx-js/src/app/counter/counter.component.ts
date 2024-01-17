import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-counter',
  standalone: true,
  imports: [],
  templateUrl: './counter.component.html',
})
export class CounterComponent {
  @Input() name: string = '';
  @Output() count$ = new EventEmitter<number>();

  counter = 0;

  constructor() { }

  ngOnInit(): void {
  }

  incrementCounter() {
    this.counter++;
    this.count$.emit(this.counter);
  }
}
