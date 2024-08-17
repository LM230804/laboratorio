package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
   private static final String URL = "jdbc:postgresql://localhost:5432/MAINDB";
   private static final String USER = "postgres";
   private static final String PASSWORD = "Pap3r1n0";

   public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection("jdbc:postgresql://localhost:5432/MAINDB", "postgres", "Pap3r1n0");
   }
}
