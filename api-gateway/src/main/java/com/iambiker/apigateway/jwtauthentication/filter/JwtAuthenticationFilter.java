package com.iambiker.apigateway.jwtauthentication.filter;

import com.iambiker.apigateway.jwtauthentication.util.GatewayJwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {
    private final GatewayJwtUtil gatewayJwtUtil;
    private List<String> publicPaths = List.of(
            "/web/authentication/**",
            "/authentication/**",
            "/web/style/**",
            "/web/images/**",
            "/web/script/**",
            "/maintenance/**"
    );
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        log.info("Entering path: {}", path);
        if (isPathPublic(path)) {
            log.info("Path: {} is public", path);
            return chain.filter(exchange);
        }
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
        String jwtToken;
        try{
            jwtToken = cookie.getValue();
        }
        catch (NullPointerException e) {
            log.info("Unauthorized request!");
            return redirectToLoginPage(exchange, "Authorization required!");
        }
        try {
            if (isTokenValid(jwtToken)) {
                log.info("Token is valid");
                return chain.filter(exchange);
            }
        } catch (ExpiredJwtException e) {
            log.info("Received expired token!");
            return redirectToLoginPage(exchange, "Token has expired!");
        } catch (IllegalArgumentException e) {
            log.warn("Unexpected error for path: {}", path);
            throw new RuntimeException(e);
        }
        log.info("User asked to login again");
        return redirectToLoginPage(exchange, "Please login again");

    }

    private Mono<Void> redirectToLoginPage(ServerWebExchange exchange, String reason) {
        String redirectUrl = "/web/authentication/login?error=" + UriUtils.encode(reason, StandardCharsets.UTF_8);
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
        log.info("Redirected to path: {}", redirectUrl);
        return exchange.getResponse().setComplete();
    }

    private boolean isTokenValid(String token) throws ExpiredJwtException, IllegalArgumentException {
        return gatewayJwtUtil.validateToken(token);
    }

    private boolean isPathPublic(String path) {
        for (String publicPath: publicPaths) {
            if (pathMatcher.matchStart(publicPath, path))
                return true;
        }
        return false;
    }
}
