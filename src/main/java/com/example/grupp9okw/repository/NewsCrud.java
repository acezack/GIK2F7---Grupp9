package com.example.grupp9okw.repository;

import com.example.grupp9okw.model.Admin;
import com.example.grupp9okw.model.News;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class NewsCrud implements INewsCrud {
    private Connection connection;

    @Override
    public List<News> getAllNews() {
        List<News> news = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/IfSCHjTL9N", "IfSCHjTL9N", "DDUbgqOo1P");
            Statement statementNews  = connection.createStatement();
            Statement statementAdmin  = connection.createStatement();
            String sqlSelectAllNews = "SELECT * FROM news";
            String sqlSelectAllAdmin = "SELECT * FROM admin WHERE adminid = ";
            ResultSet resultSetNews = statementNews.executeQuery(sqlSelectAllNews);

            //TAR IN ALLA NYHETER
            while (resultSetNews.next()) {
                News tempNews = new News();
                Admin tempAdmin = new Admin();
                int adminId = 0;

                //TAR FÖRST IN ADMIN
                //sparar ner id på admin som skrivit artikeln
                adminId = resultSetNews.getInt("addedby");
                //Hämtar in datat från databasen och sparar ner den admin som skrivit artikeln genom sitt id till ett Admin-objekt
                ResultSet resultSetAdmin = statementAdmin.executeQuery(sqlSelectAllAdmin.concat(Integer.toString(adminId)));
                while (resultSetAdmin.next()) {
                    tempAdmin.setAdminId(resultSetAdmin.getInt("adminid"));
                    tempAdmin.setFirstName(resultSetAdmin.getString("firstname"));
                    tempAdmin.setLastName(resultSetAdmin.getString("lastname"));
                    tempAdmin.setEmail(resultSetAdmin.getString("email"));
                    tempAdmin.setUsername(resultSetAdmin.getString("username"));
                    tempAdmin.setPassword(resultSetAdmin.getString("password"));
                }

                tempNews.setNewsId(resultSetNews.getInt("newsid"));
                tempNews.setHeading(resultSetNews.getString("heading"));
                tempNews.setDate(resultSetNews.getString("date"));
                tempNews.setPicture(resultSetNews.getString("picture"));
                tempNews.setAddedBy(tempAdmin);
                tempNews.setText(resultSetNews.getString("text"));
                news.add(tempNews);
            }
            resultSetNews.close();
            statementNews.close();
            connection.close();
            return news;
        }
        catch (SQLException ex) {
            Logger.getLogger(NewsCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public News getNews(int nId) {
        return null;
    }

    @Override
    public News updateNews(int nId) {
        return null;
    }

    @Override
    public Boolean deleteNews(int nId) {
        return null;
    }

    @Override
    public Boolean addNews(News news) {
        return null;
    }
}
