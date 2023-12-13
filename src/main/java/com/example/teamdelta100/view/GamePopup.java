package com.example.teamdelta100.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePopup {
    String userStringInput;
    int userIntInput;
    Stage window;

    public void popupWindow (Scene scene) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Popup");
        window.setMinWidth(200);
        window.setScene(scene);
        window.showAndWait();
    }

    public String addGame () {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add game");
        window.setMinWidth(200);

        TextField userText = new TextField();
        Button submit = new Button("Submit");
        submit.setOnAction(e->  {
            userStringInput = userText.getText();
            System.out.println(userStringInput);
            window.close();
        });


        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        return userStringInput;
    }
    public int deleteGame() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete game");
        window.setMinWidth(200);

        TextField userText = new TextField();
        Button submit = new Button("Submit");
        submit.setOnAction(e-> {
            userStringInput = userText.getText();
            userIntInput = Integer.parseInt(userStringInput);
            System.out.println(userIntInput);
            window.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        return userIntInput;
    }
}
