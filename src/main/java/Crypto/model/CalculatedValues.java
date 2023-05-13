package Crypto.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculatedValues {
    String name;
    Values values;
    BigDecimal diff;

    @Data
    public class Values {
        BigDecimal min;
        BigDecimal max;
        BigDecimal average;
        BigDecimal normal;

        @Override
        public String toString() {
            return "{ min=" + min +
                    ", max=" + max +
                    ", average=" + average +
                    ", normal=" + normal + '}';
        }
    }
}
