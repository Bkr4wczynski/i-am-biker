package com.iambiker.webservice.configuration;

import com.iambiker.webservice.util.ApiGatewayRedirectManager;
import com.iambiker.webservice.util.DefaultRedirectManager;
import com.iambiker.webservice.util.RedirectManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@Getter
@Setter
public class AppConfiguration {
    private boolean isApiGatewayEnabled = true;
    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> filter = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }

    @Bean
    public RedirectManager redirectManager() {
        if (isApiGatewayEnabled)
            return new ApiGatewayRedirectManager();
        return new DefaultRedirectManager();
    }

}
