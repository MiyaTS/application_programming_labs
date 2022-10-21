package com.my.game_room.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * class for database contact
 */

public class DatabaseManager {
    private static Connection connection;

    /**
     * connecting to the database
     * @return connection
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "postgrespw");
        }
        return connection;
    }

    /**
     * closing the connection to the database
     */
    public static void closeConnection() {
        try {
            if (connection.isClosed()){
                return;
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Не вдалось закрити конект до бд");
        }
    }
}
