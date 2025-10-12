package com.bikerapp.api_gateway.filter;

import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Request received: "+exchange.getRequest().getPath());
        String cookie = String.valueOf(exchange.getRequest().getCookies().getFirst("token"));

        System.out.println("Cookie: "+cookie);
        return chain.filter(exchange);
    }
}
