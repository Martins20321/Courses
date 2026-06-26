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

    public Boolean verificarCodigo(String code, Usuario usuarioLogado) {
        var secretDecodificada = TOTPSecret.Companion.fromBase32EncodedString(usuarioLogado.getSecret());

        //Gerando o código para 3 janelas, pois o usuário pode ter horários diferentes
        var generator = new TOTPGenerator();

        var codigoAplicacao = generator.generate(secretDecodificada, 1, 1);

        return codigoAplicacao.stream().anyMatch(totp ->
                totp.getValue().equals(code));
    }
}
