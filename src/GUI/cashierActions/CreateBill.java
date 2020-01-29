package GUI.cashierActions;

import GUI.CashierView;
import databaseAPI.RWBills;
import databaseAPI.RWProducts;
import databaseAPI.RWUsers;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Bill;
import models.Cashier;
import models.Product;
import models.User;


public class CreateBill {
    public CreateBill(Pane drawPane, Cashier user, RWUsers rwu){
        RWBills rwb = new RWBills();
        RWProducts rwp = new RWProducts();

        // creating the table
        TableView<Product> table = new TableView<Product>();
        table.prefWidthProperty().bind(drawPane.widthProperty());
        table.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.8));
        Label label = new Label("Bill");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        // creating the columns for the table
        TableColumn<Product, String> nameCol = new TableColumn<Product, String>("Product name");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> quantityCol = new TableColumn<Product, Double>("Quantity");
        quantityCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceCol = new TableColumn<Product, Double>("Price");
        priceCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("sell_price"));

        table.getColumns().add(nameCol);
        table.getColumns().add(quantityCol);
        table.getColumns().add(priceCol);


        // here we will put the table
        ScrollPane sp = new ScrollPane();
        sp.setContent(table);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, sp);
        vbox.setAlignment(Pos.CENTER);

        // creating the bottom menu panel
        Button done = new Button("Done");
        Button add = new Button("Add");
        Button remove = new Button("Remove");
        Button clear = new Button("Clear");
        ComboBox<String> comboBox = new ComboBox<>();

        for(Product i : rwp.getProducts())
            comboBox.getItems().add(i.name);

        TextField quantity = new TextField("");
        quantity.setPromptText("Quantity");

        HBox hbox = new HBox(15);
        hbox.prefWidthProperty().bind(drawPane.widthProperty());
        hbox.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.2));

        // creating the total display
        Text total = new Text();
        total.setFont(new Font("Arial", 18));

        Label totalLb = new Label("Total : ", total);
        totalLb.setFont(new Font("Arial", 20));
        totalLb.setContentDisplay(ContentDisplay.RIGHT);

        hbox.getChildren().addAll(comboBox, quantity, add, remove, clear, done);
        hbox.getChildren().add(totalLb);


        TableView.TableViewSelectionModel<Product> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        // adding functions to  buttons
        remove.setOnAction(e -> {
            Product selected = selectionModel.getSelectedItem();
            if(selected == null){
                if(table.getItems().size()>0){
                    int index = table.getItems().size();
                    table.getItems().remove(index - 1);
                }else {
                    Alert al1 = new Alert(Alert.AlertType.ERROR, "Nothing to delete!", ButtonType.OK);
                    al1.show();
                }
            } else {
                table.getItems().remove(selected);
            }
        });

        done.setOnAction(e ->{
            Bill bill = new Bill();
            for(Product i: table.getItems()){
                bill.append(i);
            }
            for(Product i: bill.getProducts()){
                if(rwp.check(i)){
                    rwp.remove(i);
                }else {
                    Alert al1 = new Alert(Alert.AlertType.ERROR, "We dont have enough "
                            + i.getName(), ButtonType.OK);
                    al1.show();
                    return ;
                }
            }
            bill.toFile(user.username, user.getBills().size()+1);
            user.addBill(bill);
            rwb.add(bill);

            rwb.update();
            rwp.update();
            rwu.update();
            table.getItems().clear();
        });


        add.setOnAction(e -> {
            Product product = rwp.getProductByName(comboBox.getValue());
            if(product != null){
                double buy_quantity = Double.parseDouble(quantity.getText());

                if(buy_quantity <= product.quantity && buy_quantity > 0){

                    Product newProduct = new Product(product.name, product.category, product.supplier, buy_quantity,
                            product.purchase_price, product.sell_price, product.purchase_date, product.expiry_date);
                    table.getItems().add(newProduct);

                    double tot = 0;
                    for(Product i : table.getItems()){
                        tot += i.sell_price * i.quantity;
                    }
                    total.setText("" + tot + " ALL");
                } else {
                    Alert al1 = new Alert(Alert.AlertType.ERROR, "Either too much or negative quantity!", ButtonType.OK);
                    al1.show();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR, "Product not found!", ButtonType.OK);
                al.show();
            }
        });

        clear.setOnAction(e -> {
            table.getItems().clear();
        });



        GridPane grid = new GridPane();
        grid.setMinSize(800, 500);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.add(vbox, 0, 0);
        grid.add(hbox, 0, 1);

        drawPane.getChildren().clear();
        drawPane.getChildren().add(grid);
    }
}
