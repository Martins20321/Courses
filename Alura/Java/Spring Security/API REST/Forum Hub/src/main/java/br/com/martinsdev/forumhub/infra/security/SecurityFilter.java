package br.com.martinsdev.forumhub.infra.security;

import br.com.martinsdev.forumhub.domain.usuario.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Recuperando o token pelo cabeçalho
        var tokenJWT = recoverToken(request);

        if (tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);

            var usuario = service.loadUserByUsername(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Teve retorno null, no recoverToken
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authenticationHeader = request.getHeader("Authorization");

        if (authenticationHeader != null){
            return authenticationHeader.replace("Bearer ","").trim();
        }

        return null;
    }
}
