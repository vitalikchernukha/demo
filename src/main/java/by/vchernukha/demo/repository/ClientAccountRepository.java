package by.vchernukha.demo.repository;

import by.vchernukha.demo.entity.ClientAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccountEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ClientAccountEntity> findById(Long clientAccountId);

}
