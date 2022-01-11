package br.com.tqi.service;

import br.com.tqi.entity.ClientEntity;
import br.com.tqi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientService {

    @Autowired private ClientRepository clientRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity clientEntity) {
        clientEntity.setPassword(passwordEncoder.encode(clientEntity.getPassword()));
        clientRepository.save(clientEntity);
        return new ResponseEntity<ClientEntity>(clientRepository.save(clientEntity), HttpStatus.CREATED);
    }
}
