package br.com.martinsdev.forumhub.domain.authentication.github;

import br.com.martinsdev.forumhub.infra.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGitHubService {

    @Value("${github.oauth.client.id}")
    private String client_id;

    @Value("${github.oauth.client.secret}")
    private String client_secret;
    private final String redirect_uri = "http://localhost:8080/login/github/autorizado";
    private final RestClient restClient;

    public LoginGitHubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String gerarUrl() {
        return "https://github.com/login/oauth/authorize" +
                "?client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&scope=read:user, user:email,public_repo";
    }

    private String obterTokenAcesso(String code) {
        var response = restClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", client_id, "client_secret", client_secret, "redirect_uri", redirect_uri))
                .retrieve()
                .body(Map.class);
        return response.get("access_token").toString(); //Extraindo apenas o token
    }

    public DadosGitHubUsuario obterDadosUsuario(String code) {
        var token = obterTokenAcesso(code);

        //Cria um cabeçalho com um bearerToken
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var response = restClient.get()
                .uri("https://api.github.com/user")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosGitHubUsuario.class);

        var responseEmail = restClient.get()
                .uri("https://api.github.com/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers)) //Envia o cabeçalho
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosEmailDTO[].class);

        for (DadosEmailDTO d : responseEmail) {
            if (d.primary() && d.verified()) {
                return new DadosGitHubUsuario(response.nomeCompleto(), d.email(), response.nickName(), response.biografia());
            }
        }
        throw new RegraDeNegocioException("Não foi possível obter o email do GitHub");
    }
}