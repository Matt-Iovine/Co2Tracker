package mattia.archeometra.co2tracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Configuro la FilterChain per richiedere l'autenticazione per tutte le richieste al percorso del mio controller
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("rest/api/v0/sensor-readings/**").authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //Creo gli utenti assegnando il ruolo che rappresenterà la città per la quale potranno leggere i valori di Co2
        UserDetails barcelona = User.withUsername("barcelona")
                .password(passwordEncoder().encode("password"))
                .roles("BARCELONA")
                .build();

        UserDetails wien = User.withUsername("wien")
                .password(passwordEncoder().encode("password"))
                .roles("WIEN")
                .build();

        UserDetails munchen = User.withUsername("munchen")
                .password(passwordEncoder().encode("password"))
                .roles("MUNCHEN")
                .build();

        Set<UserDetails> userDetailsSet = new HashSet<>();
        userDetailsSet.add(barcelona);
        userDetailsSet.add(wien);
        userDetailsSet.add(munchen);

        return new InMemoryUserDetailsManager(userDetailsSet);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
