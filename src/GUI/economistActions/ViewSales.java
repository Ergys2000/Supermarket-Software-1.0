package GUI.economistActions;

import GUI.AdminView;
import GUI.EconomistView;
import GUI.adminActions.ViewableCashier;
import databaseAPI.RWUsers;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ViewSales {
    public ViewSales(Pane drawPane){

        Label lb = new Label("Cashiers");
        lb.setFont(new Font("Arial", 22));

        RWUsers rwu = new RWUsers();
        TableView<ViewableCashier> table = new TableView<ViewableCashier>();
        table.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.8));
        table.prefWidthProperty().bind(drawPane.widthProperty());

        TableColumn<ViewableCashier, String> usernameCol = new TableColumn<>("Cashier");
        usernameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.333));
        usernameCol.setStyle("-fx-alignment: CENTER;");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ViewableCashier, String> billsCol = new TableColumn<>("Bills Sold");
        billsCol.prefWidthProperty().bind(table.widthProperty().multiply(0.333));
        billsCol.setStyle("-fx-alignment: CENTER;");
        billsCol.setCellValueFactory(new PropertyValueFactory<>("billNr"));

        TableColumn<ViewableCashier, Long> soldCol = new TableColumn<>("Sold");
        soldCol.setStyle("-fx-alignment: CENTER;");
        soldCol.prefWidthProperty().bind(table.widthProperty().multiply(0.333));
        soldCol.setCellValueFactory(new PropertyValueFactory<>("sold"));

        table.getColumns().add(usernameCol);
        table.getColumns().add(billsCol);
        table.getColumns().add(soldCol);

        double sales = 0;

        for(User i: rwu.getUsers()){
            if(i instanceof Cashier){
                table.getItems().add(new ViewableCashier(((Cashier) i).name, ((Cashier) i).getBills().size(),((Cashier) i).computeSales()));
                sales += ((Cashier) i).computeSales();
            }
        }

        // setting up the bottom panel with the datepickers
        DatePicker start = new DatePicker();
        DatePicker end = new DatePicker();

        Text total = new Text();
        total.setFont(new Font("Arial", 18));

        Label totalLb = new Label("Total : ", total);
        totalLb.setFont(new Font("Arial", 20));
        totalLb.setContentDisplay(ContentDisplay.RIGHT);
        total.setText("" + sales + " ALL");

        Button refresh = new Button("Refresh");

        Label startLb = new Label("Start Date: ", start);
        startLb.setContentDisplay(ContentDisplay.RIGHT);

        Label endLb = new Label("End Date: ", end);
        endLb.setContentDisplay(ContentDisplay.RIGHT);

        // setting function to refresh button
        refresh.setOnAction(e -> {
            double all_sales = 0;
            table.getItems().clear(); // first we clear the table
            LocalDate d1 = start.getValue(); // then we get the date of each fields
            LocalDate d2 = end.getValue();
            MyDate startDate = new MyDate(d1);
            MyDate endDate = new MyDate(d2);

            for(User i: rwu.getUsers()){
                if(i instanceof Cashier){
                    double total_sales = 0;
                    for(Bill j :((Cashier) i).getBills()){
                        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                        MyDate date = new MyDate(dtf.format(j.date)); // we put the date of the bill into a MyDate class
                        if(date.biggerEqualTo(startDate) && date.smallerEqualTo(endDate)){ // check the date
                            total_sales += j.total;
                        }
                    }
                    table.getItems().add(new ViewableCashier(((Cashier) i).name, ((Cashier) i).getBills().size(),total_sales));
                    all_sales += total_sales;
                }
            }
            total.setText("" +  all_sales + " ALL");
        });

        // putting them all into a hbox
        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(startLb, endLb, refresh, totalLb);
        hbox.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.1));

        // creating a vbox to hold everything
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(lb, table, hbox);


        drawPane.getChildren().clear();
        drawPane.getChildren().add(vbox);

    }
}
