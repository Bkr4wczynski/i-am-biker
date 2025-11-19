package com.iambiker.apigateway.jwtauthentication.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class PathManager {
    private final List<String> publicPaths = List.of(
            "/web/authentication/**",
            "/authentication/**",
            "/web/style/**",
            "/web/images/**",
            "/web/script/**",
            "/maintenance/**"
    );
    private final AntPathMatcher pathMatcher;

    public PathManager() {
        pathMatcher = new AntPathMatcher();
    }

    public boolean isPathPublic(String path) {
        for (String publicPath: publicPaths) {
            if (pathMatcher.matchStart(publicPath, path))
                return true;
        }
        return false;
    }

    private Mono<Void> redirect(ServerWebExchange exchange, String reason, String redirectUrl) {
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
        log.info("Redirected to path: {}", redirectUrl);
        return exchange.getResponse().setComplete();
    }


    public Mono<Void> redirectToLoginPage(ServerWebExchange exchange, String reason) {
        String redirectUrl = "/web/authentication/login?error=" + UriUtils.encode(reason, StandardCharsets.UTF_8);
        return redirect(exchange, reason, redirectUrl);
    }
}
