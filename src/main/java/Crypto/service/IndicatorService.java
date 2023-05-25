package Crypto.service;

import Crypto.model.CalculatedValues;
import Crypto.service.currency_services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IndicatorService {

	private final List<BaseService> servicies;

	public CalculatedValues getCurrencyByIndicator(String indicator, String value, String startDate, String endDate) {

		return servicies.stream()
			.map(s -> (Optional<CalculatedValues>) s.getValues(startDate, endDate))
			.filter(Optional::isPresent).map(Optional::get)
			.peek(it -> it.setDiff(new BigDecimal(value).subtract(it.getValues().getValue(indicator)).abs()))
			.min(Comparator.comparing(CalculatedValues::getDiff)).orElse(null);
	}

}
