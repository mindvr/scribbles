import {Observable, of} from "rxjs";
import {concatAll, map} from "rxjs/operators";

let abc = of('a', 'b', 'c');

// 1 level unpacking
abc.pipe(
    map(x => of(x)),
    concatAll(),
).subscribe(x => console.log(x))

// 2 level unpack - no
abc.pipe(
    map(x => of(x)),
    map(x => of(x)),
    concatAll(),
).subscribe(x => console.log(x))

// simple multiplexing (with flattening)
abc.pipe(
    map(x => of(x, x)),
    concatAll(),
).subscribe(x => console.log(x))

// wait for completion
const one = new Observable(subscriber => {
    console.log('subscribed to one')
    subscriber.next(11)
    subscriber.next(12)
})
const two = new Observable(subscriber => {
    console.log('subscribed to two')
    subscriber.next(21)
    subscriber.next(22)
})

of(one, two).pipe(concatAll()).subscribe(x => console.log(x));

// wait for completion
const uno = new Observable(subscriber => {
    console.log('subscribed to uno')
    subscriber.next(11)
    subscriber.next(12)
    subscriber.complete()
    console.log('completed uno')
})
const dos = new Observable(subscriber => {
    console.log('subscribed to dos')
    subscriber.next(21)
    subscriber.next(22)
    subscriber.complete()
    console.log('completed dos')
})

of(uno, dos).pipe(concatAll()).subscribe(x => console.log(x));