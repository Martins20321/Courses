package br.com.martinsdev.forumhub.domain.authentication.github;

import com.fasterxml.jackson.annotation.JsonProperty;

    public record DadosGitHubUsuario(@JsonProperty("name") String nomeCompleto,
                                     String email,
                                     @JsonProperty("login") String nickName,
                                     @JsonProperty("bio") String biografia) {
    }
