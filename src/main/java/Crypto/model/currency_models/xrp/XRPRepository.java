package Crypto.model.currency_models.xrp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XRPRepository extends JpaRepository<XRP,Long> {
}
