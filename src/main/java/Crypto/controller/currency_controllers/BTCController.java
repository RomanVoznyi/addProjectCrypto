package Crypto.controller.currency_controllers;

import Crypto.model.currency_models.btc.BTC;
import Crypto.service.currency_services.BTCService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api/v1/btc")
@RequiredArgsConstructor
@RestController
public class BTCController {
    private final BTCService btcService;

    @GetMapping(value = "/list", produces = "application/json")
    public List<BTC> getAll() {
        return btcService.getAllList();
    }

    @GetMapping(value = "/values", produces = "application/json")
    public String getValues(String startDate, String endDate) {
        return btcService.getValues(startDate,endDate);
    }

}
