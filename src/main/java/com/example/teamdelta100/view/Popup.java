package com.example.teamdelta100.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {
    String userStringSubmit;
    int userIntSubmit;
    public String addTeam (){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add");
        window.setMinWidth(200);



        TextField userText = new TextField();
        Button submit = new Button("Submit ");
        submit.setOnAction(e->{
            userStringSubmit = userText.getText();
            System.out.println(userStringSubmit);
            window.close();
        });




        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        return userStringSubmit;
    }

    public int deleteTeam (){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete");
        window.setMinWidth(200);



        TextField userText = new TextField();
        Button submit = new Button("Submit ");
        submit.setOnAction(e->{
            userStringSubmit = userText.getText();
            userIntSubmit = Integer.parseInt(userStringSubmit);
            System.out.println(userIntSubmit);
            window.close();
        });




        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
        return userIntSubmit;
    }
}
