package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Ricerca {
   public static final String CSV_GEO_NAMES = "geonames-and-coordinates.csv";
   public static final String CSV_PARAMETRI_CLIMATICI = "parametri-climatici.csv";
   public static final String CSV_SEPARATOR = ";";

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
            String[] risultato = (String[])risultati.get(0);
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
            String[] risultato = (String[])risultati.get(0);
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
      ArrayList risultati = new ArrayList();

      try {
         Throwable var3 = null;
         Object var4 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("geonames-and-coordinates.csv"));

            String line;
            try {
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 6 && data[2].equalsIgnoreCase(denominazione) && data[4].equalsIgnoreCase(stato)) {
                     String[] risultato = new String[]{data[1], data[4], data[5]};
                     risultati.add(risultato);
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
         } catch (Throwable var16) {
            if (var3 == null) {
               var3 = var16;
            } else if (var3 != var16) {
               var3.addSuppressed(var16);
            }

            throw var3;
         }
      } catch (IOException var17) {
         var17.printStackTrace();
      }

      return risultati;
   }

   private static List<String[]> ricercaPerCoordinate(String coordinate) {
      ArrayList risultati = new ArrayList();

      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("geonames-and-coordinates.csv"));

            String line;
            try {
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 6 && data[5].equalsIgnoreCase(coordinate)) {
                     String[] risultato = new String[]{data[1], data[4], data[5]};
                     risultati.add(risultato);
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
         } catch (Throwable var15) {
            if (var2 == null) {
               var2 = var15;
            } else if (var2 != var15) {
               var2.addSuppressed(var15);
            }

            throw var2;
         }
      } catch (IOException var16) {
         var16.printStackTrace();
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
         Iterator var3 = parametriClimatici.iterator();

         while(var3.hasNext()) {
            String[] parametro = (String[])var3.next();
            System.out.println("Parametro: " + parametro[0]);
            System.out.println("Numero di rilevazioni: " + parametro[1]);
            System.out.println("Statistica del punteggio: " + parametro[2]);
            System.out.println("Commenti degli operatori: " + parametro[3]);
            System.out.println();
         }
      }

   }

   private static List<String[]> getParametriClimatici(String areaGeografica) {
      ArrayList risultati = new ArrayList();

      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("parametri-climatici.csv"));

            String line;
            try {
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 2 && data[1].equals(areaGeografica)) {
                     String[] risultato = new String[]{data[2], getNumeroRilevazioni(areaGeografica, data[2]), getStatisticaPunteggio(areaGeografica, data[2]), getCommentiOperatori(areaGeografica, data[2])};
                     risultati.add(risultato);
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
         } catch (Throwable var15) {
            if (var2 == null) {
               var2 = var15;
            } else if (var2 != var15) {
               var2.addSuppressed(var15);
            }

            throw var2;
         }
      } catch (IOException var16) {
         var16.printStackTrace();
      }

      return risultati;
   }

   private static String getNumeroRilevazioni(String areaGeografica, String parametro) {
      int count = 0;

      try {
         Throwable var3 = null;
         Object var4 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("parametri-climatici.csv"));

            String line;
            try {
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 2 && data[1].equals(areaGeografica) && data[2].equals(parametro)) {
                     ++count;
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
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
      }

      return String.valueOf(count);
   }

   private static String getStatisticaPunteggio(String areaGeografica, String parametro) {
      return "Statistica del punteggio";
   }

   private static String getCommentiOperatori(String areaGeografica, String parametro) {
      StringBuilder commenti = new StringBuilder();

      try {
         Throwable var3 = null;
         Object var4 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("parametri-climatici.csv"));

            String line;
            try {
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 2 && data[1].equals(areaGeografica) && data[2].equals(parametro) && data.length >= 4) {
                     if (commenti.length() > 0) {
                        commenti.append(", ");
                     }

                     commenti.append(data[3]);
                  }
               }
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }
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
      }

      return commenti.toString();
   }
}
