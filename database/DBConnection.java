package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private DBConnection() {}

    private static final String URL =
            System.getenv("DB_URL");

    private static final String USER =
            System.getenv("DB_USER");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    public static Connection getConnection()
            throws SQLException {

        // Force UTF-8 encoding - build proper URL
        String baseUrl = URL;
        if (baseUrl == null) {
            throw new IllegalStateException("DB_URL environment variable not set");
        }

        // Remove any existing characterEncoding to avoid duplicates
        baseUrl = baseUrl.replaceAll("[?&]characterEncoding=[^&]*", "");
        baseUrl = baseUrl.replaceAll("[?&]stringtype=[^&]*", "");

        // Append encoding params
        String fullUrl = baseUrl + (baseUrl.contains("?") ? "&" : "?")
                + "characterEncoding=UTF-8"
                + "&stringtype=unspecified"
                + "&options=-c%20client_encoding=utf8";

        Connection conn = DriverManager.getConnection(fullUrl, USER, PASSWORD);

        // Double-check: set session encoding
        try (var stmt = conn.createStatement()) {
            stmt.execute("SET client_encoding = 'UTF8'");
        }

        return conn;
    }
}