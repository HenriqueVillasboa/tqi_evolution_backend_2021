package br.com.tqi.data;

import br.com.tqi.entity.ClientEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ClientDetailData implements UserDetails {

    private final Optional<ClientEntity> client;

    public ClientDetailData(Optional<ClientEntity> client) {
        this.client = client;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() { return client.orElse(new ClientEntity()).getPassword(); }

    @Override
    public String getUsername() { return client.orElse(new ClientEntity()).getEmail(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
