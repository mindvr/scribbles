import java.util.stream.Stream;

public class Jep440 {
    record Pair(Object x, Object y) {
    }
    public static void main(String[] args) {
        Pair pNum = new Pair(42, 42);
        Pair pNumSmall = new Pair(1, 2);
        Pair pS = new Pair("foo", "bar");
        Stream.of(pNum, pNumSmall, pS).forEach(Jep440::printInstanceOf);
//        42, 42
//        1, 2 < 10
//        foo, bar
        Stream.of(null, "foo", pNum, pNumSmall, pS).forEach(Jep440::printSwitch);
//        null
//        unknown foo
//        str: 42, 42
//        1, 2 < 10
//        foo, bar
    }

    private static void printInstanceOf(Pair p) {
        if (p instanceof Pair(String s, String t)) {
            System.out.println(s + ", " + t);
        } else if (p instanceof Pair(Integer x, Integer y) && y < 10) {
            System.out.println(x + ", " + y + " < 10");
        } else if (p instanceof Pair(Integer x, Integer y)) {
            System.out.println(x + ", " + y);
        } else {
            System.out.println("else");
        }
    }

    private static void printSwitch(Object o) {
        System.out.println(switch (o) {
            case null -> "null";
            case Pair(String s, String t) -> s + ", " + t;
            case Pair(Integer x, Integer y) when y < 10 -> x + ", " + y + " < 10";
            case Pair(Integer x, Integer y) -> "str: " + x + ", " + y;
            default -> "unknown " + o;
        });
    }
}
