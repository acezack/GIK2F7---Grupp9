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

            //LOOPAR IGENOM ALLA NYHETER I TABELLEN news
            while (resultSetNews.next()) {
                News tempNews = new News();
                Admin tempAdmin = new Admin();
                int adminId;

                //TAR FÖRST IN ADMIN
                //sparar ner id på admin som skrivit artikeln från den främmande nyckeln addedby i taballen news
                adminId = resultSetNews.getInt("addedby");
                // vi lägger till värdet från den främmande nyckeln addedby i news till sql-satsen sqlSelectAllAdmin
                ResultSet resultSetAdmin = statementAdmin.executeQuery(sqlSelectAllAdmin.concat(Integer.toString(adminId)));
                // vi loopar igenom datat i tabellen admin tills vi hittar fältet där adminid är samma som det nedsparade
                //värdet i intvariablen adminId
                while (resultSetAdmin.next()) {
                    //Hämtar in det funna datat och spar ner det till ett Adminobjekt
                    tempAdmin.setAdminId(resultSetAdmin.getInt("adminid"));
                    tempAdmin.setFirstName(resultSetAdmin.getString("firstname"));
                    tempAdmin.setLastName(resultSetAdmin.getString("lastname"));
                    tempAdmin.setEmail(resultSetAdmin.getString("email"));
                    tempAdmin.setUsername(resultSetAdmin.getString("username"));
                    tempAdmin.setPassword(resultSetAdmin.getString("password"));
                }

                //hämtar in och skapar ett Newsobjekt
                tempNews.setNewsId(resultSetNews.getInt("newsid"));
                tempNews.setHeading(resultSetNews.getString("heading"));
                tempNews.setDate(resultSetNews.getString("date"));
                tempNews.setPicture(resultSetNews.getString("picture"));
                tempNews.setAddedBy(tempAdmin); //skickar in ett Adminobjekt med den som skrivit nyheten
                tempNews.setText(resultSetNews.getString("text"));
                news.add(tempNews); //lägger till objektet i listan
            }
            resultSetNews.close();
            statementNews.close();
            connection.close();
            return news; //returnerar listan med alla nyheter med addedby konverterat till ett Adminobjekt
        }
        catch (SQLException ex) {
            Logger.getLogger(NewsCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public News getNews(int nId) {
            News tempNews = new News();
            for (News news : getAllNews()) { //återanvänder getAllNews()
                if (news.getNewsId() == nId) {
                    tempNews = news;
                    return tempNews;
                }
            }
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
        try {
            connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/IfSCHjTL9N", "IfSCHjTL9N", "DDUbgqOo1P");
            String sqlAddNews = "INSERT INTO `news` (`newsid`, `heading`, `date`, `picture`, `addedby`, `text`) " +
                    "VALUES (NULL, ?, CURRENT_DATE(), ?, '1', ?)";


            PreparedStatement statement = connection.prepareStatement(sqlAddNews);
            statement.setString(1, news.getHeading());
            statement.setString(2, news.getPicture());
            statement.setString(3, news.getText());

            statement.execute();
            statement.close();
            connection.close();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(NewsCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
