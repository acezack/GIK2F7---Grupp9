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
            connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/IfSCHjTL9N", "IfSCHjTL9N", "DDUbgqOo1P");
            Statement statementAdmin = connection.createStatement();
            String sqlSelectAllAdmin = "SELECT * FROM admin";
            ResultSet resultSetAdmin = statementAdmin.executeQuery(sqlSelectAllAdmin);

            while (resultSetAdmin.next()) {
                if (
                        admin.getUsername().equals(resultSetAdmin.getString("username")) &&
                        admin.getPassword().equals(resultSetAdmin.getString("password"))
                ) {
                    resultSetAdmin.close();
                    statementAdmin.close();
                    connection.close();
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(NewsCrud.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
