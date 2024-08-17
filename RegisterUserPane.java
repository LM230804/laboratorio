package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterUserPane extends GridPane {
   public RegisterUserPane(ClimateMonitoring climateMonitoring) {
      TextField usernameField = new TextField();
      TextField passwordField = new TextField();
      Button registerButton = new Button("Register");
      registerButton.setOnAction((e) -> {
         String username = usernameField.getText();
         String password = passwordField.getText();
         climateMonitoring.registerUser(username, password);
      });
      this.add(usernameField, 0, 0);
      this.add(passwordField, 0, 1);
      this.add(registerButton, 0, 2);
   }
}
