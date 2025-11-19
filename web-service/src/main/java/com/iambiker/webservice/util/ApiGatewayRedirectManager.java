package com.iambiker.webservice.util;

import org.springframework.stereotype.Component;

@Component
public class ApiGatewayRedirectManager implements RedirectManager {
    @Override
    public String redirect(String url) {
        return "redirect:http://localhost:8765"+url;
    }
}
