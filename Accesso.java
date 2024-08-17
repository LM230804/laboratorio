package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Accesso {
   private static final String CSV_FILE = "operatori-registrati.csv";
   private static final String CSV_SEPARATOR = ";";

   public static void eseguiLogin() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("UserID: ");
      String userID = scanner.nextLine();
      System.out.print("Password: ");
      String password = scanner.nextLine();
      boolean loginRiuscito = verificaCredenziali(userID, password);
      if (loginRiuscito) {
         System.out.println("Login effettuato con successo.");
         boolean continua = true;

         while(continua) {
            System.out.println("\nCosa vuoi fare dopo il login?");
            System.out.println("1. Registra Centro Aree");
            System.out.println("2. Visualizza Centri di Monitoraggio");
            System.out.println("3. Inserisci parametri climatici");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();
            switch(scelta) {
            case 0:
               continua = false;
               break;
            case 1:
               CentroMonitoraggio.registraCentroAree(userID);
               break;
            case 2:
               CentroMonitoraggio.visualizzaCentriMonitoraggio(userID);
               break;
            case 3:
               ParametriClimatici.inserisciParametriClimatici(userID);
               break;
            default:
               System.out.println("Scelta non valida.");
            }
         }
      } else {
         System.out.println("Credenziali non valide.");
      }

   }

   public static void eseguiRegistrazione() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Nome e Cognome: ");
      String nomeCognome = scanner.nextLine();
      System.out.print("Codice Fiscale: ");
      String codFisc = scanner.nextLine();
      System.out.print("Email: ");
      String email = scanner.nextLine();
      System.out.print("UserID: ");
      String userID = scanner.nextLine();
      System.out.print("Password: ");
      String password = scanner.nextLine();
      boolean registrazioneRiuscita = salvaCredenziali(nomeCognome, codFisc, email, userID, password);
      if (registrazioneRiuscita) {
         System.out.println("Registrazione effettuata con successo.");
      } else {
         System.out.println("Errore durante la registrazione.");
      }

   }

   private static boolean verificaCredenziali(String userID, String password) {
      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            BufferedReader reader = new BufferedReader(new FileReader("operatori-registrati.csv"));

            String[] data;
            try {
               do {
                  do {
                     do {
                        String line;
                        if ((line = reader.readLine()) == null) {
                           return false;
                        }

                        data = line.split(";");
                     } while(data.length < 5);
                  } while(!data[3].equals(userID));
               } while(!data[4].equals(password));
            } finally {
               if (reader != null) {
                  reader.close();
               }

            }

            return true;
         } catch (Throwable var14) {
            if (var2 == null) {
               var2 = var14;
            } else if (var2 != var14) {
               var2.addSuppressed(var14);
            }

            throw var2;
         }
      } catch (IOException var15) {
         var15.printStackTrace();
         return false;
      }
   }

   private static boolean salvaCredenziali(String nomeCognome, String codFisc, String email, String userID, String password) {
      try {
         Throwable var5 = null;
         Object var6 = null;

         try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("operatori-registrati.csv", true));

            try {
               writer.write(nomeCognome + ";" + codFisc + ";" + email + ";" + userID + ";" + password);
               writer.newLine();
            } finally {
               if (writer != null) {
                  writer.close();
               }

            }

            return true;
         } catch (Throwable var15) {
            if (var5 == null) {
               var5 = var15;
            } else if (var5 != var15) {
               var5.addSuppressed(var15);
            }

            throw var5;
         }
      } catch (IOException var16) {
         var16.printStackTrace();
         return false;
      }
   }
}
