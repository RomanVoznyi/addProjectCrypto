package Crypto.service.currency_services;

import Crypto.model.currency_models.btc.BTC;
import Crypto.model.currency_models.btc.BTCRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class BTCService extends BaseService<BTC> {

	public BTCService(BTCRepository repository, Gson gson) {
		super(repository, gson, BTC.class, "prices/BTC_values.txt");
	}
}
