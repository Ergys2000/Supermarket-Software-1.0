package GUI;

import GUI.economistActions.ViewProducts;
import GUI.economistActions.ViewSales;
import GUI.economistActions.ViewStatistics;
import GUI.economistActions.ViewSuppliers;
import databaseAPI.RWProducts;
import databaseAPI.RWUsers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Economist;
import models.MenuButton;

import java.io.File;

public class EconomistView {
    public EconomistView(Stage primaryStage, Economist user, RWUsers rwu) {
        // creating the pane that we will draw on
        Pane drawPane = new Pane();
        drawPane.getStyleClass().add("drawPane");
        drawPane.prefHeightProperty().bind(primaryStage.heightProperty());
        drawPane.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.85));

        RWProducts rwp = new RWProducts();
        rwp.checkMissing();
        // creating the main layout pane
        VBox vb = new VBox(15);
        GridPane grid = new GridPane();
        grid.setMinSize(400,400);
        grid.prefHeightProperty().bind(primaryStage.heightProperty());
        grid.prefWidthProperty().bind(primaryStage.widthProperty());
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(0);


        ImageView saleImg = new ImageView(new Image("resources" + File.separator + "Sales.jpg"));
        saleImg.setFitHeight(40);
        saleImg.setFitWidth(40);
        saleImg.setSmooth(true);
        saleImg.setPreserveRatio(true);

        ImageView statisticsImg = new ImageView(new Image("resources" + File.separator + "Statistics.png"));
        statisticsImg.setFitHeight(40);
        statisticsImg.setFitWidth(40);
        statisticsImg.setSmooth(true);
        statisticsImg.setPreserveRatio(true);

        ImageView productImg = new ImageView(new Image("resources" + File.separator + "Product.jpg"));
        productImg.setFitHeight(45);
        productImg.setFitWidth(45);
        productImg.setSmooth(true);
        productImg.setPreserveRatio(true);

        ImageView supplierImg = new ImageView(new Image("resources" + File.separator + "Supplier.jpg"));
        supplierImg.setFitHeight(45);
        supplierImg.setFitWidth(45);
        supplierImg.setSmooth(true);
        supplierImg.setPreserveRatio(true);

        // creating the needed nodes
        MenuButton viewProduct = new MenuButton("View products", vb, productImg);
        MenuButton checkCashiers = new MenuButton("Check Cashiers", vb, saleImg);
        MenuButton statistics = new MenuButton("Statistics", vb, statisticsImg);
        MenuButton suppliers = new MenuButton("Suppliers", vb, supplierImg);
        MenuButton logout = new MenuButton("Log out", vb);

        // adding functions to the buttons
        suppliers.setOnAction(e -> {
            new ViewSuppliers(drawPane, rwp);
        });
        viewProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ViewProducts(drawPane);
            }
        });

        statistics.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ViewStatistics(drawPane);
            }
        });
        checkCashiers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ViewSales(drawPane);
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new LogIn().start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // adding the nodes in the pane
        vb.getChildren().addAll(viewProduct, checkCashiers, statistics, suppliers, logout);
        vb.getStyleClass().add("VerticalMenu");
        vb.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.15));
        vb.prefHeightProperty().bind(primaryStage.heightProperty());
        vb.setAlignment(Pos.CENTER);

        grid.add(vb, 0, 0);
        grid.add(drawPane, 1, 0);

        // showing the stage
        Scene scene = new Scene(grid);
        grid.getStyleClass().add("root1");
        scene.getStylesheets().add("resources"+ File.separator +"style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
