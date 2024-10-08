package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Accesso {
    // Database connection settings for PostgreSQL
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/nome_del_db";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public static void eseguiLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("UserID: ");
        String userID = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (verificaCredenziali(userID, password)) {
            System.out.println("Login effettuato con successo.");
            eseguiOperazioniPostLogin(scanner, userID);
        } else {
            System.out.println("Credenziali non valide.");
        }
    }

    private static boolean verificaCredenziali(String userID, String password) {
        String query = "SELECT COUNT(*) FROM OperatoriRegistrati WHERE userID = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userID);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void eseguiOperazioniPostLogin(Scanner scanner, String userID) {
        boolean continua = true;
        while (continua) {
            System.out.println("\nCosa vuoi fare dopo il login?");
            System.out.println("1. Registra Centro Aree");
            System.out.println("2. Visualizza Centri di Monitoraggio");
            System.out.println("3. Inserisci parametri climatici nel DB");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 0:
                    continua = false;
                    break;
                case 1:
                    CentroMonitoraggio.registraCentroAree(userID);
                    break;
                case 2:
                    CentroMonitoraggio.visualizzaCentriMonitoraggio(userID);
                    break;
                case 3:
                    inserisciParametriClimatici(userID);
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    private static void inserisciParametriClimatici(String userID) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Temperatura: ");
        double temperatura = scanner.nextDouble();
        System.out.print("Umidità: ");
        double umidita = scanner.nextDouble();
        scanner.nextLine();

        String insertQuery = "INSERT INTO ParametriClimatici (userID, temperatura, umidita) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, userID);
            stmt.setDouble(2, temperatura);
            stmt.setDouble(3, umidita);
            stmt.executeUpdate();

            System.out.println("Parametri climatici inseriti nel database con successo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
