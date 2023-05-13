package Crypto.service.currency_services;

import Crypto.model.currency_models.eth.ETH;
import Crypto.model.currency_models.eth.ETHRepository;
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
public class ETHService {
    private final ETHRepository ethRepository;

    @PostConstruct
    private void readFile() {
        ClassPathResource res = new ClassPathResource(
                "prices/ETH_values.txt",
                ETH.class.getClassLoader());
        try {
            String fileData = "[".concat(String.join(",", Files.readAllLines(res.getFile().toPath())))
                    .concat("]");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            Gson gson=gsonBuilder.setPrettyPrinting().create();
            Type ethListType = new TypeToken<List<ETH>>() {}.getType();
            List<ETH> ethList = gson.fromJson(fileData, ethListType);

            ethList.forEach(this::addItem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(ETH item) {
        ethRepository.save(item);
    }

    public List<ETH> getAllList() {
        return ethRepository.findAll();
    }

    public String getValues(String startDate, String endDate) {
        Date start = Utils.parseDate(startDate);
        Date end = Utils.parseDate(endDate);

        if (end.getTime() == start.getTime()) {
            end = new Date(end.getTime() + Utils.getDaysInMls(1));
        }
        Date finalEnd = end;

        List<ETH> allList = getAllList();
        List<BigDecimal> pricesList = allList.stream()
                .filter(it -> it.getTimestamp().getTime() >= start.getTime() && it.getTimestamp().getTime() <= finalEnd.getTime())
                .map(ETH::getPrice)
                .collect(Collectors.toList());

        return pricesList.size() == 0
                ? "No such data"
                : String.format("{name: ETH, values: %s}", Utils.calculateValues(pricesList));
    }


}
