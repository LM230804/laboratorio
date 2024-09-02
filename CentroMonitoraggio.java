package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CentroMonitoraggio {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/nome_del_db"; // Replace with your PostgreSQL DB URL
    private static final String DB_USER = "username"; // Replace with your PostgreSQL username
    private static final String DB_PASSWORD = "password"; // Replace with your PostgreSQL password

    public static void registraCentroAree(String operatore) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome Centro di Monitoraggio: ");
        String nomeCentro = scanner.nextLine();
        System.out.print("Indirizzo Fisico (Via, CAP, località): ");
        String indirizzoFisico = scanner.nextLine();
        System.out.print("Elenco Aree di Interesse (separate da virgola, tutto attaccato): ");
        String elencoAreeInteresse = scanner.nextLine();

        if (verificaAreeInteresse(elencoAreeInteresse)) {
            if (salvaCentroMonitoraggio(nomeCentro, indirizzoFisico, elencoAreeInteresse, operatore)) {
                System.out.println("Centro di Monitoraggio registrato con successo.");
            } else {
                System.out.println("Errore durante la registrazione del Centro di Monitoraggio.");
            }
        } else {
            System.out.println("Una o più Aree di Interesse specificate non esistono.");
        }
    }

    public static void visualizzaCentriMonitoraggio(String operatore) {
        String query = "SELECT nomecentro, indirizzo FROM centrimonitoraggio WHERE operator_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, operatore);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Centri di Monitoraggio registrati per l'operatore " + operatore + ":");
            while (rs.next()) {
                System.out.println("Nome Centro: " + rs.getString("nomecentro"));
                System.out.println("Indirizzo Fisico: " + rs.getString("indirizzo"));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean verificaAreeInteresse(String elencoAreeInteresse) {
        String[] areeInteresse = elencoAreeInteresse.split(",");
        List<String> areeInteresseList = Arrays.asList(areeInteresse);

        String query = "SELECT name FROM geonamesandcoordinates WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (String area : areeInteresseList) {
                stmt.setString(1, area.trim());
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    return false; // Area not found
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, String elencoAreeInteresse, String operatore) {
        String insertQuery = "INSERT INTO centrimonitoraggio (nomecentro, indirizzo, operator_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, nomeCentro);
            stmt.setString(2, indirizzoFisico);
            stmt.setString(3, operatore);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
