import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Jep444 {
    public static void main(String[] args) {
        Instant before = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 1_000_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
        }  // executor.close() is called implicitly, and waits
        Instant after = Instant.now();
        System.out.println("elapsed seconds " + Duration.between(before, after).getSeconds());
    }
}
