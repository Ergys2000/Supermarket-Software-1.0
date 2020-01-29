package GUI.adminActions;

import GUI.AdminView;
import databaseAPI.RWUsers;
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
import models.*;

import java.io.File;
import java.io.IOException;

public class AddUser {
    public AddUser(RWUsers rwu, Pane drawPane) {
        Stage primaryStage = new Stage();
        // Create the pane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(15);

        // Creating the nodes needed
        Label lb1 = new Label("Username : "); TextField usernameTf = new TextField();
        Label lb2 = new Label("Password : "); TextField passwordTf = new TextField();
        Label lb3 = new Label("Name : "); TextField nameTf = new TextField();
        Label lb4 = new Label("Surname : "); TextField surnameTf = new TextField();
        Label lb5 = new Label("Birthday(d-m-y) : "); DatePicker bdayTf = new DatePicker();
        Button add = new Button("Add");
        Button cancel = new Button("Cancel");

        Label type = new Label("Type: ");
        ComboBox<String> cmb = new ComboBox<>();
        cmb.getItems().addAll("Economist", "Cashier", "Admin");
        cmb.setValue("Type");

        // setting up the placement of the nodes
        VBox v1 = new VBox(16); // this will hold the labels
        VBox v2 = new VBox(5); // this will hold the textfields
        v1.getChildren().addAll(type, lb1, lb2, lb3, lb4, lb5);
        v2.getChildren().addAll(cmb, usernameTf, passwordTf, nameTf, surnameTf, bdayTf);
        HBox hBox = new HBox(10); // this will hold the buttons
        hBox.getChildren().addAll(add, cancel);

        grid.add(v1, 0, 0);
        grid.add(v2, 1, 0);
        grid.add(hBox, 1,1);

        // Setting up the functions of the buttons
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String type = cmb.getValue();
                    String username = usernameTf.getText();
                    String password = passwordTf.getText();
                    String name = nameTf.getText();
                    String surname = surnameTf.getText();
                    MyDate bday = new MyDate(bdayTf.getValue());
                    if(!rwu.usernameExists(username)){
                        if(name.matches("^[a-zA-Z]+$")
                                && surname.matches("^[a-zA-Z]+$")
                                && username.matches("^[a-zA-Z-\\._\\d]+$")){
                            switch (type) {
                                case "Economist": {
                                    Economist user = new Economist(username, password, name, surname, bday);
                                    rwu.add(user);
                                    rwu.update();
                                    Alert al = new Alert(Alert.AlertType.INFORMATION, "User added!",
                                            ButtonType.OK);
                                    al.show();
                                    break;
                                }
                                case "Cashier": {
                                    Cashier user = new Cashier(username, password, name, surname, bday);
                                    rwu.add(user);
                                    rwu.update();
                                    Alert al = new Alert(Alert.AlertType.INFORMATION, "User added!",
                                            ButtonType.OK);
                                    al.show();
                                    break;
                                }
                                case "Admin": {
                                    Admin user = new Admin(username, password, name, surname, "Supermarket", bday);
                                    rwu.add(user);
                                    rwu.update();
                                    Alert al = new Alert(Alert.AlertType.INFORMATION, "User added!",
                                            ButtonType.OK);
                                    al.show();
                                    break;
                                }
                                default: {
                                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Please select a type!",
                                            ButtonType.OK);
                                    al.show();
                                    break;
                                }
                            }
                            new ViewUsers(drawPane);
                        }else{
                            Alert al = new Alert(Alert.AlertType.ERROR, "Invalid username, name or surname", ButtonType.OK);
                            al.show();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR, "Username exists!",
                                ButtonType.OK);
                        al.show();
                    }

                } catch(Exception e){
                    Alert al = new Alert(Alert.AlertType.ERROR, "Fill the fields correctly",
                            ButtonType.OK);
                    al.show();
                }
            }
        });

        cancel.setOnAction(e -> {
            primaryStage.close();
        });
        // Setting up the stage
        Scene scene = new Scene(grid, 600, 400);
        scene.getStylesheets().add("resources" + File.separator + "AddUser.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add User");
        primaryStage.show();
    }

    public static void firstAddUser(RWUsers rwu){

        Stage primaryStage = new Stage();
        // Create the pane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(15);

        // Creating the nodes needed
        Label lb1 = new Label("Username : "); TextField usernameTf = new TextField();
        Label lb2 = new Label("Password : "); TextField passwordTf = new TextField();
        Label lb3 = new Label("Name : "); TextField nameTf = new TextField();
        Label lb4 = new Label("Surname : "); TextField surnameTf = new TextField();
        Label lb5 = new Label("Birthday(d-m-y) : "); DatePicker bdayTf = new DatePicker();
        Button add = new Button("Add");
        Button cancel = new Button("Cancel");

        Label type = new Label("Type: ");
        ComboBox<String> cmb = new ComboBox<>();
        cmb.getItems().addAll("Economist", "Cashier", "Admin");
        cmb.setValue("Admin");
        cmb.setDisable(true);

        // setting up the placement of the nodes
        VBox v1 = new VBox(16); // this will hold the labels
        VBox v2 = new VBox(5); // this will hold the textfields
        v1.getChildren().addAll(type, lb1, lb2, lb3, lb4, lb5);
        v2.getChildren().addAll(cmb, usernameTf, passwordTf, nameTf, surnameTf, bdayTf);
        HBox hBox = new HBox(10); // this will hold the buttons
        hBox.getChildren().addAll(add, cancel);

        grid.add(v1, 0, 0);
        grid.add(v2, 1, 0);
        grid.add(hBox, 1,1);

        // Setting up the functions of the buttons
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    String type = cmb.getValue();
                    String username = usernameTf.getText();
                    String password = passwordTf.getText();
                    String name = nameTf.getText();
                    String surname = surnameTf.getText();
                    MyDate bday = new MyDate(bdayTf.getValue());
                    if(!rwu.usernameExists(username)){
                        if(name.matches("^[a-zA-Z]+$")
                                && surname.matches("^[a-zA-Z]+$")
                                && username.matches("^[a-zA-Z-\\._\\d]+$")){
                            switch (type) {
                                case "Admin": {
                                    Admin user = new Admin(username, password, name, surname, "Supermarket", bday);
                                    rwu.add(user);
                                    rwu.update();
                                    Alert al = new Alert(Alert.AlertType.INFORMATION, "User added!",
                                            ButtonType.OK);
                                    al.show();
                                    primaryStage.close();
                                    break;
                                }
                                default: {
                                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Please select a type!",
                                            ButtonType.OK);
                                    al.show();
                                    break;
                                }
                            }
                        }else{
                            Alert al = new Alert(Alert.AlertType.ERROR, "Invalid username, name or surname", ButtonType.OK);
                            al.show();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR, "Username exists!",
                                ButtonType.OK);
                        al.show();
                    }

                } catch(Exception e){
                    Alert al = new Alert(Alert.AlertType.ERROR, "Fill the fields correctly",
                            ButtonType.OK);
                    al.show();
                }
            }
        });

        cancel.setOnAction(e -> {
            primaryStage.close();
        });
        // Setting up the stage
        Scene scene = new Scene(grid, 600, 400);
        scene.getStylesheets().add("resources" + File.separator + "AddUser.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add User");
        primaryStage.show();
    }
}
