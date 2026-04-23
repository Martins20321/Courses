package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    //Garante que vai ser executada uma única vez antes de cada requisição
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Recuperado o cabeçalho
        var tokenJWT = recuperarToken(request);

        System.out.println(tokenJWT);
        //Continuar o fluxo da requisição
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null){
            throw new RuntimeException("Token JWT não enviado no cabeçalho Authorization!");
        }

        return authorizationHeader.replace("Bearer ","");
    }
}
