package GUI.adminActions;

import GUI.AdminView;
import databaseAPI.RWUsers;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.*;

import java.io.File;


public class ViewUsers {
    public ViewUsers(Pane drawPane) {
        RWUsers rwu = new RWUsers();

        TableView<User> table = new TableView<User>();
        table.prefWidthProperty().bind(drawPane.widthProperty());
        table.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.70));
        Label label = new Label("Users");
        label.setFont(new Font("Arial", 20));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setStyle("-fx-alignment: CENTER;");
        usernameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setStyle("-fx-alignment: CENTER;");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setStyle("-fx-alignment: CENTER;");
        surnameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        table.getColumns().add(usernameCol);
        table.getColumns().add(nameCol);
        table.getColumns().add(surnameCol);

        // Making the table editable
        TableView.TableViewSelectionModel<User> selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        Button edit = new Button("Edit");
        Button removeUser = new Button("Remove User");
        Button adddUser = new Button("Add User");

        // creating the search bar
        TextField searchBox = new TextField();
        searchBox.setPromptText("Search...");


        HBox searchBar = new HBox(5);
        searchBar.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.02));
        searchBar.prefWidthProperty().bind(drawPane.widthProperty());
        searchBar.setAlignment(Pos.TOP_RIGHT);
        searchBar.getChildren().addAll(searchBox);

        // adding functions to edit the users
        adddUser.setOnAction(e -> {
            new AddUser(rwu, drawPane);
        });
        edit.setOnAction(e -> {
            User selected = selectionModel.getSelectedItem();
            new EditUser(drawPane, selected, rwu);
        });
        removeUser.setOnAction(e -> {
            User selected = selectionModel.getSelectedItem();
            if(selected != null){
                if(selected instanceof Admin){
                    Alert al = new Alert(Alert.AlertType.INFORMATION, "Admin cannot be modified", ButtonType.OK);
                    al.show();
                }else {
                    rwu.delete(selected);
                    rwu.update();
                    Alert al = new Alert(Alert.AlertType.INFORMATION, "User Deleted", ButtonType.OK);
                    al.show();
                    new ViewUsers(drawPane);
                }
            } else {
                Alert al = new Alert(Alert.AlertType.INFORMATION, "No user found!", ButtonType.OK);
                al.show();
            }
        });
        searchBox.setOnKeyReleased(e -> {
            table.getItems().clear();
            String string = searchBox.getText().toLowerCase();
            for(User i : rwu.getUsers()){
                if(i instanceof Cashier){
                    if(((Cashier) i).name.contains(string) || i.getUsername().contains(string) ||
                            ((Cashier) i).surname.toLowerCase().contains(string)){
                        table.getItems().add(i);
                    }
                } else if(i instanceof Economist){
                    if(((Economist) i).name.contains(string) || i.getUsername().contains(string) ||
                            ((Economist) i).surname.toLowerCase().contains(string)){
                        table.getItems().add(i);
                    }
                } else {
                    if(((Admin) i).name.contains(string) || i.getUsername().contains(string) ||
                            ((Admin) i).surname.toLowerCase().contains(string)){
                        table.getItems().add(i);
                    }
                }
            }
        });


        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);


        HBox hbox = new HBox(30);
        hbox.getChildren().addAll(adddUser, edit, removeUser);
        hbox.setAlignment(Pos.CENTER);
        hbox.prefWidthProperty().bind(drawPane.widthProperty());
        hbox.prefHeightProperty().bind(drawPane.heightProperty().multiply(0.25));

        vbox.getChildren().addAll(label, searchBar, table, hbox);

        for(User i: rwu.getUsers()){
            table.getItems().add(i);
        }
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setMinSize(500,500);
        grid.add(vbox, 0,0);

        drawPane.getChildren().clear();
        drawPane.getChildren().add(grid);
    }
}
