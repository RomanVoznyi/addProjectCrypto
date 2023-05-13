package Crypto.utils;

import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Utils {

    public static Date getDate(String date) {
        return new Date(Long.parseLong(date));
    }

    public static Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return format.parse(date);
        } catch (ParseException exp) {
            System.out.println(exp.getMessage());
            return null;
        }
    }

    public static String calculateValues(List<BigDecimal> prices) {
        BigDecimal min = getMin(prices);
        BigDecimal max = getMax(prices);
        BigDecimal average = getAverage(prices);
        BigDecimal normal = getNormal(min, max);
        return String.format("{min: %s, max: %s, average: %s, normal: %s}", min, max, average.toPlainString(), normal.toPlainString());
    }

    public static BigDecimal getMin(List<BigDecimal> prices) {
        return prices
                .stream()
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal getMax(List<BigDecimal> prices) {
        return prices
                .stream()
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal getAverage(List<BigDecimal> prices) {
        BigDecimal sum = prices.stream().reduce(new BigDecimal(0), BigDecimal::add);
        return sum.divide(new BigDecimal(prices.size()), new MathContext(4));
    }

    public static BigDecimal getNormal(BigDecimal min, BigDecimal max) {
        BigDecimal normal = new BigDecimal(0);
        if (!min.equals(normal)) {
            normal = max.subtract(min).divide(min, new MathContext(4));
        }
        return normal;
    }

    public static long getDaysInMls(int days) {
        return days * 24 * 60 * 60 * 1000L;
    }

    public static boolean isValidNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            BigDecimal number = new BigDecimal(strNum);

            if (number.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isValidDates(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        Date start = parseDate(startDate);
        Date end = parseDate(endDate);
        return start != null && end != null && end.getTime() >= start.getTime();
    }
}
