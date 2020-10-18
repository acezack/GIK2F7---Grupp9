package com.example.grupp9okw.controller;

import com.example.grupp9okw.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orreskogenskickers")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping(path="/nyheter")
    public String getAllNews(Model model) {
        model.addAttribute("news", newsService.getAllNews());
        return "news";
    }
}