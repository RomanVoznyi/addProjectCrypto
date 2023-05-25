package Crypto.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.text.ParseException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DateDeserializer implements JsonDeserializer<Date> {
	
	private final SimpleDateFormat format;

	public DateDeserializer() {
		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        try {
            return format.parse(element.getAsString());
        } catch (ParseException exp) {
			log.error("DateDeserializer.deserialize : ", exp);
            return null;
        }
    }
}
