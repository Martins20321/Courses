package br.com.martinsdev.forumhub.domain.refreshtoken;

import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import br.com.martinsdev.forumhub.infra.exception.RefreshTokenException;
import br.com.martinsdev.forumhub.infra.security.DadosRefreshTokenDTO;
import br.com.martinsdev.forumhub.infra.security.DadosResponseToken;
import br.com.martinsdev.forumhub.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final TokenService tokenService;
    private final RefreshTokenRepository repository;

    @Transactional
    public DadosResponseToken atualizarToken(DadosRefreshTokenDTO refreshTokenDTO) {
        tokenService.getSubject(refreshTokenDTO.refreshToken());
        RefreshToken refreshToken = repository.findByToken(refreshTokenDTO.refreshToken())
                .orElseThrow(() -> new RefreshTokenException("Token inválido ou expirado: " + refreshTokenDTO.refreshToken()));

        if (refreshToken.isUtilizado()){
            throw new RefreshTokenException("Este token já foi utilizado!");
        }

        refreshToken.setUtilizado(true);
        repository.save(refreshToken);

        Usuario usuario = refreshToken.getUsuario();

        var tokenJWT = tokenService.gerarToken(usuario);
        var tokenAtualizado = tokenService.gerarRefreshToken(usuario);

        return new DadosResponseToken(tokenJWT, tokenAtualizado, false);
    }
}
