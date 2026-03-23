package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static final String DATABASE_NAME = "db_session12";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/"+DATABASE_NAME;
    private static final String USERNAME = "stargolem";
    private static final String PASSWORD = "Stargolem90@";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            //Nạp lớp thư viện
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(CONNECTION_STRING,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
