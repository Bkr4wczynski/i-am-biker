package com.bikerapp.api_gateway.filter;

import com.bikerapp.api_gateway.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationFilter implements GlobalFilter {
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Request received: "+exchange.getRequest().getPath());
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("token");
        String jwtToken;
        try{
            jwtToken = cookie.getValue();
        }
        catch (NullPointerException e) {
            System.out.println("No token");
            return chain.filter(exchange);
        }

        System.out.println("token: "+jwtToken);
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
}
