# What's new in Java 21 compared to 17

Sources:
- [primary JEPs list](https://openjdk.org/projects/jdk/21/jeps-since-jdk-17)
- [official blog post](https://blogs.oracle.com/java/post/the-arrival-of-java-21)
- [Jose PAUMARD's talk](https://youtu.be/14RDbiF2a_o) - not equal to 21 scope

## [Project Amber](https://openjdk.org/projects/amber/)

### Pattern Matching

JDK21 additions regarding patterns build on top of constructs already present in JDK17:
- switch expressions
- pattern matching instanceof
- record classes

#### Switch _expressions_ as of JDK 17

They were added in [Java 14](https://openjdk.org/jeps/361).

- it's an expression, i.e. it returns a value and can be placed anywhere a variable can
- it's exhaustive: default clause is required or all available cases accounted for (with enums)
- switch variable is still limited to int, String and enum types
- switching over null will raise an NPE

Syntactically it stands out by addition of `->` syntax, lack of `break`, and `yield` replacing `return`.

```java
int j = switch (day) {
    case MONDAY -> 0;
    case TUESDAY -> 1;
    default -> {
        int k = day.toString().length();
        int result = f(k);
        yield result;
    }
};

// colon syntax
int result = switch (s) {
    case "Foo":
    case "Bar":
        yield 2;
    default:
        System.out.println("Neither Foo nor Bar, hmmm...");
        yield 0;
};
```

#### Type Patterns as of JDK 17

Added in [Java 16](https://openjdk.org/jeps/394) for instanceof operator. 

```java
//      |   type pattern  |        guard     |
if (obj instanceof String s && s.length() > 5) {
    flag = s.contains("jdk");
}
```

#### [JEP 441: Pattern Matching for switch](https://openjdk.org/jeps/441)

Pattern Matching added to switch expressions:
```java
Integer i = ...
switch (i) {
    case -1, 1 -> ...                   // Special cases
//      |type pattern|    guard   |
    case Integer jjjj when jjjj > 0 -> ...    // Positive integer cases
    case Integer j -> ...               // All the remaining integers
}
```

Aside from patterns:
1. cases are evaluated in natural order, for exhaustiveness check details refer to JEP
2. switch over null is allowed either by `case null`, or by combining with `case null, default`
```java
var x = switch (o) {
    case null -> "null";
    case String s -> "String!";
    default -> "default";
}
// null

var x = switch (o) {
    case String s -> "String!";
    case null, default -> "default or null";
}
//default or null

var x = switch (o) {
    case String s -> "String!";
    default -> "default";
};
// Exception in thread "main" java.lang.NullPointerException
```
3. switch variable type limitation is lifted, it's possible to refer to enum element via class
```java
sealed interface CardClassification permits Suit, Tarot {}
public enum Suit implements CardClassification { CLUBS, DIAMONDS, HEARTS, SPADES }
final class Tarot implements CardClassification {}

static void exhaustiveSwitchWithBetterEnumSupport(CardClassification c) {
    switch (c) {
        case Suit.CLUBS -> {
            System.out.println("It's clubs");
        }
        case Suit.DIAMONDS -> {
            System.out.println("It's diamonds");
        }
        case Suit.HEARTS -> {
            System.out.println("It's hearts");
        }
        case Suit.SPADES -> {
            System.out.println("It's spades");
        }
        case Tarot t -> {
            System.out.println("It's a tarot");
        }
    }
}
```

#### [JEP 440: Record Patterns](https://openjdk.org/jeps/440)

In addition to Type Patterns, Record Patterns were added, combining record matching with record decomposition.

**Syntax**
```java
record Pair(Object x, Object y) {}

// instanceof syntax
//               |      record pattern    |  guard   |
//              type->|        |<pattern
if (p instanceof Pair(Integer x, Integer y) && y < 10) {
    System.out.println(x + ", " + y + " < 10");
}

// switch syntax
System.out.println(switch (o) {
//       |      record pattern    |   guard     |
//     type->|        |<pattern   
    case Pair(Integer x, Integer y) when y < 10 -> x + ", " + y + " < 10";
});
```
Type and record patterns can be nested. As a bonus, decomposition adds symmetry with instantiation
```java
Rectangle r = new Rectangle(new ColoredPoint(new Point(x1, y1), c1), 
                            new ColoredPoint(new Point(x2, y2), c2));

if (r instanceof Rectangle(ColoredPoint(Point(var x, var y), var c),
                           var lr)) {
    System.out.println("Upper-left corner: " + x);
}                            
```
