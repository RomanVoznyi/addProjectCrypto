package Crypto.service.currency_services;

import Crypto.model.currency_models.xrp.XRP;
import Crypto.model.currency_models.xrp.XRPRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class XRPService extends BaseService<XRP> {

	public XRPService(XRPRepository repository, Gson gson) {
		super(repository, gson, XRP.class, "prices/XRP_values.csv");
	}

}
