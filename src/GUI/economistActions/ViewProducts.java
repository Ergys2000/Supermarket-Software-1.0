package GUI.economistActions;

import databaseAPI.RWProducts;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.*;

import java.util.ArrayList;

public class ViewProducts {
    public ViewProducts(Pane drawPane){
        GridPane pane = new GridPane();

        RWProducts rwp = new RWProducts();
        ArrayList<Product> products = rwp.getProducts();
        TableView<Product> table = new TableView<>();
        table.prefWidthProperty().bind(drawPane.widthProperty());
        table.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.68));

        TableColumn<Product, Number> nameCol = new TableColumn<>("Name");
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("name"));

        TableColumn<Product, String> quantityCol = new TableColumn<>("Units");
        quantityCol.setStyle("-fx-alignment: CENTER;");
        quantityCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));

        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setStyle("-fx-alignment: CENTER;");
        categoryCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));

        TableColumn<Product, String> expiryDateCol = new TableColumn<>("Expiry Date");
        expiryDateCol.setStyle("-fx-alignment: CENTER;");
        expiryDateCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        expiryDateCol.setCellValueFactory(new PropertyValueFactory<Product, String>("expiry_date"));

        TableColumn<Product, Number> priceCol = new TableColumn<>("Price per unit");
        priceCol.setStyle("-fx-alignment: CENTER;");
        priceCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("sell_price"));

        table.getColumns().addAll(nameCol, quantityCol, categoryCol, expiryDateCol, priceCol);
        for(Product i: products)
            table.getItems().add(i);
        pane.getChildren().add(table);


        // Creating the bottom pane
        HBox bottom = new HBox(10);
        bottom.setAlignment(Pos.CENTER);
        bottom.prefWidthProperty().bind(drawPane.widthProperty());
        bottom.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.1));
        Button add = new Button("Add/Edit");
        Button remove = new Button("Remove");
        bottom.getChildren().addAll(add, remove);
        // getting the selection model to select individual products
        TableView.TableViewSelectionModel<Product> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        // creating the search bar
        TextField searchTf = new TextField();
        searchTf.setPromptText("Search");

        HBox searchBar = new HBox(5);
        searchBar.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.02));
        searchBar.prefWidthProperty().bind(drawPane.widthProperty());
        searchBar.setAlignment(Pos.TOP_RIGHT);
        searchBar.getChildren().addAll(searchTf);

        MenuBar mb = new MenuBar();

        Menu category = new Menu("Category");
        for(String s : rwp.getUniqueCategories())
            category.getItems().add(new MenuItem(s));


        mb.getMenus().addAll(category);


        searchTf.setOnKeyReleased(e -> {
            table.getItems().clear();
            String string = searchTf.getText().toLowerCase();
            for(Product i : rwp.getProducts()){
                if(i.name.toLowerCase().contains(string) || i.supplier.toLowerCase().contains(string) ||
                    i.category.toLowerCase().contains(string)){
                    table.getItems().add(i);
                }
            }
        });

        for(final MenuItem item : category.getItems()){
            item.setOnAction(e -> {
                table.getItems().clear();
                String cat = item.getText();
                for(Product p : rwp.getProducts())
                    if(p.category.equals(cat))
                        table.getItems().add(p);
            });
        }


        remove.setOnAction(e -> {
            Product prod = selectionModel.getSelectedItem();
            if(prod != null){
                rwp.getProducts().remove(prod);
                rwp.update();
                new ViewProducts(drawPane);
                Alert al = new Alert(Alert.AlertType.ERROR, "Product deleted!");
                al.show();
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR, "Please select a product!");
                al.show();
            }
        });

        add.setOnAction(e -> {
            Product prod = selectionModel.getSelectedItem();
            new AddProducts(prod, rwp, drawPane);
        });

        VBox vbox = new VBox(15);
        vbox.prefWidthProperty().bind(drawPane.widthProperty());
        vbox.getChildren().addAll(mb, searchBar, table, bottom);

        drawPane.getChildren().clear();
        drawPane.getChildren().addAll(vbox);
    }
}
