package br.com.tqi.service;

import br.com.tqi.entity.ClientEntity;
import br.com.tqi.data.ClientDetailData;
import br.com.tqi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientDetailService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientEntity> client = clientRepository.findByEmail(username);
        if (client.isEmpty()) {
            throw new UsernameNotFoundException("User with e-mail [" + username + "] not found");
        }
        return new ClientDetailData(client);
    }
}
