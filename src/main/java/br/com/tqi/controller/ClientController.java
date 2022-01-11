package br.com.tqi.controller;

import br.com.tqi.entity.ClientEntity;
import br.com.tqi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class ClientController {

    @Autowired private ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity clientEntity){
       return clientService.createClient(clientEntity);
    }
}
