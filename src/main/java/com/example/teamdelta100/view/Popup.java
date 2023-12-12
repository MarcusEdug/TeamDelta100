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
    private ComboBox comboBox;

    public void popupWindows(Scene scene){
        window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Pop-up");
        window.setHeight(100);
        window.setWidth(250);
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
        comboBox = new ComboBox();
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
    Teams tempTeam;
    public Teams updateTeam(Teams team){
        tempTeam = team;
        Text currentName = new Text(tempTeam.getName());
        TextField newName = new TextField();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(currentName, newName);


        Button submit = new Button("Submit");
        submit.setOnAction(e->{

            tempTeam.setName(newName.getText());
            window.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox,submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        popupWindows(scene);
        /*
        comboBox = new ComboBox();
        for (Teams team : teamsList){
            String name = team.getName();
            comboBox.getItems().add(name);
        }
        comboBox.getSelectionModel().select(0);




        TextArea newName = new TextArea();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(comboBox,newName);

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            tempTeam = (Teams) comboBox.getValue();
            tempTeam.setName(newName.getText());

            window.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox,submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        popupWindows(scene);

        return tempTeam;

         */
        return tempTeam;
    }

    public Teams choosTeam (List<Teams> teamsList){

        comboBox = new ComboBox();
        for (Teams team : teamsList){
            int id = team.getId();
            comboBox.getItems().add(id);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            int selectedID = (int) comboBox.getValue();
                tempTeam = teamsList.get(selectedID-1);
                window.close();

        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        popupWindows(scene);
        return tempTeam;
    }
    public void updateTextArea(List<Teams> teamsList){

    }
}
