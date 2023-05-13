package Crypto.model.currency_models.doge.doge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DOGERepository extends JpaRepository<DOGE,Long> {
}
