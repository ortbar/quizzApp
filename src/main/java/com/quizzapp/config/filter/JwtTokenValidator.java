package com.quizzapp.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.quizzapp.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

// FILTRO QUE VA A VALIDAR SI EL TOKEN ES VALIDO. SE VA EJECTURAR CADA VEZ QUE SE HAGA UNA PETICION

public class JwtTokenValidator extends OncePerRequestFilter {


    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken != null) {
            jwtToken = jwtToken.substring(7); // extraer token
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken); // decodificar token

            String username = jwtUtils.extractUsername(decodedJWT); // extrear username
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT,"authorities").asString(); // recuperar los permisos de los usuarios de los claims

            // setear ek securityContexHolder con sus permisos, pero hay que pasarlos aL FORMATO DE grANTEdAuthority. hay que extraer los permisos del string authorities

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);








        }

        filterChain.doFilter(request,response);

    }
}
