package api.gateway.api_gateway.filter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import api.gateway.api_gateway.util.JwtUtil;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        final List<String> apiEndpoints = Arrays.asList("/auth/login", "/auth/register");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
            .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(exchange.getRequest())) {
            if (authMissing(request)) return onError(exchange);
            String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    jwtUtil.validateToken(token);
                } catch (Exception e) {
                    return onError(exchange);
                }
            } else {
                return onError(exchange);
            }
        }

        return chain.filter(exchange);

    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

   
}
