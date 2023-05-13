package Crypto.model.currency_models.btc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BTCRepository extends JpaRepository<BTC,Long> {
}
