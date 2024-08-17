package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCM {
   private ClimateMonitoring climateMonitoring = new ClimateMonitoring();

   public void start(int port) {
      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            ServerSocket serverSocket = new ServerSocket(port);

            try {
               System.out.println("Server started on port " + port);

               while(true) {
                  try {
                     while(true) {
                        Throwable var5 = null;
                        Object var6 = null;

                        try {
                           Socket clientSocket = serverSocket.accept();

                           try {
                              BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                              try {
                                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                                 try {
                                    label875:
                                    while(true) {
                                       while(true) {
                                          String inputLine;
                                          if ((inputLine = reader.readLine()) == null) {
                                             break label875;
                                          }

                                          String[] parts = inputLine.split(" ");
                                          String command = parts[0];
                                          switch(command.hashCode()) {
                                          case -1707703026:
                                             if (command.equals("registerUser")) {
                                                if (parts.length == 3) {
                                                   try {
                                                      this.climateMonitoring.registerUser(parts[1], parts[2]);
                                                      writer.println("User registered successfully");
                                                   } catch (Exception var69) {
                                                      writer.println("Error registering user: " + var69.getMessage());
                                                   }
                                                } else {
                                                   writer.println("Invalid number of arguments for registerUser");
                                                }
                                                break;
                                             }
                                          default:
                                             writer.println("Unknown command");
                                          }
                                       }
                                    }
                                 } finally {
                                    if (writer != null) {
                                       writer.close();
                                    }

                                 }
                              } catch (Throwable var71) {
                                 if (var5 == null) {
                                    var5 = var71;
                                 } else if (var5 != var71) {
                                    var5.addSuppressed(var71);
                                 }

                                 if (reader != null) {
                                    reader.close();
                                 }

                                 throw var5;
                              }

                              if (reader != null) {
                                 reader.close();
                              }
                           } catch (Throwable var72) {
                              if (var5 == null) {
                                 var5 = var72;
                              } else if (var5 != var72) {
                                 var5.addSuppressed(var72);
                              }

                              if (clientSocket != null) {
                                 clientSocket.close();
                              }

                              throw var5;
                           }

                           if (clientSocket != null) {
                              clientSocket.close();
                           }
                        } catch (Throwable var73) {
                           if (var5 == null) {
                              var5 = var73;
                           } else if (var5 != var73) {
                              var5.addSuppressed(var73);
                           }

                           throw var5;
                        }
                     }
                  } catch (IOException var74) {
                     System.out.println("Error handling client connection: " + var74.getMessage());
                  }
               }
            } finally {
               if (serverSocket != null) {
                  serverSocket.close();
               }

            }
         } catch (Throwable var76) {
            if (var2 == null) {
               var2 = var76;
            } else if (var2 != var76) {
               var2.addSuppressed(var76);
            }

            throw var2;
         }
      } catch (IOException var77) {
         System.out.println("Error starting server: " + var77.getMessage());
      }
   }

   public static void main(String[] args) {
      ServerCM server = new ServerCM();
      server.start(5432);
   }
}
