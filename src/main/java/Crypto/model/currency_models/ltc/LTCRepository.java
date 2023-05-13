package Crypto.model.currency_models.ltc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LTCRepository extends JpaRepository<LTC,Long> {
}
