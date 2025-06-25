package api.gateway.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import api.gateway.api_gateway.filter.JwtAuthFilter;

@Configuration
public class GatewayConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public GatewayConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("auth", r -> r.path("/auth/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://auth-service"))
            .route("user", r -> r.path("/users/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://user"))
            .route("product", r -> r.path("/products/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://product"))
            .route("order", r -> r.path("/orders/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://order"))
            .route("payment", r -> r.path("/payments/**")
                .filters(f -> f.filter(jwtAuthFilter))
                .uri("lb://payment"))
            .build();
    }
}
