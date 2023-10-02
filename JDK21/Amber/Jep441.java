public class Jep441 {
    public static void main(String[] args) {
        Object o = null;

        System.out.println(switch (o) {
                    case null -> "null";
                    case String s -> "String!";
                    default -> "default";
                }
        );

        System.out.println(switch (o) {
                    case String s -> "String!";
                    case null, default -> "default or null";
                }
        );
        var x = switch (o) {
            case String s -> "String!";
            default -> "default";
        };
    }
}