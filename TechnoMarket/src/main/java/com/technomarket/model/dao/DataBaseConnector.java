package com.technomarket.model.dao;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataBaseConnector {

    private static final String HOST_NAME = "localhost";
    private static final String PORT = "3306";
    private static final String SCHEME = "technomarket";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static DataBaseConnector instance;
    private Connection connection;


    public static DataBaseConnector getInstance() {
        if (instance == null) {
            instance = new DataBaseConnector();
        }
        return instance;
    }

    private DataBaseConnector() {
        initiateConnection();
    }

    private void initiateConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + SCHEME, USERNAME, PASSWORD);
            System.out.println("Connection initialized!");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to use MySQL. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error communicating with MySQL Database. " + e.getMessage());
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            initiateConnection();
        }
        return connection;
    }
}