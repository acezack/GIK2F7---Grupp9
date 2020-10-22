package com.example.grupp9okw.controller;

import com.example.grupp9okw.model.News;
import com.example.grupp9okw.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orreskogenskickers")
public class MainController {

    @Autowired
    private NewsService newsService;

    @GetMapping(path="")
    public String getPageIndex(Model model) {
        List summaryNews = new ArrayList();

        for (News news : newsService.getAllNews()) {
            News tempNews = new News();
            tempNews = news;
            //Trimmar brödtexten till maximalt 150 tecken
            try {
                tempNews.setText(news.getText().substring(0, 150));
            }
            catch(StringIndexOutOfBoundsException exception) {

            }

            summaryNews.add(tempNews);
        }
        model.addAttribute("summarynews", summaryNews);
        return "index";
        }

    @GetMapping(path="/medlemmar")
    public String getPageMedlemmar() {
        return "members";
    }

    @GetMapping(path="/bilder")
    public String getPagePictures() {
        return "images";
    }

    @GetMapping(path="/lankar")
    public String getPageLinks() {
        return "links";
    }

}