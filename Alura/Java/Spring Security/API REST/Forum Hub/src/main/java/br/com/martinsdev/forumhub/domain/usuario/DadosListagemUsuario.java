package br.com.martinsdev.forumhub.domain.usuario;

public record DadosListagemUsuario(Long id,
                                   String email,
                                   String nomeCompleto,
                                   String nickName,
                                   String headLine,
                                   String biografia) {

    public DadosListagemUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getEmail(), usuario.getNomeCompleto(), usuario.getNickName(), usuario.getHeadLine(), usuario.getBiografia());
    }
}
