package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CentroMonitoraggio {
    public static final String EXCEL_FILE = "geonames-and-coordinates.xlsx";

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
        try (FileInputStream fileInputStream = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("Centri di Monitoraggio registrati per l'operatore " + operatore + ":");

            for (Row row : sheet) {
                Cell operatoreCell = row.getCell(3);
                if (operatoreCell != null && operatoreCell.getStringCellValue().equals(operatore)) {
                    System.out.println("Nome Centro: " + row.getCell(0).getStringCellValue());
                    System.out.println("Indirizzo Fisico: " + row.getCell(1).getStringCellValue());
                    System.out.println("Elenco Aree di Interesse: " + row.getCell(2).getStringCellValue());
                    System.out.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean verificaAreeInteresse(String elencoAreeInteresse) {
        String[] areeInteresse = elencoAreeInteresse.split(",");
        List<String> areeInteresseList = Arrays.asList(areeInteresse);

        try (FileInputStream fileInputStream = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell areaCell = row.getCell(0); // Assuming area of interest name is in the first column
                if (areaCell != null && areeInteresseList.contains(areaCell.getStringCellValue())) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, String elencoAreeInteresse, String operatore) {
        try (FileInputStream fileInputStream = new FileInputStream(new File(EXCEL_FILE));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            newRow.createCell(0).setCellValue(nomeCentro);
            newRow.createCell(1).setCellValue(indirizzoFisico);
            newRow.createCell(2).setCellValue(elencoAreeInteresse);
            newRow.createCell(3).setCellValue(operatore);

            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(EXCEL_FILE))) {
                workbook.write(fileOutputStream);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

