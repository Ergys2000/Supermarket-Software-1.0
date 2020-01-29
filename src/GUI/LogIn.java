package GUI;

import databaseAPI.RWUsers;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Admin;
import models.Cashier;
import models.Economist;
import models.User;

import java.io.File;


public class LogIn extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(900);
        // Creating the layout pane and getting the users
        RWUsers rwu = new RWUsers();
        GridPane grid = new GridPane();
        grid.setMinSize(900, 400);
        grid.getStyleClass().add("root");
        grid.prefHeightProperty().bind(primaryStage.heightProperty());
        grid.prefWidthProperty().bind(primaryStage.widthProperty());
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);

        // Creating the nodes needed in the window
        ImageView userImage = new ImageView(new Image("resources"+ File.separator + "UserIcon.png"));
        ImageView passImage = new ImageView(new Image("resources" + File.separator + "PassIcon.png"));
        userImage.setFitWidth(50); userImage.setSmooth(true);
        userImage.setFitHeight(50); passImage.setSmooth(true);
        passImage.setFitHeight(50);
        passImage.setFitWidth(50);

        TextField usernameTf = new TextField();
        usernameTf.setPromptText("Username");
        usernameTf.prefWidthProperty().bind(primaryStage.heightProperty().multiply(0.3));
        usernameTf.getStyleClass().add("textfield");

        PasswordField passwordTf = new PasswordField();
        passwordTf.setPromptText("Password");
        passwordTf.prefWidthProperty().bind(primaryStage.heightProperty().multiply(0.3));
        passwordTf.getStyleClass().add("textfield");

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button");

        Button logIn = new Button("Log in");
        logIn.getStyleClass().add("button");

        // Inserting the nodes in the pane
        HBox hb = new HBox(15);
        hb.getChildren().addAll(logIn, cancel);

        VBox imageVb = new VBox(15);
        imageVb.getChildren().addAll(userImage, passImage);

        VBox fields = new VBox(25);
        fields.getChildren().addAll(usernameTf, passwordTf, hb);

        grid.add(imageVb,0,0);
        grid.add(fields, 1, 0);

        // setting the functions of buttons
        cancel.setOnAction(e->{
            primaryStage.close();
        });
        logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameTf.getText();
                String password = passwordTf.getText();
                User user = rwu.checkLogin(username, password);
                if(user != null){
                    if(user instanceof Admin){
                        new AdminView(primaryStage, (Admin)user, rwu);
                    }else if(user instanceof Cashier){
                        new CashierView(primaryStage, (Cashier)user, rwu);
                    }else if(user instanceof Economist){
                        new EconomistView(primaryStage, (Economist) user, rwu);
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Wrong Credentials",
                            ButtonType.OK);
                    al.show();
                }
            }
        });

        // Creating the scene and putting it in the stage
        Scene scene = new Scene(grid);
        grid.getStyleClass().add("root1");
        scene.getStylesheets().add("resources" + File.separator + "style.css");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("resources" + File.separator + "WindowIcon.jpg"));
        primaryStage.setTitle("Supermarket Log in");
        primaryStage.show();
    }
}
