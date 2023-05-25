package Crypto.service.currency_services;

import Crypto.model.currency_models.ltc.LTC;
import Crypto.model.currency_models.ltc.LTCRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class LTCService extends BaseService<LTC> {

	public LTCService(LTCRepository repository, Gson gson) {
		super(repository, gson, LTC.class, "prices/LTC_values.csv");
	}
}
