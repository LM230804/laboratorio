package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CentroMonitoraggio {
   public static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";
   public static final String CSV_AREE_INTERESSE = "geonames-and-coordinates.csv";
   public static final String CSV_SEPARATOR = ";";

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
      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("centri-monitoraggio.csv"));

            try {
               System.out.println("Centri di Monitoraggio registrati per l'operatore " + operatore + ":");

               String line;
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 4 && data[3].equals(operatore)) {
                     System.out.println("Nome Centro: " + data[0]);
                     System.out.println("Indirizzo Fisico: " + data[1]);
                     System.out.println("Elenco Aree di Interesse: " + data[2]);
                     System.out.println();
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
         } catch (Throwable var13) {
            if (var1 == null) {
               var1 = var13;
            } else if (var1 != var13) {
               var1.addSuppressed(var13);
            }

            throw var1;
         }
      } catch (IOException var14) {
         var14.printStackTrace();
      }

   }

   private static boolean verificaAreeInteresse(String elencoAreeInteresse) {
      String[] areeInteresse = elencoAreeInteresse.split(",");
      List areeInteresseList = Arrays.asList(areeInteresse);

      try {
         Throwable var3 = null;
         Object var4 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("geonames-and-coordinates.csv"));

            String[] data;
            try {
               do {
                  do {
                     String line;
                     if ((line = reader.readLine()) == null) {
                        return false;
                     }

                     data = line.split(";");
                  } while(data.length < 1);
               } while(!areeInteresseList.contains(data[0]));
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }

            return true;
         } catch (Throwable var15) {
            if (var3 == null) {
               var3 = var15;
            } else if (var3 != var15) {
               var3.addSuppressed(var15);
            }

            throw var3;
         }
      } catch (IOException var16) {
         var16.printStackTrace();
         return false;
      }
   }

   private static boolean salvaCentroMonitoraggio(String nomeCentro, String indirizzoFisico, String elencoAreeInteresse, String operatore) {
      try {
         Throwable var4 = null;
         Object var5 = null;

         try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("centri-monitoraggio.csv", true));

            try {
               writer.write(nomeCentro + ";" + indirizzoFisico + ";" + elencoAreeInteresse + ";" + operatore);
               writer.newLine();
            } finally {
               if (writer != null) {
                  writer.close();
               }

            }

            return true;
         } catch (Throwable var14) {
            if (var4 == null) {
               var4 = var14;
            } else if (var4 != var14) {
               var4.addSuppressed(var14);
            }

            throw var4;
         }
      } catch (IOException var15) {
         var15.printStackTrace();
         return false;
      }
   }
}
