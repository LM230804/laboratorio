package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InsertParametersPane extends GridPane {
   public InsertParametersPane(ClimateMonitoring climateMonitoring) {
      TextField centerIdField = new TextField();
      TextField parameterField = new TextField();
      TextField valueField = new TextField();
      Button insertButton = new Button("Insert");
      insertButton.setOnAction((e) -> {
         int centerId = Integer.parseInt(centerIdField.getText());
         String parameter = parameterField.getText();
         String value = valueField.getText();
         climateMonitoring.insertParameters(centerId, parameter, value);
      });
      this.add(centerIdField, 0, 0);
      this.add(parameterField, 0, 1);
      this.add(valueField, 0, 2);
      this.add(insertButton, 0, 3);
   }
}
