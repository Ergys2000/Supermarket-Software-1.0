package GUI.economistActions;

import databaseAPI.RWBills;
import databaseAPI.RWProducts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import models.Bill;
import models.MyDate;
import models.Product;

public class CreateCharts {

    public PieChart createPieChart(RWBills rwb){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(String i: rwb.getUniqueCategories()){
            double sales = rwb.getSalesOfCategory(i);
            pieChartData.add(new PieChart.Data(i, sales));
        }
        PieChart pie = new PieChart(pieChartData);
        pie.setLegendSide(Side.LEFT);

        return pie;

    }
    public PieChart createPieChart(RWBills rwb, MyDate sd, MyDate ed){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(String i: rwb.getUniqueCategories(sd, ed)){
            double sales = rwb.getSalesOfCategory(i, sd, ed);
            pieChartData.add(new PieChart.Data(i, sales));
        }
        PieChart pie = new PieChart(pieChartData);
        pie.setLegendSide(Side.LEFT);

        return pie;

    }

    public PieChart createPieChartForCategory(String category, RWBills rwb){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Product i: rwb.getProductsByCategory(category)){
            double sales = (i.quantity) * i.sell_price;
            pieChartData.add(new PieChart.Data(i.getName(), sales));
        }
        PieChart pie = new PieChart(pieChartData);

        return pie;
    }
    public PieChart createPieChartForCategory(String category, RWBills rwb, MyDate sd, MyDate ed){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Product i: rwb.getProductsByCategory(category, sd, ed)){
            double sales = (i.quantity) * i.sell_price;
            pieChartData.add(new PieChart.Data(i.getName(), sales));
        }
        PieChart pie = new PieChart(pieChartData);

        return pie;
    }

    public BarChart createBarChart(RWProducts rwp){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Sale Summary");
        xAxis.setLabel("Product Name");
        yAxis.setLabel("Sold(ALL)");
        XYChart.Series series1 = new XYChart.Series();
        for(String i : rwp.getUniqueCategories()){
            double sold = rwp.getSalesOfCategory(i);
            series1.getData().add(new XYChart.Data<>(i, sold));
        }
        bc.getData().add(series1);
        return bc;
    }

    public BarChart createBarChartForCategory(String category, RWProducts rwp){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Sale Summary");
        xAxis.setLabel("Product Name");
        yAxis.setLabel("Sold(ALL)");

        XYChart.Series series1 = new XYChart.Series();
        for(Product i: rwp.getProductsByCategory(category)){
            double sold = (i.bought_quantity - i.quantity) * i.sell_price;
            series1.getData().add(new XYChart.Data<>(i.name, sold));
        }

        bc.getData().add(series1);
        return bc;
    }
}
