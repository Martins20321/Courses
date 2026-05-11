package med.voll.web_application.infra.exception.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public UserDetailsService dadosUsuariosCadastrados() {
        UserDetails user1 = User.builder()
                .username("bruno@email.com")
                .password("{noop}bruno123")
                .build();
        UserDetails user2 = User.builder()
                .username("anna@email.com")
                .password("{noop}anna123")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
