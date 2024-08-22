import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCM {
  
  public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{      
    
    try (Socket socket = new Socket("localhost", 5432);
           PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
           BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

         writer.println("registerUser johnDoe secretPassword");
         String response = reader.readLine();
         System.out.println("Response from server: " + response);

      } catch (IOException e) {
         e.printStackTrace();
      }
  }
}

