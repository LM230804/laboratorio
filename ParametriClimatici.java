package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParametriClimatici {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/nome_del_db"; // Update with your PostgreSQL DB URL
    private static final String DB_USER = "username"; // Update with your PostgreSQL username
    private static final String DB_PASSWORD = "password"; // Update with your PostgreSQL password

    public static void inserisciParametriClimatici(String operatore) {
        Scanner scanner = new Scanner(System.in);

        if (!verificaOperatore(operatore)) {
            System.out.println("L'operatore specificato non esiste.");
        } else {
            List<String> geonames = getGeonames(operatore);
            if (geonames.isEmpty()) {
                System.out.println("Non sono disponibili geonames per l'operatore.");
            } else {
                System.out.println("Geonames disponibili:");

                for (int i = 0; i < geonames.size(); i++) {
                    System.out.println((i + 1) + ". " + geonames.get(i));
                }

                int selectedGeonameIndex = -1;

                while (true) {
                    System.out.print("Seleziona un geoname (1-" + geonames.size() + "): ");
                    selectedGeonameIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (selectedGeonameIndex >= 1 && selectedGeonameIndex <= geonames.size()) {
                        String selectedGeoname = geonames.get(selectedGeonameIndex - 1);

                        if (!isParametroPresente(operatore, selectedGeoname)) {
                            inserisciNuoviParametri(scanner, operatore, selectedGeoname);
                            System.out.println("Parametri climatici inseriti con successo.");
                        } else {
                            System.out.println("Il parametro è già stato inserito.");
                        }
                        return;
                    }

                    System.out.println("Selezione non valida.");
                }
            }
        }
    }

    private static boolean verificaOperatore(String operatore) {
        String query = "SELECT * FROM operatoriregistrati WHERE operator_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, operatore);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static List<String> getGeonames(String operatore) {
        List<String> geonames = new ArrayList<>();

        String query = "SELECT geoname FROM centrimonitoraggio cm "
                     + "JOIN geonamesandcoordinates gc ON cm.operator_id = ? "
                     + "AND cm.nomecentro = gc.name";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, operatore);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                geonames.add(rs.getString("geoname"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return geonames;
    }

    private static boolean isParametroPresente(String operatore, String geoname) {
        String query = "SELECT * FROM parametriclimatici WHERE geoname = ? AND EXISTS "
                     + "(SELECT 1 FROM centrimonitoraggio WHERE operator_id = ? AND nomecentro = ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, geoname);
            stmt.setString(2, operatore);
            stmt.setString(3, geoname);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void inserisciNuoviParametri(Scanner scanner, String operatore, String geoname) {
        String[] parametri = {"vento", "umidita", "pressione", "temperatura", "precipitazioni", "altitudinegiacciai", "massaghiacciai"};

        String insertQuery = "INSERT INTO parametriclimatici (nomecentro, geoname, vento, umidita, pressione, temperatura, precipitazioni, altitudinegiacciai, massaghiacciai) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, geoname);
            stmt.setString(2, geoname);  // assuming geoname is also used as nomecentro
            for (int i = 0; i < parametri.length; i++) {
                System.out.print(parametri[i] + ": ");
                String valore = scanner.nextLine();
                stmt.setString(i + 3, valore);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

