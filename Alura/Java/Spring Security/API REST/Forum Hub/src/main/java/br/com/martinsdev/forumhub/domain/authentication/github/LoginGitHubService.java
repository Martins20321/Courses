package br.com.martinsdev.forumhub.domain.authentication.github;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGitHubService {

    private final String client_id = "Ov23libsEZ5ky0mMT9yq";
    private final String client_secret = "83010421819e8d4294bc42d520db2da575825ed3";
    private final String redirect_uri = "http://localhost:8080/login/github/autorizado";
    private final RestClient restClient;

    public LoginGitHubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String gerarUrl() {
        return "https://github.com/login/oauth/authorize" +
                "?client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&scope=read:user,user:email";
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

    public String obterEmail(String code) {
        var token = obterTokenAcesso(code);

        //Cria um cabeçalho com um bearerToken
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        //Faz a requisição
        var response = restClient.get()
                .uri("https://api.github.com/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers)) //Envia o cabeçalho
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosEmailDTO[].class);

        for (DadosEmailDTO d : response) {
            if (d.primary() && d.verified()) {
                return d.email();
            }
        }
        return null;
    }
}