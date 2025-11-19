package com.iambiker.webservice.util;

public class DefaultRedirectManager implements RedirectManager {
    @Override
    public String redirect(String url) {
        return "redirect:"+url;
    }
}
