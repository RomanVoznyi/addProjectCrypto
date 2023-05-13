package Crypto.controller.currency_controllers;

import Crypto.model.currency_models.ltc.LTC;
import Crypto.service.currency_services.LTCService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api/v1/ltc")
@RequiredArgsConstructor
@RestController
public class LTCController {
    private final LTCService ltcService;

    @GetMapping(value = "/list", produces = "application/json")
    public List<LTC> getAll() {
        return ltcService.getAllList();
    }

    @GetMapping(value = "/values", produces = "application/json")
    public String getValues(String startDate, String endDate) {
        return ltcService.getValues(startDate,endDate);
    }
}
