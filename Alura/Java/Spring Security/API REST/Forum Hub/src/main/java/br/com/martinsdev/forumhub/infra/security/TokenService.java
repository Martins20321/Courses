package br.com.martinsdev.forumhub.infra.security;

import br.com.martinsdev.forumhub.domain.refreshtoken.RefreshToken;
import br.com.martinsdev.forumhub.domain.refreshtoken.RefreshTokenRepository;
import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.infra.exception.ErroTokenJWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${spring.securtiy.secret.key}")
    private String secretKey;

    private final RefreshTokenRepository repository;

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

    public String gerarRefreshToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String refreshToken = JWT.create()
                    .withIssuer("Forum Hub")
                    .withSubject(usuario.getId().toString())
                    .withJWTId(UUID.randomUUID().toString()) //Garante que cada refreshToken seja único
                    .withExpiresAt(dataExpiracaoRefreshToken())
                    .withClaim("id", usuario.getId())
                    .sign(algorithm);
            repository.save(new RefreshToken(null, refreshToken, false, usuario));
            return refreshToken;
        } catch (JWTCreationException exception){
            throw new ErroTokenJWTException("Não foi possível fazer a geração do Refresh Token JWT!");
        }
    }

    public String getSubject(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
             return JWT.require(algorithm)
                    .withIssuer("Forum Hub")
                    .build()
                     .verify(token)
                     .getSubject();

        } catch (JWTVerificationException exception){
            throw new ErroTokenJWTException("Este token está inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant dataExpiracaoRefreshToken() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
