package com.quizzapp.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    // metodo para generar el token
    public String createToken(Authentication authentication) {
        // definir algoritmo de encriptacion, HMAC256, y éste pide la clave secreta...
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        //extraer el usuario autenticado
        String username = authentication.getPrincipal().toString();

        // guardamos en authorities un string con los permisos separados por comas
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //generar token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator) // usuario q va a generar el token, nuestro backend
                .withSubject(username) // sujeto a quien se le va a generar el token
                .withClaim("authorities", authorities) // los claim q va a llevar el token en el payload
                .withIssuedAt(new Date()) // la fecha en que se genera el token
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // momento en que va a expirar el token, momento actual más 30 min a milisegundos
                .withJWTId(UUID.randomUUID().toString()) // asignar un id para el token
                .withNotBefore(new Date(System.currentTimeMillis())) // momento a partir de cual el token va a dejar de ser valido; a partir del momento corriente.
                .sign(algorithm);
        System.out.println("Token generado: " + jwtToken); // Imprime el token generado
        return jwtToken;

    }

    // metodo para verificar el token
    public DecodedJWT validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    // METODO PARA EXTRAER EL USUARIO DEL TOKEN DECODIFICADO, QUE VIENE DENTRO DEL TOKEN CODIFICADO

    public String extractUsername (DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    // EXTRAER EL CLAIM DE LOS ROLES
    public Claim getSpecificClaim (DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }


}
