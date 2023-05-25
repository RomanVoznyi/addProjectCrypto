package Crypto.utils;

import Crypto.model.Values;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Utils {

	private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	static{
		FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

    public static Date getDate(String date) {
        return new Date(Long.parseLong(date));
    }

    public static Date parseDate(String date) {
        try {
            return FORMATTER.parse(date);
        } catch (ParseException exp) {
            System.out.println(exp.getMessage());
            return null;
        }
    }

    public static Values calculateValues(List<BigDecimal> prices) {
        BigDecimal min = getMin(prices);
        BigDecimal max = getMax(prices);
		return Values.builder()
			.max(max)
			.min(min)
			.average(getAverage(prices))
			.normal(getNormal(min, max))
			.build();
	}

    public static BigDecimal getMin(List<BigDecimal> prices) {
		return getValueByComparator(prices, Comparator.naturalOrder());
    }

    public static BigDecimal getMax(List<BigDecimal> prices) {
        return getValueByComparator(prices, Comparator.reverseOrder());
    }
	
	private static BigDecimal getValueByComparator(List<BigDecimal> prices, Comparator<BigDecimal>comparator) {
        return prices
                .stream()
                .max(comparator)
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
        try {
			return new BigDecimal(strNum).compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
    }

	public static boolean isValidDates(String startDate, String endDate) {
		try {
			Date start = parseDate(startDate);
			Date end = parseDate(endDate);
			return start != null && end != null && end.getTime() >= start.getTime();
		} catch (Exception e) {
			return false;
		}
	}
}
