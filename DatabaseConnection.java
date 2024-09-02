import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection() {
    public static void main(String[] args) {
        // URL del database, formato: jdbc:postgresql://host:port/database
        String url = "jdbc:postgresql://localhost:5432/MAINDB";
        String user = "postgres";
        String password = "Pap3r1n0";

        Connection conn = null;
        try {
            // Connessione al database
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();  // Chiudi la connessione
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
