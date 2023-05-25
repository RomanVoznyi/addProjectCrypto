package Crypto.service.currency_services;

import Crypto.model.currency_models.doge.doge.DOGE;
import Crypto.model.currency_models.doge.doge.DOGERepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class DOGEService extends BaseService<DOGE> {

	public DOGEService(DOGERepository repository, Gson gson) {
		super(repository, gson, DOGE.class, "prices/DOGE_values.csv");
	}
    
}
