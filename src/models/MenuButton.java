package models;


import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MenuButton extends Button {
    public MenuButton(String string, VBox menu){
        super(string);
        super.prefWidthProperty().bind(menu.widthProperty());
    }
    public MenuButton(String string, VBox menu, ImageView image){
        super(string, image);
        super.prefWidthProperty().bind(menu.widthProperty());
        this.setContentDisplay(ContentDisplay.LEFT);
    }
}
