package Crypto.service.currency_services;

import Crypto.model.currency_models.eth.ETH;
import Crypto.model.currency_models.eth.ETHRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class ETHService extends BaseService<ETH> {
	
	public ETHService(ETHRepository repository, Gson gson) {
		super(repository, gson, ETH.class, "prices/ETH_values.txt");
	}

}
