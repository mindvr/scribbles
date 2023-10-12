import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YearStats {
    private final static ScopedValue<Integer> CONTEXT = ScopedValue.newInstance();

    record DateStat(LocalDate date, List<Integer> hist, int digits) {

    }

    record YearStat(int year, int min, int max, float avg) {
    }

    public static void main(String[] args) {
        calculate(0, 3000)
                .forEach(yearStat -> {
                    if (yearStat instanceof YearStat(int year, int min, int max, float avg)) {
                        System.out.println(STR. "\{ year }\t\{ min }\t\{ max }\t\{ avg }" );
                    }
                });
    }

    private static List<YearStat> calculate(int from, int to) {
        try (var taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<StructuredTaskScope.Subtask<YearStat>> subtasks = IntStream.rangeClosed(from, to)
                    .mapToObj(i -> taskScope.fork(() -> ScopedValue.where(CONTEXT, i)
                            .call(YearStats::collectYearStat)))
                    .toList();
            taskScope.join().throwIfFailed();
            return subtasks.stream().map(StructuredTaskScope.Subtask::get).toList();
        } catch (InterruptedException | ExecutionException _) {
            throw new RuntimeException("interrupted");
        }
    }

    private static YearStat collectYearStat() {
        int year = CONTEXT.get();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int total = 0;
        int totalCount = 0;
        for (LocalDate date = LocalDate.of(year, 1, 1);
             date.getYear() == year;
             date = date.plusDays(1), totalCount++) {
            if (collectStat(date) instanceof DateStat(_, _, int distinct)) {
                if (min > distinct) min = distinct;
                if (max < distinct) max = distinct;
                total += distinct;
            }
        }
        return new YearStat(year, min, max, (float) total / totalCount);
    }

    private static DateStat collectStat(LocalDate date) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 1, 4, SignStyle.NEVER)
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .toFormatter();

        String str = date.format(formatter);
        int[] hist = new int[10];
        for (char ch : str.toCharArray()) {
            int i = Integer.parseInt(String.valueOf(ch));
            hist[i] += 1;
        }
        int distinct = (int) IntStream.of(hist).filter(i -> i > 0).count();
        return new DateStat(date, Arrays.stream(hist).boxed().collect(Collectors.toList()), distinct);
    }
}