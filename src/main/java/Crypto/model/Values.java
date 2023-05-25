package Crypto.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Values {

	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal average;
	private BigDecimal normal;

	public BigDecimal getValue(String val) {
		switch (val) {
		case "min": return min;
		case "max": return max;
		case "average": return average;
		default: return normal;
		}
	}

	@Override
	public String toString() {
		return "{ min=" + min + ", max=" + max + ", average=" + average + ", normal=" + normal + '}';
	}

}