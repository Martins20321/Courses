package br.com.martinsdev.forumhub.infra.security;

import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.infra.exception.ErroTokenJWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.securtiy.secret.key}")
    private String secretKey;

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("Forum Hub")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .withClaim("id", usuario.getId())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new ErroTokenJWTException("Não foi possível fazer a geração do token JWT!");
        }
    }

    public String getSubject(String tokenJWT){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
             return JWT.require(algorithm)
                    .withIssuer("Forum Hub")
                    .build()
                     .verify(tokenJWT)
                     .getSubject();

        } catch (JWTVerificationException exception){
            throw new ErroTokenJWTException("Este token está inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
