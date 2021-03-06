package com.example.grupp9okw.controller;

import com.example.grupp9okw.model.Admin;
import com.example.grupp9okw.model.News;
import com.example.grupp9okw.repository.AdminVerify;
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

    @GetMapping(path = "/admin")
    public String getPageAdmin(Model model) {

        int numberOfNews = 0;
        List adminNews = new ArrayList();

        //loopar igenom alla nyheter med metoden getAllNews() från servicelagret
        for (News news : newsService.getAllNews()) {
            News tempNews = new News();
            tempNews = news;
            try {
                tempNews.setText(news.getText().substring(0, 50));
            } catch (StringIndexOutOfBoundsException exception) {

            }
            adminNews.add(tempNews);
            numberOfNews++; //räknar antal news
        }
        model.addAttribute("adminnews", adminNews);
        model.addAttribute("countnews", numberOfNews);
        model.addAttribute("news", newsService.getAllNews());
        return "a_main";
    }

    @GetMapping(path = "/nyheter")
    public String getAllNews(Model model) {
        model.addAttribute("news", newsService.getAllNews());
        return "news";
    }

    @GetMapping(path = "/nyheter/")
    public String getNews(Model model, @RequestParam int newsId) {

        model.addAttribute("news", newsService.getNews(newsId));

        return "a_news";
    }

    @GetMapping(path = "/formlaggtillnyhet/")
    public String inputNewsPage(Model model) {

        return "a_news_add";
    }

    @PostMapping(path = "/laggtillnyhet/")
    public String addNews(Model model, @RequestParam Map<String, String> allFormInput) {
        News tempNews = new News();
        tempNews.setHeading(allFormInput.get("header"));
        tempNews.setPicture(allFormInput.get("picture"));
        tempNews.setText(allFormInput.get("bodytext"));

        if (newsService.addNews(tempNews)) {
            model.addAttribute("news", tempNews);
            return "a_news_added";
        } else {
            return "error";
        }
    }

    @GetMapping(path = "/redigeranyhet/")
    public String inputUpdateNewsPage(Model model, @RequestParam int newsId) {

        model.addAttribute("news", newsService.getNews(newsId));
        return "a_news_edit";
    }


    @PostMapping(path = "/uppdateranyhet/")
    public String updateNews(@RequestParam Map<String, String> allFormInput, Model model) {
        News tempNews = new News();
        tempNews.setNewsId(Integer.valueOf(allFormInput.get("newsId")));
        tempNews.setHeading(allFormInput.get("heading"));
        tempNews.setPicture(allFormInput.get("picture"));
        tempNews.setText(allFormInput.get("text"));

        if (newsService.updateNews(tempNews)) {
            model.addAttribute("test", tempNews);
            //returnerar nyhetens egna sida som blivit uppdaterad
            return "redirect:/orreskogenskickers/nyheter/?newsId=" + Integer.toString(tempNews.getNewsId());
        } else {
            return "error";
        }
    }

    @GetMapping(path = "/raderanyhet/")
    public String deleteNews(@RequestParam int newsId) {

        if (newsService.deleteNews(newsId)) {
            return "a_news_removed";
        } else {
            return "error";
        }

    }

    @GetMapping(path="/loggain/")
    public String loginAdmin() {

        return "a_login";
    }

    @PostMapping("/loggainkontroll/")
    public String loginAdminCheck(@RequestParam Map<String, String> allFormInput) {
        //Skapar en temporär instans av Admin för att skicka till kontroll.
        Admin tempAdmin = new Admin();
        tempAdmin.setUsername(allFormInput.get("username"));
        tempAdmin.setPassword(allFormInput.get("password"));
        if (newsService.checkCredentials(tempAdmin)) {
            return "redirect:/orreskogenskickers/admin";
        } else {
            return "error";
        }
    }
}

