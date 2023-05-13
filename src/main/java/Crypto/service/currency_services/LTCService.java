package Crypto.service.currency_services;

import Crypto.model.currency_models.ltc.LTC;
import Crypto.model.currency_models.ltc.LTCRepository;
import Crypto.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LTCService {
    private final LTCRepository ltcRepository;

    @PostConstruct
    private void readFile() {
        ClassPathResource res = new ClassPathResource(
                "prices/LTC_values.csv",
                LTC.class.getClassLoader());
        try {
            String fileData = String.join(";", Files.readAllLines(res.getFile().toPath()));
            Arrays.stream(fileData.split(";")).skip(1).forEach(item -> {
                String[] el = item.split(",");
                LTC ltcRate = new LTC();
                ltcRate.setTimestamp(Utils.getDate(el[0]));
                ltcRate.setSymbol(el[1]);
                ltcRate.setPrice(new BigDecimal(el[2]));
                addItem(ltcRate);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(LTC item) {
        ltcRepository.save(item);
    }

    public List<LTC> getAllList() {
        return ltcRepository.findAll();
    }

    public String getValues(String startDate, String endDate) {
        Date start = Utils.parseDate(startDate);
        Date end = Utils.parseDate(endDate);

        if (end.getTime() == start.getTime()) {
            end = new Date(end.getTime() + Utils.getDaysInMls(1));
        }
        Date finalEnd = end;

        List<LTC> allList = getAllList();
        List<BigDecimal> pricesList = allList.stream()
                .filter(it -> it.getTimestamp().getTime() >= start.getTime() && it.getTimestamp().getTime() <= finalEnd.getTime())
                .map(LTC::getPrice)
                .collect(Collectors.toList());

        return pricesList.size() == 0
                ? "No such data"
                : String.format("{name: LTC, values: %s}", Utils.calculateValues(pricesList));
    }


}
