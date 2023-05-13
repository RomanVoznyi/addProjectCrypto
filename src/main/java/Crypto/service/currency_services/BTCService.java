package Crypto.service.currency_services;

import Crypto.model.currency_models.btc.BTC;
import Crypto.model.currency_models.btc.BTCRepository;
import Crypto.utils.DateDeserializer;
import Crypto.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BTCService {
    private final BTCRepository btcRepository;

    @PostConstruct
    private void readFile() {
        ClassPathResource res = new ClassPathResource(
                "prices/BTC_values.txt",
                BTC.class.getClassLoader());
        try {
            String fileData = "[".concat(String.join(",", Files.readAllLines(res.getFile().toPath())))
                    .concat("]");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            Type btcListType = new TypeToken<List<BTC>>() {
            }.getType();
            List<BTC> btcList = gson.fromJson(fileData, btcListType);

            btcList.forEach(this::addItem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(BTC item) {
        btcRepository.save(item);
    }

    public List<BTC> getAllList() {
        return btcRepository.findAll();
    }

    public String getValues(String startDate, String endDate) {
        Date start = Utils.parseDate(startDate);
        Date end = Utils.parseDate(endDate);

        if (end.getTime() == start.getTime()) {
            end = new Date(end.getTime() + Utils.getDaysInMls(1));
        }
        Date finalEnd = end;

        List<BTC> allList = getAllList();
        List<BigDecimal> pricesList = allList.stream()
                .filter(it -> it.getTimestamp().getTime() >= start.getTime() && it.getTimestamp().getTime() <= finalEnd.getTime())
                .map(BTC::getPrice)
                .collect(Collectors.toList());

        return pricesList.size() == 0
                ? "No such data"
                : String.format("{name: BTC, values: %s}", Utils.calculateValues(pricesList));
    }


}
