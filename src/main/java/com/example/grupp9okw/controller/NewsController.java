package com.example.grupp9okw.controller;

import com.example.grupp9okw.model.News;
import com.example.grupp9okw.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orreskogenskickers")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping(path ="/admin")
    public String getPageAdmin(Model model) {

        int numberOfNews = 0;
        List adminNews = new ArrayList();

        //loopar igenom alla nyheter
        for (News news : newsService.getAllNews()) {
            News tempNews = new News();
            tempNews = news;
            tempNews.setText(news.getText().substring(0, 50));
            adminNews.add(tempNews);
            numberOfNews++; //räknar antal news
        }
        model.addAttribute("adminnews", adminNews);

        model.addAttribute("countnews", numberOfNews);
        model.addAttribute("news", newsService.getAllNews());
        return "a_main";
    }

    @GetMapping(path="/nyheter")
    public String getAllNews(Model model) {
        model.addAttribute("news", newsService.getAllNews());
        return "news";
    }

    @GetMapping(path="/nyheter/")
    public String getNews(Model model, @RequestParam int newsId) {

        model.addAttribute("news", newsService.getNews(newsId));

        return "a_news";
    }

    @GetMapping(path="/formlaggtillnyhet/")
    public String getInputNewsPage(Model model) {

        return "a_news_add";
    }
    @GetMapping(path="/formeditnyhet/")
    public String getInputEditNewsPage(Model model) {

        return "a_news_edit";
    }
}