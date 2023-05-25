package Crypto.model.currency_models.btc;

import Crypto.model.Crypto;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BTC implements Crypto{
	private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(length = 10, nullable = false)
    private String symbol;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Date timestamp;
}
