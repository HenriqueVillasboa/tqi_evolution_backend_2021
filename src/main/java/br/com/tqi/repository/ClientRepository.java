package br.com.tqi.repository;

import br.com.tqi.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    ClientEntity findOneByEmail(String email);
    public Optional<ClientEntity> findByEmail(String email);
}
