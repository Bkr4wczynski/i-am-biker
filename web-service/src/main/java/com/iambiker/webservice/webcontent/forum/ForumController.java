package com.iambiker.webservice.webcontent.forum;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forum")
public class ForumController {
    @GetMapping
    public String displayMainForumPage() {
        return "forum/mainForumPage";
    }
}
