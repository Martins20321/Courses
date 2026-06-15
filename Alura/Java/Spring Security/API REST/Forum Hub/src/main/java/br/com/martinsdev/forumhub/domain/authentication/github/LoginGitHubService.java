package br.com.martinsdev.forumhub.domain.authentication.github;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginGitHubService {

    private final String client_id = "Ov23libsEZ5ky0mMT9yq";
    private final String client_secret = "83010421819e8d4294bc42d520db2da575825ed3";
    private final String redirect_uri = "http://localhost:8080/login/github/autorizado";
    private final RestClient restClient;

    public LoginGitHubService(RestClient.Builder restClient) {
        this.restClient = restClient.build();
    }

    public String gerarUrl() {
        return "https://github.com/login/oauth/authorize" +
                "?client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&scope=read:user,user:email";
    }

    public String obterTokenAcesso(String code) {
        var response = restClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", client_id, "client_secret", client_secret, "redirect_uri", redirect_uri))
                .retrieve()
                .body(String.class);
        return response;
    }
}
