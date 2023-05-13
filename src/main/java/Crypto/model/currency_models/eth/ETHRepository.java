package Crypto.model.currency_models.eth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ETHRepository extends JpaRepository<ETH,Long> {
}
