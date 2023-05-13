package Crypto.service;

import Crypto.model.CalculatedValues;
import Crypto.service.currency_services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IndicatorService {
    private final BTCService btcService;
    private final DOGEService dogeService;
    private final ETHService ethService;
    private final LTCService ltcService;
    private final XRPService xrpService;

    public String getCurrencyByIndicator(String indicator, String value, String startDate, String endDate) {
        List<String> valueList = new ArrayList<>();
        valueList.add(btcService.getValues(startDate, endDate));
        valueList.add(dogeService.getValues(startDate, endDate));
        valueList.add(ethService.getValues(startDate, endDate));
        valueList.add(ltcService.getValues(startDate, endDate));
        valueList.add(xrpService.getValues(startDate, endDate));

        List<CalculatedValues> currencies = checkValues(valueList);

        if (currencies.size() == 0) {
            return "No such data";
        }

        CalculatedValues bestSuitable = currencies.stream().peek(it -> {
            switch (indicator) {
                case "min":
                    it.setDiff(new BigDecimal(value).subtract(it.getValues().getMin()).abs());
                    break;
                case "max":
                    it.setDiff(new BigDecimal(value).subtract(it.getValues().getMax()).abs());
                    break;
                case "average":
                    it.setDiff(new BigDecimal(value).subtract(it.getValues().getAverage()).abs());
                    break;
                default:
                    it.setDiff(new BigDecimal(value).subtract(it.getValues().getNormal()).abs());
                    break;
            }
        }).min(Comparator.comparing(CalculatedValues::getDiff)).orElse(null);

        return String.format("Currency '%s' with values '%s' is the nearest currency for indicator '%s' and value '%s' for chosen period",
                bestSuitable.getName(), bestSuitable.getValues(), indicator, value);
    }

    private List<CalculatedValues> checkValues(List<String> valueList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return valueList.stream().filter(it->!it.equals("No such data")).map(it -> gson.fromJson(it, CalculatedValues.class)
        ).collect(Collectors.toList());
    }

}
