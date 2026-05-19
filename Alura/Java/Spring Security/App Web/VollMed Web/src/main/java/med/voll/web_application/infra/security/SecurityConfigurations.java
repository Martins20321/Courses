package med.voll.web_application.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
        UserDetails user3 = User.builder()
                .username("jose@email.com")
                .password("{noop}jose123")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests(reqMatcherRegistry -> {reqMatcherRegistry
                .requestMatchers("/css/**", "/js/**", "/assest/**").permitAll()
                .anyRequest().authenticated();})
                .formLogin(flogin -> flogin
                        .loginPage("/login").defaultSuccessUrl("/").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/logout").permitAll())
                .build();
    }
}
