package GUI.economistActions;

import databaseAPI.RWProducts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.MyDate;
import models.Product;
import models.ProductExistsException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProducts {
    public AddProducts(Product product, RWProducts rwp, Pane drawPane){
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());

        // creating the labels and textfields for all the needed information
        TextField nametf = new TextField();
        nametf.getStyleClass().add("textfield");
        Label nameL = new Label("Name : ");

        TextField categorytf = new TextField();
        categorytf.getStyleClass().add("textfield");
        Label categoryL = new Label("Category : ");

        TextField suppliertf = new TextField();
        suppliertf.getStyleClass().add("textfield");
        Label supplierL = new Label("Supplier : ");

        TextField quantitytf = new TextField();
        quantitytf.getStyleClass().add("textfield");
        Label quantityL = new Label("Quantity : ");

        TextField purchasetf = new TextField();
        purchasetf.getStyleClass().add("textfield");
        Label purchaseL = new Label("Purchase price : ");

        TextField selltf = new TextField();
        selltf.getStyleClass().add("textfield");
        Label sellL = new Label("Sell Price : ");

        TextField expiryDatetf = new TextField();
        expiryDatetf.getStyleClass().add("textfield");
        Label expiryDateL = new Label("Expiry Date: ");

        // creating the buttons
        Button addProduct = new Button("Add Product");
        Button clear = new Button("Clear");

        // creating the panes to put the labels and the textfields
        VBox labelBox = new VBox(46);
        labelBox.getChildren().addAll(nameL, categoryL, supplierL, quantityL, purchaseL,
                sellL, expiryDateL);

        VBox textfieldBox = new VBox(36);
        textfieldBox.getChildren().addAll(nametf, categorytf, suppliertf, quantitytf, purchasetf, selltf,
                                            expiryDatetf);

        // hbox to put the buttons
        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(addProduct, clear);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(15);
        pane.setVgap(15);
        pane.add(labelBox, 0, 0);
        pane.add(textfieldBox, 1, 0);
        pane.add(buttonBox, 1, 1);

        if(product != null){
            nametf.setText(product.name);
            nametf.setDisable(true);

            categorytf.setText(product.category);
            categorytf.setDisable(true);

            suppliertf.setText(product.supplier);
            suppliertf.setDisable(true);

            purchasetf.setText("" + product.purchase_price);
            purchasetf.setDisable(true);

            selltf.setText("" + product.sell_price);

            expiryDatetf.setText("" + product.expiry_date);
            expiryDatetf.setDisable(true);
        }

        // adding functions to buttons
        addProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(product == null){
                        // we will use this to format the date of today
                        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");

                        String name = nametf.getText();
                        String category = categorytf.getText();
                        String supplier = suppliertf.getText();
                        double quantity = Double.parseDouble(quantitytf.getText());
                        double purchasePrice = Double.parseDouble(purchasetf.getText());
                        double sellPrice = Double.parseDouble(selltf.getText());
                        MyDate purchaseDate = new MyDate(dtf.format(new Date())); // here we format the date today
                        MyDate expiryDate = new MyDate(expiryDatetf.getText());
                        Product product = new Product(name, category, supplier, quantity, purchasePrice, sellPrice, purchaseDate, expiryDate);
                        try {
                            rwp.append(product);
                            rwp.update();
                            Alert al = new Alert(Alert.AlertType.INFORMATION, "Product added!",
                                    ButtonType.OK);
                            al.show();
                            new ViewProducts(drawPane);

                        } catch(ProductExistsException ex){
                            Alert al = new Alert(Alert.AlertType.ERROR, "Product name exists, please change the name!",
                                    ButtonType.CLOSE);
                            al.show();
                        }

                    } else {
                        double quantity = Double.parseDouble(quantitytf.getText());
                        product.setQuantity(product.quantity + quantity);
                        product.setSell_price(Double.parseDouble(selltf.getText()));
                        product.bought_quantity += quantity;
                        rwp.update();
                        new ViewProducts(drawPane);
                    }
                } catch (Exception e){
                    Alert al = new Alert(Alert.AlertType.ERROR, "Fill the fields correctly: ",
                                        ButtonType.OK);
                    al.show();
                }
            }
        });

        clear.setOnAction(e -> {
            if(product == null){
                nametf.clear();
                categorytf.clear();
                suppliertf.clear();
                quantitytf.clear();
                purchasetf.clear();
                selltf.clear();
                expiryDatetf.clear();
            }else {
                quantitytf.clear();
            }
        });


        Scene scene = new Scene(pane, 500, 700);
        scene.getStylesheets().add("resources" + File.separator + "AddUser.css");
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
}
