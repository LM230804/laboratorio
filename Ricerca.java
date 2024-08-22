package application;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ricerca {
    public static final String EXCEL_FILE = "geonames-and-coordinates.xlsx";

    public static void cercaAreaGeografica() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleziona un criterio di ricerca:");
        System.out.println("1. Ricerca per denominazione e stato di appartenenza");
        System.out.println("2. Ricerca per coordinate geografiche");
        System.out.print("Scelta: ");
        int scelta = scanner.nextInt();
        scanner.nextLine();

        if (scelta == 1) {
            System.out.print("Denominazione: ");
            String denominazione = scanner.nextLine();
            System.out.print("Stato di appartenenza: ");
            String stato = scanner.nextLine();
            List<String[]> risultati = ricercaPerDenominazione(denominazione, stato);
            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per l'area geografica specificata.");
            } else {
                String[] risultato = risultati.get(0);
                System.out.println("Informazioni sull'area geografica:");
                System.out.println("Denominazione: " + risultato[0]);
                System.out.println("Stato: " + risultato[1]);
                System.out.println("Coordinate: " + risultato[2]);
                System.out.println("Parametro climatico: " + risultato[3]);
                System.out.println("Numero di rilevazioni: " + risultato[4]);
                System.out.println("Statistica del punteggio: " + risultato[5]);
                System.out.println("Commenti degli operatori: " + risultato[6]);
            }
        } else if (scelta == 2) {
            System.out.print("Coordinate geografiche (es. 42.35287, -72.04535): ");
            String coordinate = scanner.nextLine();
            List<String[]> risultati = ricercaPerCoordinate(coordinate);
            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per le coordinate geografiche specificate.");
            } else {
                String[] risultato = risultati.get(0);
                System.out.println("Informazioni sull'area geografica:");
                System.out.println("Denominazione: " + risultato[0]);
                System.out.println("Stato: " + risultato[1]);
                System.out.println("Coordinate: " + risultato[2]);
                System.out.println("Parametro climatico: " + risultato[3]);
                System.out.println("Numero di rilevazioni: " + risultato[4]);
                System.out.println("Statistica del punteggio: " + risultato[5]);
                System.out.println("Commenti degli operatori: " + risultato[6]);
            }
        } else {
            System.out.println("Scelta non valida.");
        }
    }

    private static List<String[]> ricercaPerDenominazione(String denominazione, String stato) {
        List<String[]> risultati = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell denominazioneCell = row.getCell(2);
                Cell statoCell = row.getCell(4);
                if (denominazioneCell != null && statoCell != null &&
                    denominazioneCell.getStringCellValue().equalsIgnoreCase(denominazione) &&
                    statoCell.getStringCellValue().equalsIgnoreCase(stato)) {
                    
                    String[] risultato = new String[7];
                    risultato[0] = row.getCell(1).getStringCellValue();
                    risultato[1] = statoCell.getStringCellValue();
                    risultato[2] = row.getCell(5).getStringCellValue();
                    risultato[3] = row.getCell(6).getStringCellValue();  // Parametro climatico
                    risultato[4] = row.getCell(7).getStringCellValue();  // Numero di rilevazioni
                    risultato[5] = row.getCell(8).getStringCellValue();  // Statistica del punteggio
                    risultato[6] = row.getCell(9).getStringCellValue();  // Commenti degli operatori
                    
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return risultati;
    }

    private static List<String[]> ricercaPerCoordinate(String coordinate) {
        List<String[]> risultati = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell coordinateCell = row.getCell(5);
                if (coordinateCell != null &&
                    coordinateCell.getStringCellValue().equalsIgnoreCase(coordinate)) {

                    String[] risultato = new String[7];
                    risultato[0] = row.getCell(1).getStringCellValue();
                    risultato[1] = row.getCell(4).getStringCellValue();
                    risultato[2] = coordinateCell.getStringCellValue();
                    risultato[3] = row.getCell(6).getStringCellValue();  // Parametro climatico
                    risultato[4] = row.getCell(7).getStringCellValue();  // Numero di rilevazioni
                    risultato[5] = row.getCell(8).getStringCellValue();  // Statistica del punteggio
                    risultato[6] = row.getCell(9).getStringCellValue();  // Commenti degli operatori
                    
                    risultati.add(risultato);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return risultati;
    }
}
