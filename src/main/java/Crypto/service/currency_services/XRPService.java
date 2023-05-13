package Crypto.service.currency_services;

import Crypto.model.currency_models.xrp.XRP;
import Crypto.model.currency_models.xrp.XRPRepository;
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
public class XRPService {
    private final XRPRepository xrpRepository;

    @PostConstruct
    private void readFile() {
        ClassPathResource res = new ClassPathResource(
                "prices/XRP_values.csv",
                XRP.class.getClassLoader());
        try {
            String fileData = String.join(";", Files.readAllLines(res.getFile().toPath()));
            Arrays.stream(fileData.split(";")).skip(1).forEach(item -> {
                String[] el = item.split(",");
                XRP xrpRate = new XRP();
                xrpRate.setTimestamp(Utils.getDate(el[0]));
                xrpRate.setSymbol(el[1]);
                xrpRate.setPrice(new BigDecimal(el[2]));
                addItem(xrpRate);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItem(XRP item) {
        xrpRepository.save(item);
    }

    public List<XRP> getAllList() {
        return xrpRepository.findAll();
    }

    public String getValues(String startDate, String endDate) {
        Date start = Utils.parseDate(startDate);
        Date end = Utils.parseDate(endDate);

        if (end.getTime() == start.getTime()) {
            end = new Date(end.getTime() + Utils.getDaysInMls(1));
        }
        Date finalEnd = end;

        List<XRP> allList = getAllList();
        List<BigDecimal> pricesList = allList.stream()
                .filter(it -> it.getTimestamp().getTime() >= start.getTime() && it.getTimestamp().getTime() <= finalEnd.getTime())
                .map(XRP::getPrice)
                .collect(Collectors.toList());

        return pricesList.size() == 0
                ? "No such data"
                : String.format("{name: XRP, values: %s}", Utils.calculateValues(pricesList));
    }


}
