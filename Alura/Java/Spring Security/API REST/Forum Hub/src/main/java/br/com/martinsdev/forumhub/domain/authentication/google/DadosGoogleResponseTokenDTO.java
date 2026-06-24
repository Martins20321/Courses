package br.com.martinsdev.forumhub.domain.authentication.google;

public record DadosGoogleResponseTokenDTO(String id_token,
                                          String access_token,
                                          String refresh_token) {
}
