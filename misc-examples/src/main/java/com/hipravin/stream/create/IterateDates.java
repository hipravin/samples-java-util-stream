package com.hipravin.stream.create;

import java.time.LocalDate;
import java.util.stream.Stream;

public class IterateDates {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        Stream<LocalDate> oneWeekForward = Stream.iterate(today, d -> d.plusDays(1))
                        .limit(7);
        oneWeekForward.forEach(d -> System.out.println(d));

        System.out.println(" -------- ");
        Stream<LocalDate> datesTillEndOfMonth =
                Stream.iterate(today, d -> d.getMonth() == today.getMonth(), d -> d.plusDays(1));
        datesTillEndOfMonth.forEach(d -> System.out.println(d));
    }
}
