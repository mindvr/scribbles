# Empty AppStore Balance for Region Switch

## Overview

The script calculates various payment combinations to get to zero account balance.
Additional constrain considered is ability to add funds only by gift cards (which have minimum value).

## Usage
Run the script with the following command:
```
cat <input> | ./main.py > <output>.csv
```

### Input File
Create an input file with three lines:

1. **Prices Available**: List available prices (in cents) for purchasable items or services.
2. **Maximum Repetitions**: Maximum times each price can be used.
3. **Balance Details**: The exact balance to zero out, the minimum, and maximum additional amounts.

#### Example Input
```
449,199,15,99,199,249,349,449,499,699
2,3,5,2,2,1,1,1,1,1
500,10,100
```

### Understanding the Output
The script outputs a CSV file with columns:
- `total`: Total amount after combining prices.
- `delta`: Difference between the total and the exact amount.
- Price columns: Each representing the number of times that price is used in a combination.

#### Example Output
```
total,delta,449,199,15,99,199,249,349,449,499,699
1308,529,1,1,1,4,0,1,0,0,0,0
1309,530,1,1,1,3,0,0,1,0,0,0
1309,530,1,1,1,2,1,1,0,0,0,0
```

