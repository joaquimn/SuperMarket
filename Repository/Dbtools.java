package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Dbtools {

    static Connection varConn = null;
    static PreparedStatement prepareStat = null;

    protected void makeJDBCConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //log("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            varConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "");
            if (varConn != null) {
                //log("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    // Simple log utility
    protected void log(String string) {
        System.out.println(string);

    }

    protected void closeConnection(Connection varConn){

        try {
            varConn.close();
        } catch (SQLException e){
            System.out.println("Problems during close connection");
        }
    }
}
