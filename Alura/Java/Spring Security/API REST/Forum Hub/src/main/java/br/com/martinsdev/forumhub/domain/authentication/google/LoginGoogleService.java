package br.com.martinsdev.forumhub.domain.authentication.google;

import br.com.martinsdev.forumhub.domain.usuario.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGoogleService {

    @Value("${google.oauth.client.id}")
    private String client_id;

    @Value("${google.oauth.client.secret}")
    private String client_secret;
    private final String redirect_uri = "http://localhost:8080/login/google/autorizado";
    private final String grant_type = "authorization_code";
    private final RestClient restClient;
    private final UsuarioRepository repository;

    public LoginGoogleService(RestClient.Builder restClientBuilder, UsuarioRepository repository) {
        this.restClient = restClientBuilder.build();
        this.repository = repository;
    }

    public String gerarUrlAutorizacao() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&scope=https://www.googleapis.com/auth/userinfo.email" +
                "&response_type=code" +
                "&access_type=offline" +
                "&prompt=consent";
    }

    //Obtendo o token para poder pegar os dados do usuário
    public DadosGoogleResponseTokenDTO obterTokens(String code) {
        var response = restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", client_id, "client_secret", client_secret, "redirect_uri", redirect_uri, "grant_type", grant_type))
                .retrieve()
                .body(DadosGoogleResponseTokenDTO.class);
        return new DadosGoogleResponseTokenDTO(response.id_token(), response.access_token(), response.refresh_token());
    }

    public String obterEmail(String code) {
        var tokens = obterTokens(code);

        DecodedJWT decodedJWT = JWT.decode(tokens.id_token());
        System.out.println(decodedJWT.getClaims());

        return decodedJWT.getClaim("email").asString();
    }

    public boolean existeEmail(String email) {
        return repository.existsUsuarioByEmail(email);
    }
}
