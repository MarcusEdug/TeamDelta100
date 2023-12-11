package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Teams;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class Popup {
    private String userStringSubmit;
    private int userIntSubmit;
    private Stage window;

    public void popupWindows(Scene scene){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Pop-up");
        window.setMinWidth(200);
        window.setScene(scene);
        window.showAndWait();
    }
    public String addTeam (){
        TextField userText = new TextField();
        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            userStringSubmit = userText.getText();
            System.out.println(userStringSubmit);
            window.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);

        popupWindows(scene);

        return userStringSubmit;
    }

    public int deleteTeam (List<Teams> teamsList){
        ComboBox comboBox = new ComboBox();
        for (Teams team : teamsList){
            int id = team.getId();
            comboBox.getItems().add(id);
        }


        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            userIntSubmit = (int) comboBox.getValue();
            window.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);

        popupWindows(scene);

        return userIntSubmit;
    }

}
