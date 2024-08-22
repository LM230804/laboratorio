package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerCM {
    private ClimateMonitoring climateMonitoring = new ClimateMonitoring();
    private static final int THREAD_POOL_SIZE = 10;

    public void start(int port) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    executor.execute(new ClientHandler(clientSocket, climateMonitoring));
                } catch (IOException e) {
                    System.out.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
        ServerCM server = new ServerCM();
        server.start(5432);
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ClimateMonitoring climateMonitoring;

    public ClientHandler(Socket socket, ClimateMonitoring climateMonitoring) {
        this.clientSocket = socket;
        this.climateMonitoring = climateMonitoring;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] parts = inputLine.split(" ");
                String command = parts[0];

                switch (command) {
                    case "registerUser":
                        if (parts.length == 3) {
                            try {
                                climateMonitoring.registerUser(parts[1], parts[2]);
                                writer.println("User registered successfully");
                            } catch (Exception e) {
                                writer.println("Error registering user: " + e.getMessage());
                            }
                        } else {
                            writer.println("Invalid number of arguments for registerUser");
                        }
                        break;

                    case "addClimateData":
                        if (parts.length == 4) {
                            try {
                                double temperature = Double.parseDouble(parts[1]);
                                double humidity = Double.parseDouble(parts[2]);
                                double pressure = Double.parseDouble(parts[3]);
                                climateMonitoring.addClimateData(temperature, humidity, pressure);
                                writer.println("Climate data added successfully");
                            } catch (Exception e) {
                                writer.println("Error adding climate data: " + e.getMessage());
                            }
                        } else {
                            writer.println("Invalid number of arguments for addClimateData");
                        }
                        break;

                    case "findUser":
                        if (parts.length == 2) {
                            try {
                                String userData = climateMonitoring.findUser(parts[1]);
                                writer.println("User found: " + userData);
                            } catch (Exception e) {
                                writer.println("Error finding user: " + e.getMessage());
                            }
                        } else {
                            writer.println("Invalid number of arguments for findUser");
                        }
                        break;

                    default:
                        writer.println("Unknown command");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
