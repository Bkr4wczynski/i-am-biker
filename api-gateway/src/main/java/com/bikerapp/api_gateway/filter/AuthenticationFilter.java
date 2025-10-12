package com.bikerapp.api_gateway.filter;

import com.bikerapp.api_gateway.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {
    private final JwtUtils jwtUtils;
    private List<String> publicPaths = List.of(
            "/web/auth/**",
            "/auth/**",
            "/web/style/**",
            "/web/images/**",
            "/web/script/**"
    );
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        System.out.println(path);
        if (isPathPublic(path)) {
            System.out.println("Public path: "+path);
            return chain.filter(exchange);
        }
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
        String jwtToken;
        try{
            jwtToken = cookie.getValue();
            System.out.println(jwtToken);
        }
        catch (NullPointerException e) {
            return redirectToLoginPage(exchange, "Authorization required!");
        }
        try {
            if (isTokenValid(jwtToken)) {
                return chain.filter(exchange);
            }
        } catch (ExpiredJwtException e) {
            return redirectToLoginPage(exchange, "Token has expired!");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        return redirectToLoginPage(exchange, "Please login again");

    }

    private Mono<Void> redirectToLoginPage(ServerWebExchange exchange, String reason) {
        String redirectUrl = "/web/auth/login?error=" + UriUtils.encode(reason, StandardCharsets.UTF_8);
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
        return exchange.getResponse().setComplete();
    }

    private boolean isTokenValid(String token) throws ExpiredJwtException, IllegalArgumentException {
        return jwtUtils.validateToken(token);
    }

    private boolean isPathPublic(String path) {
        for (String publicPath: publicPaths) {
            if (pathMatcher.matchStart(publicPath, path))
                return true;
        }
        return false;
    }
}
