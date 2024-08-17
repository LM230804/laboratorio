package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCM {
   public static void main(String[] args) {
      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            Socket socket = new Socket("localhost", 5432);

            try {
               PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

               try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                  try {
                     writer.println("registerUser johnDoe secretPassword");
                     String response = reader.readLine();
                     System.out.println("Response from server: " + response);
                  } finally {
                     if (reader != null) {
                        reader.close();
                     }

                  }
               } catch (Throwable var28) {
                  if (var1 == null) {
                     var1 = var28;
                  } else if (var1 != var28) {
                     var1.addSuppressed(var28);
                  }

                  if (writer != null) {
                     writer.close();
                  }

                  throw var1;
               }

               if (writer != null) {
                  writer.close();
               }
            } catch (Throwable var29) {
               if (var1 == null) {
                  var1 = var29;
               } else if (var1 != var29) {
                  var1.addSuppressed(var29);
               }

               if (socket != null) {
                  socket.close();
               }

               throw var1;
            }

            if (socket != null) {
               socket.close();
            }
         } catch (Throwable var30) {
            if (var1 == null) {
               var1 = var30;
            } else if (var1 != var30) {
               var1.addSuppressed(var30);
            }

            throw var1;
         }
      } catch (IOException var31) {
         var31.printStackTrace();
      }

   }
}
