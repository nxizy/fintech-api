package br.com.fintech.API.wallet.repository;

import br.com.fintech.API.wallet.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, String> {

    List<Operation> findByAccount_IdOrderByCreatedAtDesc(String accountId);
}