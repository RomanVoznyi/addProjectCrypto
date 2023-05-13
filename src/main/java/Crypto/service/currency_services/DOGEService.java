package Crypto.service.currency_services;

import Crypto.model.currency_models.doge.doge.DOGE;
import Crypto.model.currency_models.doge.doge.DOGERepository;
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
public class DOGEService {
    private final DOGERepository dogeRepository;

    @PostConstruct
    private void readFile() {
        ClassPathResource res = new ClassPathResource(
                "prices/DOGE_values.csv",
                DOGE.class.getClassLoader());
        try {
            String fileData = String.join(";", Files.readAllLines(res.getFile().toPath()));
            Arrays.stream(fileData.split(";")).skip(1).forEach(item -> {
                String[] el = item.split(",");
                DOGE dogeRate = new DOGE();
                dogeRate.setTimestamp(Utils.getDate(el[0]));
                dogeRate.setSymbol(el[1]);
                dogeRate.setPrice(new BigDecimal(el[2]));
                addItem(dogeRate);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(DOGE item) {
        dogeRepository.save(item);
    }

    public List<DOGE> getAllList() {
        return dogeRepository.findAll();
    }

    public String getValues(String startDate, String endDate) {
        Date start = Utils.parseDate(startDate);
        Date end = Utils.parseDate(endDate);

        if (end.getTime() == start.getTime()) {
            end = new Date(end.getTime() + Utils.getDaysInMls(1));
        }
        Date finalEnd = end;

        List<DOGE> allList = getAllList();
        List<BigDecimal> pricesList = allList.stream()
                .filter(it -> it.getTimestamp().getTime() >= start.getTime() && it.getTimestamp().getTime() <= finalEnd.getTime())
                .map(DOGE::getPrice)
                .collect(Collectors.toList());

        return pricesList.size() == 0
                ? "No such data"
                : String.format("{name: DOGE, values: %s}", Utils.calculateValues(pricesList));
    }


}
