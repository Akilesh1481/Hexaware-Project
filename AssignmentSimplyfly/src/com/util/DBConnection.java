package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String url="jdbc:mysql://localhost:3306/assignment1?createDatabaseIfNotExist=true";
    private String userDb = "root";
    private String passDB = "Achu2004@";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Connection connection;

    public Connection dbConnect() {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            connection =  DriverManager.getConnection(url,userDb,passDB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public void dbClose(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}