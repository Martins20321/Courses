package br.com.martinsdev.forumhub.infra.security.totp;

import br.com.martinsdev.forumhub.domain.usuario.Usuario;
import com.atlassian.onetime.core.TOTPGenerator;
import com.atlassian.onetime.model.TOTPSecret;
import com.atlassian.onetime.service.RandomSecretProvider;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    public String gerarSecret() {
        return new RandomSecretProvider().generateSecret().getBase32Encoded();
    }

    public String gerarQrCode(Usuario usuario) {
        var issuer = "Forum Hub";
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, usuario.getUsername(), usuario.getSecret(), issuer
        );
    }

    public boolean validarCodigo(String code, Usuario usuarioLogado) {
        var secretDecodificada = TOTPSecret.Companion.fromBase32EncodedString(usuarioLogado.getSecret());
        var generator = new TOTPGenerator();

        var codigosAutenticacao = generator.generate(secretDecodificada, 1 , 1);

        return codigosAutenticacao.stream().anyMatch(
                totp -> totp.getValue().equals(code));
    }
}
