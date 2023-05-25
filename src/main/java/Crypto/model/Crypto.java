package Crypto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface Crypto extends Serializable{
	
    long getId();
    String getSymbol();
    BigDecimal getPrice();
    Date getTimestamp();
    
    void setId(long id);
    void setSymbol(String symbol);
    void setPrice(BigDecimal price);
    void setTimestamp(Date date);
	
}
