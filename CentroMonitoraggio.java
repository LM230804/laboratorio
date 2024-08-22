package application;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CentroMonitoraggio {
    public static final String FILE_CENTRI_MONITORAGGIO = "centri-monitoraggio.xlsx";
    public static final String FILE_AREE_INTERESSE = "geonames-and-coordinates.xlsx";

    public static void registraCentroAree(String operatore) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome Centro di Monitoraggio: ");
        String nomeCentro = scanner.nextLine();
        System.out.print("Indirizzo Fisico (Via, CAP, localita'): ");
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
        try (FileInputStream fis = new FileInputStream(FILE_CENTRI_MONITORAGGIO);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("Centri di Monitoraggio registrati per l'operatore " + operatore + ":");

            for (Row row : sheet) {
                if (row.getCell(3).getStringCellValue().equals(operatore)) {
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

        try (FileInputStream fis = new FileInputStream(FILE_AREE_INTERESSE);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (areeInteresseList.contains(row.getCell(0).getStringCellValue())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, String elencoAreeInteresse, String operatore) {
        try (FileInputStream fis = new FileInputStream(FILE_CENTRI_MONITORAGGIO);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            row.createCell(0).setCellValue(nomeCentro);
            row.createCell(1).setCellValue(indirizzoFisico);
            row.createCell(2).setCellValue(elencoAreeInteresse);
            row.createCell(3).setCellValue(operatore);

            try (FileOutputStream fos = new FileOutputStream(FILE_CENTRI_MONITORAGGIO)) {
                workbook.write(fos);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
