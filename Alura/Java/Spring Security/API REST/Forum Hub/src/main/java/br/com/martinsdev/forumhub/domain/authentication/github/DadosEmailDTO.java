package br.com.martinsdev.forumhub.domain.authentication.github;

public record DadosEmailDTO(String email,
                            Boolean primary,
                            Boolean verified,
                            String visibility) {
}
