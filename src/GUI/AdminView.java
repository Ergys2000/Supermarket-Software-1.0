package GUI;

import GUI.adminActions.*;
import GUI.economistActions.ViewProducts;
import GUI.economistActions.ViewStatistics;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Admin;
import models.Cashier;
import models.User;

import java.io.File;


public class AdminView {
    public AdminView(Stage primaryStage, Admin user, RWUsers rwu){
        // Creating the draw pane
        Pane drawPane = new Pane();
        drawPane.getStyleClass().add("drawPane");
        drawPane.prefHeightProperty().bind(primaryStage.heightProperty());
        drawPane.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.85));
        // Creating the main layout
        VBox vb = new VBox(25);
        GridPane grid = new GridPane();
        grid.setVgap(0);
        grid.setHgap(0);
        grid.setAlignment(Pos.TOP_LEFT);

        // printing the users
        for(User i: rwu.getUsers()){
           System.out.println(i);
           if(i instanceof Cashier){
               System.out.println(((Cashier) i).computeSales());
           }
        }
        // creating the images for the buttons
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

        ImageView userImg = new ImageView(new Image("resources" + File.separator + "User.png"));
        userImg.setFitHeight(40);
        userImg.setFitWidth(40);
        userImg.setSmooth(true);
        userImg.setPreserveRatio(true);

        ImageView productImg = new ImageView(new Image("resources" + File.separator + "Product.jpg"));
        productImg.setFitHeight(45);
        productImg.setFitWidth(45);
        productImg.setSmooth(true);
        productImg.setPreserveRatio(true);

        // Creating the nodes that we will need in the layout
        Text desc = new Text("Welcome " + user.getName() + " " + user.getSurname());
        models.MenuButton viewSales = new models.MenuButton("View sales", vb, saleImg);
        models.MenuButton viewProducts = new models.MenuButton("View Products", vb, productImg);
        models.MenuButton viewUsers = new models.MenuButton("View Users", vb, userImg);
        models.MenuButton statistics = new models.MenuButton("Statistics", vb, statisticsImg);
        models.MenuButton logOut = new models.MenuButton("Log out", vb);

        // Setting the functions of the buttons
        viewUsers.setOnAction(e -> {
            new ViewUsers(drawPane);
        });
        viewSales.setOnAction(e -> {
            new GUI.economistActions.ViewSales(drawPane);
        });
        viewProducts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ViewProducts(drawPane);
            }
        });
        statistics.setOnAction(e -> {
            new ViewStatistics(drawPane);
        });
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new LogIn().start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Putting the nodes inside the grid
        vb.getStyleClass().add("VerticalMenu");
        vb.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.15));
        vb.prefHeightProperty().bind(primaryStage.heightProperty());
        vb.getChildren().addAll(viewUsers, viewProducts, viewSales, statistics, logOut);
        vb.setAlignment(Pos.CENTER_LEFT);

        grid.add(vb, 0 ,0);
        grid.add(drawPane, 1, 0);
        grid.prefHeightProperty().bind(primaryStage.heightProperty());
        grid.prefWidthProperty().bind(primaryStage.widthProperty());

        // Creating the scene and setting up the stage
        Scene scene = new Scene(grid);
        grid.getStyleClass().add("root1");
        scene.getStylesheets().add("resources" + File.separator + "style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin View");
        primaryStage.show();
    }
}
