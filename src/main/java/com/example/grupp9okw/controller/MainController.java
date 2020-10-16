package com.example.grupp9okw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/orreskogenskickers")
public class MainController {


    @GetMapping(path="")
    public String getPageIndex(Model model) {
            return "index";
        }

    @GetMapping(path="/medlemmar")
    public String getPageMedlemmar(Model model) {
        return "members";
    }

    @GetMapping(path="/bilder")
    public String getPagePictures(Model model) {
        return "images";
    }

    @GetMapping(path="/lankar")
    public String getPageLinks(Model model) {
        return "links";
    }

}