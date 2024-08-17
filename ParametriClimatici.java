package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ParametriClimatici {
   public static final String CSV_CENTRI_MONITORAGGIO = "centri-monitoraggio.csv";
   public static final String CSV_PARAMETRI_CLIMATICI = "parametri-climatici.csv";
   public static final String CSV_SEPARATOR = ";";

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
            for(selectedGeonameIndex = 0; selectedGeonameIndex < geonames.size(); ++selectedGeonameIndex) {
               System.out.println(selectedGeonameIndex + 1 + ". " + (String)geonames.get(selectedGeonameIndex));
            }

            selectedGeonameIndex = -1;

            while(true) {
               do {
                  if (selectedGeonameIndex >= 0 && selectedGeonameIndex < geonames.size() + 1) {
                     String selectedGeoname = (String)geonames.get(selectedGeonameIndex - 1);

                     Throwable var5;
                     Object var6;
                     try {
                        var5 = null;
                        var6 = null;

                        try {
                           BufferedReader reader = new BufferedReader(new FileReader("parametri-climatici.csv"));

                           try {
                              boolean parametroPresente = false;

                              while(true) {
                                 String line;
                                 if ((line = reader.readLine()) != null) {
                                    String[] data = line.split(";");
                                    if (data.length < 2 || !data[0].equals(operatore) || !data[1].equals(selectedGeoname)) {
                                       continue;
                                    }

                                    parametroPresente = true;
                                 }

                                 if (!parametroPresente) {
                                    break;
                                 }

                                 System.out.println("Il parametro è già stato inserito.");
                                 return;
                              }
                           } finally {
                              if (reader != null) {
                                 reader.close();
                              }

                           }
                        } catch (Throwable var42) {
                           if (var5 == null) {
                              var5 = var42;
                           } else if (var5 != var42) {
                              var5.addSuppressed(var42);
                           }

                           throw var5;
                        }
                     } catch (IOException var43) {
                        var43.printStackTrace();
                        System.out.println("Errore durante la verifica dei parametri climatici.");
                        return;
                     }

                     try {
                        var5 = null;
                        var6 = null;

                        try {
                           BufferedWriter writer = new BufferedWriter(new FileWriter("parametri-climatici.csv", true));

                           try {
                              writer.write(operatore + ";" + selectedGeoname + ";");
                              String[] parametri = new String[]{"vento", "umidita", "pressione", "temperatura", "precipitazioni", "altitudine-giacciai", "massa-ghiacciai"};
                              System.out.println("Inserisci spiegazione, punteggio e delle note separate da una virgola");
                              String[] var12 = parametri;
                              int var11 = parametri.length;

                              for(int var47 = 0; var47 < var11; ++var47) {
                                 String parametro = var12[var47];
                                 System.out.print(parametro + ": ");
                                 String valore = scanner.nextLine();
                                 writer.write(valore + ";");
                              }

                              writer.newLine();
                              System.out.println("Parametri climatici inseriti con successo.");
                           } finally {
                              if (writer != null) {
                                 writer.close();
                              }

                           }
                        } catch (Throwable var39) {
                           if (var5 == null) {
                              var5 = var39;
                           } else if (var5 != var39) {
                              var5.addSuppressed(var39);
                           }

                           throw var5;
                        }
                     } catch (IOException var40) {
                        var40.printStackTrace();
                        System.out.println("Errore durante l'inserimento dei parametri climatici.");
                     }

                     return;
                  }

                  System.out.print("Seleziona un geoname (1-" + geonames.size() + "): ");
                  selectedGeonameIndex = scanner.nextInt();
                  scanner.nextLine();
               } while(selectedGeonameIndex >= 0 && selectedGeonameIndex < geonames.size() + 1);

               System.out.println("Selezione non valida.");
            }
         }
      }
   }

   private static boolean verificaOperatore(String operatore) {
      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("centri-monitoraggio.csv"));

            String[] data;
            try {
               do {
                  do {
                     String line;
                     if ((line = reader.readLine()) == null) {
                        return false;
                     }

                     data = line.split(";");
                  } while(data.length < 4);
               } while(!data[3].equals(operatore));
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }

            return true;
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
         return false;
      }
   }

   private static List<String> getGeonames(String operatore) {
      ArrayList geonames = new ArrayList();

      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("centri-monitoraggio.csv"));

            String line;
            try {
               while((line = reader.readLine()) != null) {
                  String[] data = line.split(";");
                  if (data.length >= 4 && data[3].equals(operatore)) {
                     String[] geonamesArray = data[2].trim().split(",");
                     geonames.addAll(Arrays.asList(geonamesArray));
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

      return geonames;
   }
}
