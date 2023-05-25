package Crypto.controller.currency_controllers;

import Crypto.model.CalculatedValues;
import Crypto.model.currency_models.btc.BTC;
import Crypto.service.currency_services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/btc")
@RequiredArgsConstructor
@RestController
public class BTCController {

	private final BaseService<BTC> service;

	@GetMapping(value = "/list", produces = "application/json")
	public List<BTC> getAll() {
		return service.getAllList();
	}

	@GetMapping(value = "/values", produces = "application/json")
	public Optional<CalculatedValues> getValues(String startDate, String endDate) {
		return service.getValues(startDate, endDate);
	}

}
