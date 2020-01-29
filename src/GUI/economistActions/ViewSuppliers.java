package GUI.economistActions;

import databaseAPI.RWProducts;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Product;
import models.Supplier;
import models.User;

import java.io.File;
import java.util.ArrayList;

public class ViewSuppliers {
    public ViewSuppliers(Pane drawPane, RWProducts rwp){
        TableView<Supplier> table = new TableView<>();
        table.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.75));
        table.prefWidthProperty().bind(drawPane.widthProperty().multiply(0.98));

        TableColumn<Supplier, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty());

        table.getColumns().add(nameCol);

        ArrayList<Supplier> suppliers = rwp.getUniqueSuppliers();
        for(Product p: rwp.getProducts()){
            for(Supplier s: suppliers)
                if(p.supplier.equals(s.name))
                    s.addProduct(p);
        }
        for(Supplier s: suppliers)
            table.getItems().add(s);

        Label lb = new Label("Suppliers");
        lb.setFont(new Font("Arial", 22));
        Button viewProducts = new Button("View Products");

        TableView.TableViewSelectionModel<Supplier> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        viewProducts.setOnAction(e -> {
            Supplier supplier = selectionModel.getSelectedItem();
            if(supplier != null)
                viewProducts(supplier);
            else{
                Alert al = new Alert(Alert.AlertType.ERROR, "Please select a supplier!", ButtonType.OK);
                al.show();
            }
        });

        VBox vb = new VBox(15);
        vb.setAlignment(Pos.CENTER);
        vb.prefHeightProperty().bind(drawPane.heightProperty());
        vb.prefWidthProperty().bind(drawPane.widthProperty());
        vb.getChildren().addAll(lb, table, viewProducts);

        drawPane.getChildren().clear();
        drawPane.getChildren().add(vb);
    }
    public void viewProducts(Supplier supplier){
        Stage stage = new Stage();

        ArrayList<Product> products = supplier.getProducts();
        TableView<Product> table = new TableView<>();
        table.prefWidthProperty().bind(stage.widthProperty());
        table.prefHeightProperty().bind(stage.heightProperty().multiply(0.88));

        TableColumn<Product, Number> nameCol = new TableColumn<>("Name");
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("name"));


        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setStyle("-fx-alignment: CENTER;");
        categoryCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));

        TableColumn<Product, String> expiryDateCol = new TableColumn<>("Expiry Date");
        expiryDateCol.setStyle("-fx-alignment: CENTER;");
        expiryDateCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        expiryDateCol.setCellValueFactory(new PropertyValueFactory<Product, String>("expiry_date"));

        TableColumn<Product, Number> priceCol = new TableColumn<>("Price per unit");
        priceCol.setStyle("-fx-alignment: CENTER;");
        priceCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("sell_price"));

        table.getColumns().addAll(nameCol, categoryCol, expiryDateCol, priceCol);

        for(Product p: products)
            table.getItems().add(p);

        table.getStylesheets().add("resources" + File.separator + "style.css");
        Scene scene = new Scene(table, 500, 500);
        stage.setScene(scene);
        stage.setTitle(supplier.name + " Products");
        stage.show();
    }
}
