package br.com.fintech.API.wallet.repository; // Caminho atualizado

import br.com.fintech.API.wallet.model.Operation; // Import do novo caminho da Entidade
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {

    // Encontra todas as operações de uma conta, ordenadas pela mais recente
    List<Operation> findByAccount_AccountIdOrderByCreatedAtDesc(UUID accountId);
}