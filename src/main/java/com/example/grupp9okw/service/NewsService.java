package com.example.grupp9okw.service;

import com.example.grupp9okw.model.News;
import com.example.grupp9okw.repository.IAdminVerify;
import com.example.grupp9okw.repository.INewsCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    //dependency injection  - Spring containern instansierar automatiskt ett objekt av klassen vi vill använda
    @Autowired
    private INewsCrud newsCrud;
    private IAdminVerify adminVerify;

    public List<News> getAllNews() {
        return newsCrud.getAllNews();
    }

    public News getNews(int nId) {
        return newsCrud.getNews(nId);
    }
    
}
