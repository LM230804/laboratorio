package application;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ParametriClimatici {
    public static final String XLSX_FILE_PATH = "centri-monitoraggio.xlsx";

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

                int selectedGeonameIndex;
                for (selectedGeonameIndex = 0; selectedGeonameIndex < geonames.size(); ++selectedGeonameIndex) {
                    System.out.println(selectedGeonameIndex + 1 + ". " + geonames.get(selectedGeonameIndex));
                }

                selectedGeonameIndex = -1;

                while (true) {
                    do {
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
                    } while (true);
                }
            }
        }
    }

    private static boolean verificaOperatore(String operatore) {
        try (FileInputStream fileInputStream = new FileInputStream(XLSX_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell operatoreCell = row.getCell(3); // Assuming 'operatore' is in the 4th column (index 3)
                if (operatoreCell != null && operatoreCell.getStringCellValue().equals(operatore)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static List<String> getGeonames(String operatore) {
        List<String> geonames = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(XLSX_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell operatoreCell = row.getCell(3); // Assuming 'operatore' is in the 4th column (index 3)
                if (operatoreCell != null && operatoreCell.getStringCellValue().equals(operatore)) {
                    Cell geonamesCell = row.getCell(2); // Assuming 'geonames' are in the 3rd column (index 2)
                    if (geonamesCell != null) {
                        String[] geonamesArray = geonamesCell.getStringCellValue().trim().split(",");
                        geonames.addAll(Arrays.asList(geonamesArray));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return geonames;
    }

    private static boolean isParametroPresente(String operatore, String geoname) {
        try (FileInputStream fileInputStream = new FileInputStream(XLSX_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet("parametri-climatici");

            for (Row row : sheet) {
                Cell operatoreCell = row.getCell(0);
                Cell geonameCell = row.getCell(1);
                if (operatoreCell != null && geonameCell != null &&
                        operatoreCell.getStringCellValue().equals(operatore) &&
                        geonameCell.getStringCellValue().equals(geoname)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void inserisciNuoviParametri(Scanner scanner, String operatore, String geoname) {
        String[] parametri = {"vento", "umidita", "pressione", "temperatura", "precipitazioni", "altitudine-giacciai", "massa-ghiacciai"};

        try (FileInputStream fileInputStream = new FileInputStream(XLSX_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(XLSX_FILE_PATH)) {

            Sheet sheet = workbook.getSheet("parametri-climatici");
            if (sheet == null) {
                sheet = workbook.createSheet("parametri-climatici");
            }

            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);

            row.createCell(0).setCellValue(operatore);
            row.createCell(1).setCellValue(geoname);

            for (int i = 0; i < parametri.length; i++) {
                System.out.print(parametri[i] + ": ");
                String valore = scanner.nextLine();
                row.createCell(i + 2).setCellValue(valore);
            }

            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

