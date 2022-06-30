package ma.itroad.ram.kpi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.*;
import org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter;


import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter.Directive.CACHE;
import static org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter.Directive.COOKIES;

@Configuration
@EnableWebFluxSecurity

public class SecurityConfig {

    @Value("${port.front-end}")
    private String serverPort;

    @Value("${host.front-end}")
    private String host;

    private static final String FRONTEND_URL = "http://localhost:3000";

    //private static final String FRONTEND_URL ="http://20.86.130.237:3000";

    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        logoutSuccessHandler.setPostLogoutRedirectUri(FRONTEND_URL);
        return logoutSuccessHandler;
    }

    @Bean
    public SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
        ServerLogoutHandler securityContext = new
                SecurityContextServerLogoutHandler();
        ClearSiteDataServerHttpHeadersWriter writer = new
                ClearSiteDataServerHttpHeadersWriter(CACHE, COOKIES);
        ServerLogoutHandler clearSiteData = new
                HeaderWriterServerLogoutHandler(writer);
        DelegatingServerLogoutHandler logoutHandler = new
                DelegatingServerLogoutHandler(securityContext, clearSiteData);
        http
                // ...
                .logout()
                .logoutHandler(logoutHandler)
                .logoutSuccessHandler(oidcLogoutSuccessHandler());
        http
           // by default uses a Bean by the name of corsConfigurationSource
                .cors(withDefaults());
        http.csrf().disable()
                .authorizeExchange(
                        exchanges -> exchanges
                                .pathMatchers("/manifest.json", "/*.png", "/static/**", "/api/user", "actuator/refresh")
                                .permitAll().anyExchange().authenticated())
                .oauth2Login().and().oauth2ResourceServer().jwt();
        return http.build();
    }


}
