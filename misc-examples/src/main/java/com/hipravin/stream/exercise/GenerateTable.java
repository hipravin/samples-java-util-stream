package com.hipravin.stream.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GenerateTable {
    static String sampleReport = """
            BenchmarkConfig.bench_sumPrimitiveStream             thrpt    5  32,616 ± 1,055  ops/s
            BenchmarkConfig.bench_sumAtomicLong                  thrpt    5   1,869 ± 0,068  ops/s
            BenchmarkConfig.bench_sumBigInteger                  thrpt    5   0,360 ± 0,083  ops/s
            BenchmarkConfig.bench_sumBoxedCycle                  thrpt    5   1,296 ± 0,080  ops/s
            BenchmarkConfig.bench_sumBoxedStream                 thrpt    5   0,879 ± 0,162  ops/s
            """;

    public static void main(String[] args) {
        System.out.println(toHtmlTableBody(sampleReport));
    }

    public static String toHtmlTableBody(String rawTextReport) {
        Pattern reportLinePattern = Pattern.compile(
                "^\\s*BenchmarkConfig.bench_(\\S+)\\s+thrpt\\s+\\d+\\s+(\\S+)\\s+±\\s+(\\S+)\\s+ops/s\\s*$");

        return sampleReport.lines()
                .map(line -> wrapGroups(line, reportLinePattern, "<td>", "</td>"))
                .map(line -> "<tr>" + line + "</tr>")
                .collect(Collectors.joining("\n"));
    }

    private static CharSequence wrapGroups(String text, Pattern pattern, String prepend, String append) {
        StringBuilder result = new StringBuilder();

        Matcher matcher = pattern.matcher(text);
        if(!matcher.find()) {
            return "";
        }
        for (int i = 1; i <= matcher.groupCount(); i++) {
            result.append(prepend)
                    .append(matcher.group(i))
                    .append(append);
        }
        return result;
    }
}
