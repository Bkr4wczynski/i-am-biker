package com.bikerapp.api_gateway.filter;

import com.bikerapp.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private RouteValidator validator;

    private RestTemplate template;

    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator validator, RestTemplate template, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.template = template;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey("Authorization"))
                    throw new RuntimeException("Missing authorization header");
            }

            String authHeaders = exchange.getRequest().getHeaders().get("Authorization").getFirst();

            if (authHeaders!=null && authHeaders.startsWith("Bearer ")) {
                authHeaders = authHeaders.substring(7);
            }
            try {
                jwtUtil.validateToken(authHeaders);
            }
            catch (Exception e) {
                throw new RuntimeException("Unauthorized access!");
            }

            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
