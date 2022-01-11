package br.com.tqi.repository;

import br.com.tqi.entity.ClientEntity;
import br.com.tqi.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {
    List<LoanApplicationEntity> findByClientEntity(ClientEntity loggedClient);
}
