package com.pyskacz.android.myexpenses.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {
    private NumberUtils() {
    }

    private static final Pattern DOUBLE_DECIMAL_PATTERN = Pattern.compile("([+-]?)(\\d+)(\\.\\d+)?");

    public static double sumUpStringDoubleValues(String input) {
        double sum = 0.0;
        Matcher matcher = DOUBLE_DECIMAL_PATTERN.matcher(input);
        while (matcher.find()) {
            String group = matcher.group();
            sum += Double.parseDouble(group);
        }

        return sum;
    }

    public static String sumUpStringDoubleValuesAndFormat(String input, DecimalFormat format) {
        return format.format(sumUpStringDoubleValues(input));
    }
}
