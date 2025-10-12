package com.bikerapp.api_gateway.filter;

import com.bikerapp.api_gateway.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {
    private final JwtUtils jwtUtils;
    private List<String> publicPaths = List.of(
            "/web/auth/**",
            "/web/style/**",
            "/web/images/**",
            "/web/script/**"
    );
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = String.valueOf(exchange.getRequest().getPath());
        if (isPathPublic(path)) {
            chain.filter(exchange);
        }
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
        String jwtToken;
        try{
            jwtToken = cookie.getValue();
        }
        catch (NullPointerException e) {
            System.out.println("No token");
            return chain.filter(exchange);
        }

        if (isTokenValid(jwtToken)) {
            System.out.println("Token is valid");
        }
        else {
            System.out.println("token is not valid");
        }
        return chain.filter(exchange);
    }

    private boolean isTokenValid(String token) {
        return jwtUtils.validateToken(token);
    }

    private boolean isPathPublic(String path) {
        for (String publicPath: publicPaths) {
            if (pathMatcher.match(publicPath, path))
                return true;
        }
        return false;
    }
}
