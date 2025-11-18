package com.iambiker.webservice.webcontent.tips;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SafetyTipsController {
    @GetMapping("/safety-tips")
    public String displaySafetyTips() {
        return "content/safetyTips";
    }
}
