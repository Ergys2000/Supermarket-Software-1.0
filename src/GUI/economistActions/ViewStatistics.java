package GUI.economistActions;

import GUI.EconomistView;
import databaseAPI.RWBills;
import databaseAPI.RWProducts;
import databaseAPI.RWUsers;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ViewStatistics {
    public ViewStatistics(Pane drawPane){
        RWBills rwb = new RWBills();
        // Creating the menus
        Menu dispProducts = new Menu("Display");
        Menu dispProfit = new Menu("Revenue");

        // Creating the menu items
        MenuItem productsSold = new MenuItem("View Products Sold");
        MenuItem productsBought = new MenuItem("View Products Bought");
        MenuItem pieChart = new MenuItem("Display Pie Chart");
        MenuItem profit = new MenuItem("View revenue");

        // Putting the items in the menus
        dispProfit.getItems().add(profit);
        dispProducts.getItems().addAll(productsSold, productsBought, pieChart);

        // Puttin the menus in the menubar
        MenuBar mb = new MenuBar();
        mb.getMenus().addAll(dispProducts, dispProfit);

        VBox displayVbox = new VBox(15);
        displayVbox.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.95));
        displayVbox.prefWidthProperty().bind(drawPane.widthProperty());

        // creating main pane and adding the menu bar and the display pane
        VBox mainvbox = new VBox(0);
        mainvbox.prefWidthProperty().bind(drawPane.widthProperty());
        mainvbox.prefHeightProperty().bind(drawPane.heightProperty());
        mainvbox.getChildren().addAll(mb, displayVbox);

        drawPane.getChildren().clear();
        drawPane.getChildren().add(mainvbox);

        // setting functions to menu

        pieChart.setOnAction(e -> {
            displayPieChart(displayVbox, rwb);
        });

        profit.setOnAction(e -> {
            displayProfit(displayVbox, rwb);
        });

        productsBought.setOnAction(e -> {
            displayProductsBought(displayVbox);
        });

        productsSold.setOnAction(e -> {
            displayProductsSold(displayVbox, rwb);
        });
    }

    public void displayPieChart(VBox displayVbox, RWBills rwb){
        PieChart pie = new CreateCharts().createPieChart(rwb);
        pie.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.8));
        pie.prefWidthProperty().bind(displayVbox.widthProperty());

        // Creating the nodes of the bottom pane, where we choose the date
        DatePicker start = new DatePicker();
        DatePicker end = new DatePicker();

        Label startLb = new Label("Start : ", start);
        Label endLb = new Label("End : ", end);
        startLb.setContentDisplay(ContentDisplay.RIGHT);
        endLb.setContentDisplay(ContentDisplay.RIGHT);

        Button refresh = new Button("Refresh");

        Text selectedPercent = new Text();
        Text selectedTotal = new Text();
        Text pie_total = new Text();

        Label selectedPlabel = new Label("Selected percentage: ", selectedPercent);
        Label selectedTlabel = new Label("Selected total : ", selectedTotal);
        Label totalLabel = new Label("Total : ", pie_total);
        selectedPlabel.setContentDisplay(ContentDisplay.RIGHT);
        selectedTlabel.setContentDisplay(ContentDisplay.RIGHT);
        totalLabel.setContentDisplay(ContentDisplay.RIGHT);
        selectedPlabel.setFont(new Font("Arial", 20));
        selectedTlabel.setFont(new Font("Arial", 20));
        totalLabel.setFont(new Font("Arial", 20));

        HBox bottom = new HBox(20);
        bottom.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.2));
        bottom.setAlignment(Pos.CENTER);
        bottom.prefWidthProperty().bind(displayVbox.widthProperty());

        VBox labelBox = new VBox(5);
        labelBox.getChildren().addAll(selectedPlabel, selectedTlabel, totalLabel);

        bottom.getChildren().addAll(startLb, endLb, refresh, labelBox);

        for (final PieChart.Data data : pie.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            double total = 0;
                            for(PieChart.Data d: pie.getData()){
                                total += d.getPieValue();
                            }
                            String name = data.getName();
                            PieChart pie1 = new CreateCharts().createPieChartForCategory(name, rwb);
                            Label lb1 = new Label("");
                            lb1.setFont(new Font("Arial", 15));

                            for(final PieChart.Data data: pie1.getData()){
                                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, event-> {
                                    double total1 = 0;
                                    for(PieChart.Data d: pie1.getData()){
                                        total1 += d.getPieValue();
                                    }
                                    lb1.setText("" + (data.getPieValue()/total1) * 100 + "%");
                                });
                            }

                            Stage st = new Stage();
                            st.setScene(new Scene(new VBox(pie1, lb1), 400, 400));
                            st.setTitle("Pie Chart for " + name + " category");
                            st.show();
                            selectedPercent.setText((data.getPieValue()/total) * 100 + "%");
                            selectedTotal.setText("" + data.getPieValue());
                            pie_total.setText("" + total);
                        }
                    });
        }


        refresh.setOnAction(event -> {
            try{
                double winnings = 0;
                LocalDate d1 = start.getValue(); // then we get the date of each fields
                LocalDate d2 = end.getValue();
                MyDate sd = new MyDate(d1);
                MyDate ed = new MyDate(d2);

                PieChart newPieChart = new CreateCharts().createPieChart(rwb, sd, ed);
                newPieChart.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.9));
                newPieChart.prefWidthProperty().bind(displayVbox.widthProperty());


                for (final PieChart.Data data : newPieChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                            new EventHandler<MouseEvent>() {
                                @Override public void handle(MouseEvent e) {
                                    double total = 0;
                                    for(PieChart.Data d: newPieChart.getData()){
                                        total += d.getPieValue();
                                    }
                                    String name = data.getName();
                                    PieChart pie1 = new CreateCharts().createPieChartForCategory(name, rwb, sd, ed);
                                    Label lb1 = new Label("");
                                    lb1.setFont(new Font("Arial", 15));

                                    for(final PieChart.Data data: pie1.getData()){
                                        data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, event-> {
                                            double total1 = 0;
                                            for(PieChart.Data d: pie1.getData()){
                                                total1 += d.getPieValue();
                                            }
                                            lb1.setText((data.getPieValue()/total1) * 100 + "%");
                                        });
                                    }

                                    Stage st = new Stage();
                                    st.setScene(new Scene(new VBox(pie1, lb1), 400, 400));
                                    st.setTitle("Pie Chart for " + name + " category");
                                    st.show();
                                    selectedPercent.setText(name + (data.getPieValue()/total) * 100 + "%");
                                    selectedTotal.setText("" + data.getPieValue());
                                    pie_total.setText("" + total);
                                }
                            });
                }
                displayVbox.getChildren().clear();
                displayVbox.getChildren().addAll(newPieChart, bottom);

            }catch (Exception ex){ // if something goes wrong
                Alert al = new Alert(Alert.AlertType.INFORMATION, "Please fill both dates!", ButtonType.OK);
                al.show();
            }

        });


        displayVbox.getChildren().clear();
        displayVbox.getChildren().addAll(pie, bottom);

    }

    public void displayProductsSold(VBox displayVbox, RWBills rwb){
        // Creating the table
        TableView<Product> table = new TableView<>();
        table.prefWidthProperty().bind(displayVbox.widthProperty());
        table.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.8));

        // Creating the columns of the table and adding them to the table
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setStyle("-fx-alignment: CENTER;");
        quantityCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setStyle("-fx-alignment: CENTER;");
        priceCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("sell_price"));

        table.getColumns().addAll(nameCol, quantityCol, priceCol);

        // Getting all the bills into one giant bill
        Bill giantBill = rwb.getProductsSold();
        double revenue = 0;
        for(Product i: giantBill.getProducts()){ // for each product
            table.getItems().add(i); // we add it to the table
            revenue += i.quantity * i.sell_price; // we also add the revenue
        }

        // Creating the nodes of the bottom pane, where we choose the date
        DatePicker start = new DatePicker();
        DatePicker end = new DatePicker();

        Text total = new Text();
        total.setFont(new Font("Arial", 18));

        Label totalLb = new Label("Total : ", total);
        totalLb.setFont(new Font("Arial", 20));
        totalLb.setContentDisplay(ContentDisplay.RIGHT);
        total.setText("" + revenue + " ALL");

        Button refresh = new Button("Refresh");

        Label startLb = new Label("Start Date: ", start);
        startLb.setContentDisplay(ContentDisplay.RIGHT);

        Label endLb = new Label("End Date: ", end);
        endLb.setContentDisplay(ContentDisplay.RIGHT);

        // refresh function
        refresh.setOnAction(event -> {
            try{
                double winnings = 0;
                table.getItems().clear(); // first we clear the table
                LocalDate d1 = start.getValue(); // then we get the date of each fields
                LocalDate d2 = end.getValue();
                MyDate sd = new MyDate(d1);
                MyDate ed = new MyDate(d2);

                Bill bill = rwb.getProductsSold(sd, ed); // here we get the bills that are in between those dates
                for(Product i: bill.getProducts()){ // add them to the table
                    table.getItems().add(i);
                    winnings += i.sell_price * i.quantity;
                }
                total.setText("" + winnings + "ALL");
            }catch (Exception ex){ // if something goes wrong
                Alert al = new Alert(Alert.AlertType.INFORMATION, "Please fill both dates!", ButtonType.OK);
                al.show();
            }
        });

        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(startLb, endLb, refresh, totalLb);
        hbox.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.2));

        displayVbox.getChildren().clear(); // clearing the display pane before adding our elements
        displayVbox.setAlignment(Pos.CENTER);
        displayVbox.getChildren().addAll(table, hbox);

    }

    public void displayProductsBought(VBox displayVbox){
        RWProducts rwp = new RWProducts();
        // Creating the table
        TableView<Product> table = new TableView<>();
        table.prefWidthProperty().bind(displayVbox.widthProperty());
        table.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.8));

        // Creating the columns of the table and adding them to the table
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Number> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setStyle("-fx-alignment: CENTER;");
        quantityCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("bought_quantity"));

        TableColumn<Product, Number> priceCol = new TableColumn<>("Price per unit");
        priceCol.setStyle("-fx-alignment: CENTER;");
        priceCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("purchase_price"));

        table.getColumns().addAll(nameCol, quantityCol, priceCol);

        double spendings = 0;
        for(Product i: rwp.getProducts()){
            table.getItems().add(i);
            spendings += i.bought_quantity * i.purchase_price;
        }

        // Creating the nodes of the bottom pane, where we choose the date
        DatePicker start = new DatePicker();
        DatePicker end = new DatePicker();

        Text total = new Text();
        total.setFont(new Font("Arial", 18));

        Label totalLb = new Label("Total : ", total);
        totalLb.setFont(new Font("Arial", 20));
        totalLb.setContentDisplay(ContentDisplay.RIGHT);
        total.setText("" + spendings + "  ALL");

        Button refresh = new Button("Refresh");

        Label startLb = new Label("Start Date: ", start);
        startLb.setContentDisplay(ContentDisplay.RIGHT);

        Label endLb = new Label("End Date: ", end);
        endLb.setContentDisplay(ContentDisplay.RIGHT);

        // refresh function
        refresh.setOnAction(event -> {
            try{
                double spent = 0;
                table.getItems().clear(); // first we clear the table
                LocalDate d1 = start.getValue(); // then we get the date of each fields
                LocalDate d2 = end.getValue();
                MyDate startDate = new MyDate(d1);
                MyDate endDate = new MyDate(d2);
                for(Product i: rwp.getProducts()) { // add them to the table
                    if (i.purchase_date.biggerEqualTo(startDate) && i.purchase_date.smallerEqualTo(endDate)) {
                        table.getItems().add(i);
                        spent += i.bought_quantity * i.purchase_price;
                    }
                }
                total.setText("" + spent + " ALL");
            }catch (Exception ex){ // if something goes wrong
                Alert al = new Alert(Alert.AlertType.INFORMATION, "Please fill both dates!", ButtonType.OK);
                al.show();
            }
        });

        ScrollPane sp = new ScrollPane();
        sp.setContent(table);
        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(startLb, endLb, refresh, totalLb);
        hbox.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.2));

        displayVbox.getChildren().clear(); // clearing the display pane before adding our elements
        displayVbox.setAlignment(Pos.CENTER);
        displayVbox.getChildren().addAll(sp, hbox);

    }

    public void displayProfit(VBox displayVbox, RWBills rwb){
        RWProducts rwp = new RWProducts();
        TableView<Product> table = new TableView<>();
        table.prefWidthProperty().bind(displayVbox.widthProperty());
        table.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.8));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        TableColumn<Product, Number> unitsSoldCol = new TableColumn<>("Units Sold");
        TableColumn<Product, Number> priceUnitCol = new TableColumn<>("Price per unit");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));

        unitsSoldCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitsSoldCol.setStyle("-fx-alignment: CENTER;");
        unitsSoldCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));


        priceUnitCol.setCellValueFactory(new PropertyValueFactory<>("sell_price"));
        priceUnitCol.setStyle("-fx-alignment: CENTER;");
        priceUnitCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));

        table.getColumns().addAll(nameCol, unitsSoldCol,priceUnitCol);

        double revenue = 0;
        double spentt = 0;

        Bill giantBill = rwb.getProductsSold();
        for(Product i: giantBill.getProducts()){
            table.getItems().add(i);
            revenue += (i.quantity) * i.sell_price;
            spentt += (i.quantity) * (i.purchase_price);
        }

        // Creating the nodes of the bottom pane, where we choose the date
        DatePicker start = new DatePicker();
        DatePicker end = new DatePicker();

        Text total = new Text();
        Text spendings = new Text();
        Text winnings = new Text();
        total.setFont(new Font("Arial", 18));
        spendings.setFont(new Font("Arial", 18));
        winnings.setFont(new Font("Arial", 18));

        Label spendingsLb = new Label("Spendings : ", spendings);
        spendingsLb.setFont(new Font("Arial", 20));
        spendingsLb.setContentDisplay(ContentDisplay.RIGHT);
        spendings.setText("" + spentt + " ALL");

        Label winningsLb = new Label("Profit : ", winnings);
        winningsLb.setFont(new Font("Arial", 20));
        winningsLb.setContentDisplay(ContentDisplay.RIGHT);
        winnings.setText("" + (revenue - spentt) + " ALL (Salaries: 0)");

        Label totalLb = new Label("Total : ", total);
        totalLb.setFont(new Font("Arial", 20));
        totalLb.setContentDisplay(ContentDisplay.RIGHT);
        total.setText("" + revenue + "ALL");

        Button refresh = new Button("Refresh");

        Label startLb = new Label("Start Date: ", start);
        startLb.setContentDisplay(ContentDisplay.RIGHT);

        Label endLb = new Label("End Date: ", end);
        endLb.setContentDisplay(ContentDisplay.RIGHT);

        // refresh function
        refresh.setOnAction(event -> {
            try{
                double made = 0;
                double spent = 0;
                table.getItems().clear(); // first we clear the table
                LocalDate d1 = start.getValue(); // then we get the date of each fields
                LocalDate d2 = end.getValue();
                MyDate sd = new MyDate(d1);
                MyDate ed = new MyDate(d2);
                Bill bill = rwb.getProductsSold(sd, ed);
                for(Product i: bill.getProducts()){ // add them to the table
                    if(i.purchase_date.biggerEqualTo(sd) && i.purchase_date.smallerEqualTo(ed)) {
                        table.getItems().add(i);
                        made += (i.quantity) * i.sell_price;
                        spent += (i.quantity) * (i.purchase_price);
                    }
                }
                double totalSalariesPaid = getSalariesPaid(sd, ed) * new RWUsers().getSalariesTotal();
                spent += totalSalariesPaid;

                total.setText("" + made + " ALL");
                spendings.setText("" + spent + " ALL");
                winnings.setText("" + (made - spent) + " ALL (Salaries: "+totalSalariesPaid+")");
            }catch (Exception ex){ // if something goes wrong
                Alert al = new Alert(Alert.AlertType.INFORMATION, "Please fill both dates!", ButtonType.OK);
                al.show();
            }
        });

        ScrollPane sp = new ScrollPane();
        sp.setContent(table);

        VBox labelBox = new VBox(5);
        labelBox.getChildren().addAll(totalLb, spendingsLb, winningsLb);

        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(startLb, endLb, refresh, labelBox);
        hbox.prefHeightProperty().bind(displayVbox.heightProperty().multiply(0.2));

        displayVbox.getChildren().clear(); // clearing the display pane before adding our elements
        displayVbox.setAlignment(Pos.CENTER);
        displayVbox.getChildren().addAll(sp, hbox);

    }

    public int getSalariesPaid(MyDate sd, MyDate ed){
        int count = 0;
        if(sd.y < ed.y){
            count += (ed.y - sd.y - 1)*12;
            count += (12 - sd.m) + ed.m;
        }else if(sd.y == ed.y && sd.m < ed.m){
            count += (ed.m - sd.m);
            if(sd.d == 1) count++;
        }else if(sd.m == ed.m && ed.y == sd.y) if(sd.d == 1) count++;

        return count;
    }
}
