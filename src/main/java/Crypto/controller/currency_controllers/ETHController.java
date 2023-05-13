package Crypto.controller.currency_controllers;

import Crypto.model.currency_models.eth.ETH;
import Crypto.service.currency_services.ETHService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api/v1/eth")
@RequiredArgsConstructor
@RestController
public class ETHController {
    private final ETHService ethService;

    @GetMapping(value = "/list", produces = "application/json")
    public List<ETH> getAll() {
        return ethService.getAllList();
    }

    @GetMapping(value = "/values", produces = "application/json")
    public String getValues(String startDate, String endDate) {
        return ethService.getValues(startDate,endDate);
    }

}
