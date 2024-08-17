package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ViewAreaPane extends BorderPane {
   private TableView<application.ViewAreaPane.Area> tableView;
   private ObservableList<application.ViewAreaPane.Area> areaList = FXCollections.observableArrayList();
   private TextArea detailsArea;

   public ViewAreaPane() {
      this.areaList.addAll(new application.ViewAreaPane.Area[]{new application.ViewAreaPane.Area(1, "Area 1", "Descrizione 1"), new application.ViewAreaPane.Area(2, "Area 2", "Descrizione 2")});
      this.tableView = new TableView(this.areaList);
      this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      TableColumn<application.ViewAreaPane.Area, Integer> idColumn = new TableColumn("ID");
      idColumn.setCellValueFactory(new PropertyValueFactory("id"));
      TableColumn<application.ViewAreaPane.Area, String> nameColumn = new TableColumn("Name");
      nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
      this.tableView.getColumns().addAll(new TableColumn[]{idColumn, nameColumn});
      this.detailsArea = new TextArea();
      this.detailsArea.setEditable(false);
      this.detailsArea.setPrefHeight(200.0D);
      this.tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
         if (newSelection != null) {
            this.showAreaDetails(newSelection);
         }

      });
      VBox vbox = new VBox(10.0D, new Node[]{this.tableView, this.detailsArea});
      vbox.setPadding(new Insets(10.0D));
      this.setCenter(vbox);
   }

   private void showAreaDetails(application.ViewAreaPane.Area area) {
      StringBuilder sb = new StringBuilder();
      sb.append("ID: ").append(area.getId()).append("\n");
      sb.append("Name: ").append(area.getName()).append("\n");
      sb.append("Description: ").append(area.getDescription()).append("\n");
      this.detailsArea.setText(sb.toString());
   }
}
