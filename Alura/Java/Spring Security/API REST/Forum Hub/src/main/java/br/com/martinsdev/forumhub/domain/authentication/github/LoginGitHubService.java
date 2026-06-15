package br.com.martinsdev.forumhub.domain.authentication.github;

import org.springframework.stereotype.Service;

@Service
public class LoginGitHubService {

    public String gerarUrl() {
        return "https://github.com/login/oauth/authorize" +
                "?client_id=Ov23libsEZ5ky0mMT9yq" +
                "&redirect_uri=http://localhost:8080/login/github/autorizado" +
                "&scope=read:user,user:email";
    }
}
