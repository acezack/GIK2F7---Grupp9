package com.example.grupp9okw.repository;

import com.example.grupp9okw.model.Admin;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class AdminVerify implements IAdminVerify {
    private Connection connection;

    @Override
    public Boolean checkCredentials(Admin admin) {
        try {
            //Skapar en connection mot databasen
            connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/IfSCHjTL9N", "IfSCHjTL9N", "DDUbgqOo1P");
            Statement statementAdmin = connection.createStatement();
            //Säger att vi vill ha allt ur admin-tabellen.
            String sqlSelectAllAdmin = "SELECT * FROM admin";
            ResultSet resultSetAdmin = statementAdmin.executeQuery(sqlSelectAllAdmin);

            //Loopar igenom alla resultat från databasen
            while (resultSetAdmin.next()) {
                //Jämför metodens in-parameter(en instans av Admin-klassen)
                // mot informationen som returnerades från databsen och ser till att det stämmer.
                if (
                        admin.getUsername().equals(resultSetAdmin.getString("username")) &&
                        admin.getPassword().equals(resultSetAdmin.getString("password"))
                ) {
                    //Stänger allting och returnerar sant om ovanståender stämmer.
                    resultSetAdmin.close();
                    statementAdmin.close();
                    connection.close();
                    return true;
                }
            }
            //Om inget som stämmer hittas stänger vi allting och returnerar falskt.
            resultSetAdmin.close();
            statementAdmin.close();
            connection.close();
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(NewsCrud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
