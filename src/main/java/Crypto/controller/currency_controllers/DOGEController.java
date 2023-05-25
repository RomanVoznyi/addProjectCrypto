package Crypto.controller.currency_controllers;

import Crypto.model.CalculatedValues;
import Crypto.model.currency_models.doge.doge.DOGE;
import Crypto.service.currency_services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/doge")
@RequiredArgsConstructor
@RestController
public class DOGEController {
	
    private final BaseService<DOGE> service;

    @GetMapping(value = "/list", produces = "application/json")
    public List<DOGE> getAll() {
        return service.getAllList();
    }

    @GetMapping(value = "/values", produces = "application/json")
    public Optional<CalculatedValues> getValues(String startDate, String endDate) {
        return service.getValues(startDate,endDate);
    }

}
