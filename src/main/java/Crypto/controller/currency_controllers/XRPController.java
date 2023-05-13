package Crypto.controller.currency_controllers;

import Crypto.model.currency_models.xrp.XRP;
import Crypto.service.currency_services.XRPService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api/v1/xrp")
@RequiredArgsConstructor
@RestController
public class XRPController {
    private final XRPService xrpService;

    @GetMapping(value = "/list", produces = "application/json")
    public List<XRP> getAll() {
        return xrpService.getAllList();
    }

    @GetMapping(value = "/values", produces = "application/json")
    public String getValues(String startDate, String endDate) {
        return xrpService.getValues(startDate,endDate);
    }
}
