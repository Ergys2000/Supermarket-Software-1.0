package GUI;

import GUI.cashierActions.Fun;
import GUI.economistActions.ViewProducts;
import GUI.cashierActions.CreateBill;
import databaseAPI.RWUsers;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Cashier;
import models.MenuButton;

import java.io.File;
import java.nio.file.Path;

public class CashierView {
    public CashierView(Stage primaryStage, Cashier user, RWUsers rwu){
        Pane drawPane = new Pane();
        drawPane.getStyleClass().add("drawPane");
        drawPane.prefHeightProperty().bind(primaryStage.heightProperty());
        drawPane.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.85));
        // creating the main layout pane
        VBox vb = new VBox(15);
        GridPane grid = new GridPane();
        grid.prefHeightProperty().bind(primaryStage.heightProperty());
        grid.prefWidthProperty().bind(primaryStage.widthProperty());
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(15);


        ImageView productImg = new ImageView(new Image("resources" + File.separator + "Product.jpg"));
        productImg.setFitHeight(45);
        productImg.setFitWidth(45);
        productImg.setSmooth(true);
        productImg.setPreserveRatio(true);

        ImageView registerImg = new ImageView(new Image("resources" + File.separator + "Register.jpg"));
        registerImg.setFitHeight(45);
        registerImg.setFitWidth(45);
        registerImg.setSmooth(true);
        registerImg.setPreserveRatio(true);

        // creating the needed nodes
        MenuButton fun = new MenuButton("Fun", vb);
        MenuButton reset = new MenuButton("Reset", vb);
        MenuButton viewProduct = new MenuButton("View Products", vb, productImg);
        MenuButton createBill = new MenuButton("Create Bill", vb, registerImg);
        MenuButton logout = new MenuButton("Log out", vb);

        // adding functions to the buttons
         reset.setOnAction(e -> new CashierView(primaryStage, user, rwu));

        fun.setOnAction(e -> {
            new Fun(drawPane,vb);
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
        viewProduct.setOnAction(e -> {
            new ViewProducts(drawPane);
        });
        createBill.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new CreateBill(drawPane, user, rwu);
            }
        });

        vb.getChildren().addAll(fun, reset, viewProduct,createBill, logout);
        vb.getStyleClass().add("VerticalMenu");
        vb.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.15));
        vb.prefHeightProperty().bind(primaryStage.heightProperty());
        vb.setAlignment(Pos.CENTER);
        // adding the nodes in the pane
        grid.add(vb, 0, 0);
        grid.add(drawPane, 1, 0);

        // showing the stage
        Scene scene = new Scene(grid);
        grid.getStyleClass().add("root1");
        scene.getStylesheets().add("resources" + File.separator + "style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
