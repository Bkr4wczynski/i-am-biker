package com.iambiker.apigateway.jwtauthentication.filter;

import com.iambiker.apigateway.unit.GatewayJwtUtil;
import com.iambiker.apigateway.unit.PathManager;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {
    private final GatewayJwtUtil gatewayJwtUtil;
    private final PathManager pathManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        log.info("Entering path: {}", path);
        if (pathManager.isPathPublic(path)) {
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
            return pathManager.redirectToLoginPage(exchange, "Authorization required!");
        }
        try {
            if (gatewayJwtUtil.validateToken(jwtToken)) {
                log.info("Token is valid");
                return chain.filter(exchange);
            }
        } catch (ExpiredJwtException e) {
            log.info("Received expired token!");
            return pathManager.redirectToLoginPage(exchange, "Token has expired!");
        } catch (IllegalArgumentException e) {
            log.warn("Wrong JWT for path: {}", path);
        }
        log.info("User asked to login again");
        return pathManager.redirectToLoginPage(exchange, "Please login again");

    }

}
