package rs.bike.dzeparac.util;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SchoolYearConfig {

    public static LocalDate getStartDate(int year) {
        return LocalDate.of(year, 9, 1);
    }

    public static List<LocalDate> getWeekStartDates(int year) {
        return IntStream.range(0, 52)
                .mapToObj(i -> getStartDate(year).plusWeeks(i))
                .collect(Collectors.toList());
    }
}