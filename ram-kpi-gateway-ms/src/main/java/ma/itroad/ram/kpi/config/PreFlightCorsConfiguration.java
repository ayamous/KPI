package ma.itroad.ram.kpi.config;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class PreFlightCorsConfiguration {

    @Bean
    public CorsWebFilter corsFilter() {
        return new CorsWebFilter(corsConfigurationSource());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        //config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
       // config.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }

 /*   @Bean
    public RouteLocator theRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r ->
                        r.path("/auth/**")
                                .filters(f -> f.rewriteResponseHeader("Referrer-Policy", "no-referrer", "http://localhost:3000"))
                                .uri("http://localhost:8086"))
                .build();
    }*/


}
