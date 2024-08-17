package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ClimateMonitoringApp extends Application {
   private ClimateMonitoring climateMonitoring = new ClimateMonitoring();

   public void start(Stage primaryStage) {
      TabPane tabPane = new TabPane();
      Tab registerUserTab = new Tab("Register User", new RegisterUserPane(this.climateMonitoring));
      Tab createCenterTab = new Tab("Create Center", new CreateCenterPane(this.climateMonitoring));
      Tab insertParametersTab = new Tab("Insert Parameters", new InsertParametersPane(this.climateMonitoring));
      Tab viewAreaTab = new Tab("View Area", new ViewAreaPane());
      tabPane.getTabs().addAll(new Tab[]{registerUserTab, createCenterTab, insertParametersTab, viewAreaTab});
      Scene scene = new Scene(tabPane, 800.0D, 600.0D);
      primaryStage.setTitle("Climate Monitoring System");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
