import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Jep443 {
    public static void main(String[] args) {
        System.out.println(Stream.of("foo", "bar")
                .collect(Collectors.toMap(String::toUpperCase, _ -> "NODATA")));
    }
}
