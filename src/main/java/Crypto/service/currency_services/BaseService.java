package Crypto.service.currency_services;

import Crypto.model.CalculatedValues;
import Crypto.model.Crypto;
import Crypto.utils.Utils;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T extends Crypto> {
	
	private final Gson gson;
    private final JpaRepository <T, Long> repository;
    private final Class<T> entityClass;
    private final String filePath;

    public BaseService(JpaRepository<T, Long> repository, Gson gson, Class<T> entityClass, String filePath) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.filePath = filePath;
		this.gson = gson;
    }

    @SneakyThrows
    @PostConstruct
    protected void readFile() {
        File file = new ClassPathResource(filePath, entityClass.getClassLoader()).getFile();
		if (file.getName().endsWith(".csv")) {
			readCsvFile(file.toPath());
		} else if(file.getName().endsWith(".txt")){
			readTxtFile(file.toPath());
		} else throw new RuntimeException("Not supported yet");
	}

    private void readCsvFile(Path path) throws IOException {
		Files.lines(path)
                .skip(1)
                .map(item -> {
                    String[] el = item.split(",");
                    T instance = createEntityInstance();
                    instance.setTimestamp(Utils.getDate(el[0]));
                    instance.setSymbol(el[1]);
                    instance.setPrice(new BigDecimal(el[2]));
                    return instance;
                })
                .forEach(repository::save);
    }

	private void readTxtFile(Path path) throws IOException {
		Files.lines(path)
			.map(line -> gson.fromJson(line, entityClass))
			.forEach(repository::save);
	}

    @SneakyThrows
    private T createEntityInstance(){
        return entityClass.getConstructor().newInstance();
    }
	
    public List<T> getAllList() {
        return repository.findAll();
    }

    public Optional<CalculatedValues> getValues(String startDate, String endDate) {
        Date start = Utils.parseDate(startDate);
        Date end = Utils.parseDate(endDate);
        if (end.getTime() == start.getTime()) {
            end = new Date(end.getTime() + Utils.getDaysInMls(1));
        }
        Date finalEnd = end;
        List<T> allList = repository.findAll();
        List<BigDecimal> pricesList = allList.stream()
                .filter(it -> it.getTimestamp().getTime() >= start.getTime() && it.getTimestamp().getTime() <= finalEnd.getTime())
                .map(T::getPrice)
                .collect(Collectors.toList());
        return pricesList.isEmpty() ? Optional.empty() :
			Optional.of(CalculatedValues.builder()
				.name(entityClass.getSimpleName())
				.values(Utils.calculateValues(pricesList))
				.build());
    }

}
