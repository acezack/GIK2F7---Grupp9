package com.example.grupp9okw.repository;

import com.example.grupp9okw.model.News;

import java.util.List;

public interface INewsCrud {

    public List<News> getAllNews();
    public News getNews(int nId);
    public Boolean updateNews(News news);
    public Boolean deleteNews(int nId);
    public Boolean addNews(News news);

}
