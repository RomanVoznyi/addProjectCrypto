package Crypto.controller.currency_controllers;

import Crypto.model.currency_models.doge.doge.DOGE;
import Crypto.service.currency_services.DOGEService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api/v1/doge")
@RequiredArgsConstructor
@RestController
public class DOGEController {
    private final DOGEService dogeService;

    @GetMapping(value = "/list", produces = "application/json")
    public List<DOGE> getAll() {
        return dogeService.getAllList();
    }

    @GetMapping(value = "/values", produces = "application/json")
    public String getValues(String startDate, String endDate) {
        return dogeService.getValues(startDate,endDate);
    }

}
