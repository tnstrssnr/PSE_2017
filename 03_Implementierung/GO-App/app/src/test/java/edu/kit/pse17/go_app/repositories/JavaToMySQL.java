package edu.kit.pse17.go_app.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 */
public class JavaToMySQL {

    // JDBC URL, username and password of MySQL server
    private static final String URL = "jdbc:mysql://i43pc164.ipd.kit.edu:3306/PSESoSe17Gruppe3";
    private static final String USER = "PSESoSe17User3";
    private static final String PASSWORD = "vAprU3acuxux3S8a";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static long getGoId(long groupId) {
        long goId = -1;
        String query = "SELECT go_id FROM `GOS` WHERE group_id = " + groupId;

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                goId = rs.getLong(1);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection, stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return goId;
    }

}