package br.com.tqi.config;

import br.com.tqi.data.ClientDetailData;
import br.com.tqi.entity.ClientEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final int EXPIRATION_TIME = 10_800_000;
    public static final String TOKEN = "52470c1f-d642-43ef-80bd-7a36ed70bef9";

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ClientEntity client = new ObjectMapper().readValue(request.getInputStream(), ClientEntity.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    client.getEmail(),
                    client.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to authenticate user. ", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        ClientDetailData clientData = (ClientDetailData) authResult.getPrincipal();

        String token = JWT.create().withSubject(clientData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
