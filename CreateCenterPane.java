package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CreateCenterPane extends GridPane {
   public CreateCenterPane(ClimateMonitoring climateMonitoring) {
      TextField centerNameField = new TextField();
      TextField locationField = new TextField();
      Button createButton = new Button("Create");
      createButton.setOnAction((e) -> {
         String centerName = centerNameField.getText();
         String location = locationField.getText();
         climateMonitoring.createCenter(centerName, location);
      });
      this.add(centerNameField, 0, 0);
      this.add(locationField, 0, 1);
      this.add(createButton, 0, 2);
   }
}
