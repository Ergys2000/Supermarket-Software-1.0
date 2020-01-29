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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Admin;
import models.Cashier;
import models.Economist;
import models.MenuButton;
import models.User;

import java.io.File;

public class EditUser {
    public EditUser(Pane drawPane, User user, RWUsers rwu){
        // setting up the new stage
        Stage stage = new Stage();
        stage.setTitle("Change User");

        if(user instanceof Admin){
            Alert al = new Alert(Alert.AlertType.INFORMATION, "Admin cannot be modified", ButtonType.OK);
            al.show();
        }else if(user instanceof Cashier || user instanceof Economist){
            editFields(user, rwu, stage, drawPane);
        }else{
            Alert al = new Alert(Alert.AlertType.INFORMATION, "The user does not exist!", ButtonType.OK);
            al.show();
        }

        // putting the scene in the stage
    }
    public void editFields(User user, RWUsers rwu, Stage stage, Pane prevdrawPane){
        String currUsername;
        String currName;
        String currSurname;
        double salary;
        if(user instanceof Admin){
            currUsername = ((Admin) user).getUsername();
            currName =     ((Admin) user).getName();
            currSurname =  ((Admin) user).getSurname();
            salary = 0;
        } else if(user instanceof Economist){
            currUsername = ((Economist) user).getUsername();
            currName =     ((Economist) user).getName();
            currSurname =  ((Economist) user).getSurname();
            salary = ((Economist) user).salary;
        } else {
            currUsername = ((Cashier) user).getUsername();
            currName =     ((Cashier) user).getName();
            currSurname =  ((Cashier) user).getSurname();
            salary = ((Cashier) user).salary;
        }

        Pane drawPane = new Pane(); // where we will draw our buttons
        GridPane grid = new GridPane(); // the main pane


        // here we create the vertical menu
        VBox menuBox = new VBox(10);


        MenuButton changeName = new MenuButton("Change Name", menuBox);
        MenuButton changeSurname = new MenuButton("Change Surname", menuBox);
        MenuButton changeUsername = new MenuButton("Change Username", menuBox);
        MenuButton changePassword = new MenuButton("Change Password", menuBox);
        MenuButton changeSalary = new MenuButton("Change Salary", menuBox);
        MenuButton done = new MenuButton("Done", menuBox);
        menuBox.getChildren().addAll(changeName, changeSurname, changeUsername, changePassword, changeSalary, done);

        changeName.setOnAction(e -> {

            VBox vbox = new VBox(15);
            HBox hbox = new HBox(15);

            TextField newName = new TextField();
            newName.setPromptText("New Name");
            Label currNameL = new Label("(" + currName + ")", newName);
            currNameL.setContentDisplay(ContentDisplay.RIGHT);

            Button save = new Button("Save");

            save.setOnAction(event -> {
                String newName1 = newName.getText();
                if(!newName1.isEmpty()){
                    if(user instanceof Economist){
                        ((Economist) user).setName(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Cashier){
                        ((Cashier) user).setName(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Admin){
                        ((Admin) user).setName(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR, "Please fill the field!", ButtonType.OK);
                    al.show();
                }
                new ViewUsers(prevdrawPane);
            });


            vbox.getChildren().add(currNameL);
            hbox.getChildren().addAll(save );

            GridPane all = new GridPane();
            all.prefHeightProperty().bind(drawPane.heightProperty());
            all.prefWidthProperty().bind(drawPane.widthProperty());
            all.setAlignment(Pos.CENTER);
            all.setVgap(15);
            all.add(vbox, 0, 0);
            all.add(hbox, 0, 1);
            drawPane.getChildren().clear();
            drawPane.getChildren().add(all);
        });

        changeSurname.setOnAction(e -> {
            VBox vbox = new VBox(15);
            HBox hbox = new HBox(15);

            TextField newValue = new TextField();
            newValue.setPromptText("New Surname");
            Label currValueL = new Label("(" + currSurname + ")", newValue);
            currValueL.setContentDisplay(ContentDisplay.RIGHT);

            Button save = new Button("Save");

            save.setOnAction(event -> {
                String newName1 = newValue.getText();
                if(!newName1.isEmpty()){
                    if(user instanceof Economist){
                        ((Economist) user).setSurname(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Cashier){
                        ((Cashier) user).setSurname(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Admin){
                        ((Admin) user).setSurname(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR, "Please fill the field!", ButtonType.OK);
                    al.show();
                }
                new ViewUsers(prevdrawPane);
            });


            vbox.getChildren().add(currValueL);
            hbox.getChildren().addAll(save);

            GridPane all = new GridPane();
            all.prefHeightProperty().bind(drawPane.heightProperty());
            all.prefWidthProperty().bind(drawPane.widthProperty());
            all.setVgap(20);
            all.setAlignment(Pos.CENTER);
            all.add(vbox, 0, 0);
            all.add(hbox, 0, 1);
            drawPane.getChildren().clear();
            drawPane.getChildren().add(all);

        });

        changeUsername.setOnAction(e -> {
            VBox vbox = new VBox(15);
            HBox hbox = new HBox(15);

            TextField newValue = new TextField();
            newValue.setPromptText("New Username");
            Label currValueL = new Label("(" + currUsername + ")", newValue);
            currValueL.setContentDisplay(ContentDisplay.RIGHT);

            Button save = new Button("Save");

            save.setOnAction(event -> {
                String newName1 = newValue.getText();
                if(!newName1.isEmpty()){
                    if(user instanceof Economist){
                        ((Economist) user).setUsername(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Cashier){
                        ((Cashier) user).setUsername(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Admin){
                        ((Admin) user).setUsername(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR, "Please fill the field!", ButtonType.OK);
                    al.show();
                }
                new ViewUsers(prevdrawPane);
            });


            vbox.getChildren().add(currValueL);
            hbox.getChildren().addAll(save);

            GridPane all = new GridPane();
            all.prefHeightProperty().bind(drawPane.heightProperty());
            all.prefWidthProperty().bind(drawPane.widthProperty());
            all.setVgap(20);
            all.setAlignment(Pos.CENTER);
            all.add(vbox, 0, 0);
            all.add(hbox, 0, 1);
            drawPane.getChildren().clear();
            drawPane.getChildren().add(all);

        });

        changePassword.setOnAction(e -> {

            VBox vbox = new VBox(15);
            HBox hbox = new HBox(15);

            TextField newValue = new TextField();
            newValue.setPromptText("New Password");
            Label currValueL = new Label( "", newValue);
            currValueL.setContentDisplay(ContentDisplay.RIGHT);

            Button save = new Button("Save");

            save.setOnAction(event -> {
                String newName1 = newValue.getText();
                if(!newName1.isEmpty()){
                    if(user instanceof Economist){
                        ((Economist) user).setPassword(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Cashier){
                        ((Cashier) user).setPassword(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Admin){
                        ((Admin) user).setPassword(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR, "Please fill the field!", ButtonType.OK);
                    al.show();
                }
                new ViewUsers(prevdrawPane);
            });


            vbox.getChildren().add(currValueL);
            hbox.getChildren().addAll(save);

            GridPane all = new GridPane();
            all.prefHeightProperty().bind(drawPane.heightProperty());
            all.prefWidthProperty().bind(drawPane.widthProperty());
            all.setAlignment(Pos.CENTER);
            all.setVgap(20);
            all.add(vbox, 0, 0);
            all.add(hbox, 0, 1);
            drawPane.getChildren().clear();
            drawPane.getChildren().add(all);

        });

        changeSalary.setOnAction(e -> {

            VBox vbox = new VBox(15);
            HBox hbox = new HBox(15);

            TextField newValue = new TextField();
            newValue.setPromptText("New Salary");
            Label currValueL = new Label( "(" + salary + ")", newValue);
            currValueL.setContentDisplay(ContentDisplay.RIGHT);

            Button save = new Button("Save");

            save.setOnAction(event -> {
                double newName1 = Double.parseDouble(newValue.getText());
                try{
                    if(user instanceof Economist){
                        ((Economist) user).setSalary(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Cashier){
                        ((Cashier) user).setSalary(newName1);
                        rwu.update();
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "User edited successfully!", ButtonType.OK);
                        al.show();
                    }else if(user instanceof Admin){
                        Alert al = new Alert(Alert.AlertType.INFORMATION, "Admin has no salary!", ButtonType.OK);
                        al.show();
                    }
                }catch (Exception ex){
                    Alert al = new Alert(Alert.AlertType.ERROR, "Please fill the field!", ButtonType.OK);
                    al.show();
                }
                new ViewUsers(prevdrawPane);
            });
            vbox.getChildren().add(currValueL);
            hbox.getChildren().addAll(save);

            GridPane all = new GridPane();
            all.prefHeightProperty().bind(drawPane.heightProperty());
            all.prefWidthProperty().bind(drawPane.widthProperty());
            all.setAlignment(Pos.CENTER);
            all.setVgap(20);
            all.add(vbox, 0, 0);
            all.add(hbox, 0, 1);
            drawPane.getChildren().clear();
            drawPane.getChildren().add(all);
        });

        done.setOnAction(event -> {
            stage.close();
        });


        grid.setAlignment(Pos.TOP_LEFT);

        menuBox.getStyleClass().add("VerticalMenu");
        menuBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.35));
        menuBox.prefHeightProperty().bind(stage.heightProperty());
        menuBox.setAlignment(Pos.CENTER);

        drawPane.prefWidthProperty().bind(stage.widthProperty().multiply(0.65));
        drawPane.prefHeightProperty().bind(stage.heightProperty());
        drawPane.setStyle("-fx-background-color: white;");

        grid.add(menuBox, 0, 0);
        grid.add(drawPane, 1, 0);

        Scene scene = new Scene(grid, 500, 500);
        scene.getStylesheets().add("resources" + File.separator + "style.css");

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

