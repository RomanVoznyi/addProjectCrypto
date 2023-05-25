package Crypto.config;

import Crypto.utils.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

	@Bean
	public Gson gson() {
		return new GsonBuilder()
			.registerTypeAdapter(Date.class, new DateDeserializer())
			.setPrettyPrinting()
			.create();
	}

}
