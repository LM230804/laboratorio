package application;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
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
        String coordinate;
        if (scelta == 1) {
            System.out.print("Denominazione: ");
            coordinate = scanner.nextLine();
            System.out.print("Stato di appartenenza: ");
            String stato = scanner.nextLine();
            List<String[]> risultati = ricercaPerDenominazione(coordinate, stato);
            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per l'area geografica specificata.");
            } else {
                String[] risultato = risultati.get(0);
                System.out.println("Informazioni sull'area geografica:");
                System.out.println("Denominazione: " + risultato[0]);
                System.out.println("Stato: " + risultato[1]);
                System.out.println("Coordinate: " + risultato[2]);
                System.out.println();
                System.out.print("Vuoi visualizzare l'area geografica? (s/n): ");
                String sceltaVisualizza = scanner.nextLine();
                if (sceltaVisualizza.equalsIgnoreCase("s")) {
                    visualizzaAreaGeografica(risultato[0]);
                }
            }
        } else if (scelta == 2) {
            System.out.print("Coordinate geografiche (es. 42.35287, -72.04535): ");
            coordinate = scanner.nextLine();
            List<String[]> risultati = ricercaPerCoordinate(coordinate);
            if (risultati.isEmpty()) {
                System.out.println("Nessun risultato trovato per le coordinate geografiche specificate.");
            } else {
                String[] risultato = risultati.get(0);
                System.out.println("Informazioni sull'area geografica:");
                System.out.println("Denominazione: " + risultato[0]);
                System.out.println("Stato: " + risultato[1]);
                System.out.println("Coordinate: " + risultato[2]);
                System.out.println();
                System.out.print("Vuoi visualizzare l'area geografica? (s/n): ");
                String sceltaVisualizza = scanner.nextLine();
                if (sceltaVisualizza.equalsIgnoreCase("s")) {
                    visualizzaAreaGeografica(risultato[0]);
                }
            }
        } else {
            System.out.println("Scelta non valida.");
        }
    }

    private static List<String[]> ricercaPerDenominazione(String denominazione, String stato) {
        List<String[]> risultati = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cellDenominazione = row.getCell(2); // Adjust index as per the actual layout
                Cell cellStato = row.getCell(4); // Adjust index as per the actual layout

                if (cellDenominazione != null && cellStato != null &&
                    cellDenominazione.getStringCellValue().equalsIgnoreCase(denominazione) &&
                    cellStato.getStringCellValue().equalsIgnoreCase(stato)) {
                    String[] risultato = new String[]{
                        row.getCell(1).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(5).getStringCellValue()
                    };
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

        try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cellCoordinate = row.getCell(5); // Adjust index as per the actual layout
                if (cellCoordinate != null && cellCoordinate.getStringCellValue().equalsIgnoreCase(coordinate)) {
                    String[] risultato = new String[]{
                        row.getCell(1).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(5).getStringCellValue()
                    };
                    risultati.add(risultato);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    private static void visualizzaAreaGeografica(String areaGeografica) {
        List<String[]> parametriClimatici = getParametriClimatici(areaGeografica);
        if (parametriClimatici.isEmpty()) {
            System.out.println("Non sono disponibili dati sui parametri climatici per questa area.");
        } else {
            System.out.println("Parametri climatici per l'area " + areaGeografica + ":");
            System.out.println();
            for (String[] parametro : parametriClimatici) {
                System.out.println("Parametro: " + parametro[0]);
                System.out.println("Numero di rilevazioni: " + parametro[1]);
                System.out.println("Statistica del punteggio: " + parametro[2]);
                System.out.println("Commenti degli operatori: " + parametro[3]);
                System.out.println();
            }
        }
    }

    private static List<String[]> getParametriClimatici(String areaGeografica) {
        List<String[]> risultati = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(1); // Assuming climate parameters are on the second sheet
            for (Row row : sheet) {
                Cell cellArea = row.getCell(1); // Adjust index as per the actual layout
                if (cellArea != null && cellArea.getStringCellValue().equals(areaGeografica)) {
                    String[] risultato = new String[]{
                        row.getCell(2).getStringCellValue(),
                        getNumeroRilevazioni(areaGeografica, row.getCell(2).getStringCellValue()),
                        getStatisticaPunteggio(areaGeografica, row.getCell(2).getStringCellValue()),
                        getCommentiOperatori(areaGeografica, row.getCell(2).getStringCellValue())
                    };
                    risultati.add(risultato);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

    private static String getNumeroRilevazioni(String areaGeografica, String parametro) {
        int count = 0;

        try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(1); // Assuming climate parameters are on the second sheet
            for (Row row : sheet) {
                Cell cellArea = row.getCell(1); // Adjust index as per the actual layout
                Cell cellParametro = row.getCell(2); // Adjust index as per the actual layout
                if (cellArea != null && cellParametro != null &&
                    cellArea.getStringCellValue().equals(areaGeografica) &&
                    cellParametro.getStringCellValue().equals(parametro)) {
                    count++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(count);
    }

    private static String getStatisticaPunteggio(String areaGeografica, String parametro) {
        // Implementation for statistics if available
        return "Statistica del punteggio";
    }

    private static String getCommentiOperatori(String areaGeografica, String parametro) {
        StringBuilder commenti = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(1); // Assuming climate parameters are on the second sheet
            for (Row row : sheet) {
                Cell cellArea = row.getCell(1); // Adjust index as per the actual layout
                Cell cellParametro = row.getCell(2); // Adjust index as per the actual layout
                if (cellArea != null && cellParametro != null &&
                    cellArea.getStringCellValue().equals(areaGeografica) &&
                    cellParametro.getStringCellValue().equals(parametro) &&
                    row.getCell(3
