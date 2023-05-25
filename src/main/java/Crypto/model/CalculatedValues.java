package Crypto.model;

import lombok.Data;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculatedValues {
    private String name;
    private Values values;
    private BigDecimal diff;
}
