package com.example.teamdelta100.view.Team;

import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Popup {
    private String userStringSubmit;
    private int userIntSubmit;
    private Stage window;
    private ComboBox comboBox;
    private Scene scene;
    private String titleName;
    private Label featureText;
    private int teamId;
    private int playerId;
    private Teams tempTeam;
    private Text error = new Text();
    public void popupWindows(){
        window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titleName);
        window.setHeight(150);
        window.setWidth(320);
        window.setScene(scene);
        window.showAndWait();
    }

    public String addTeam (){
        TextField userText = new TextField();
        userText.setMaxWidth(100);
        titleName = "Add team";
        featureText = new Label("What is your Team name?");

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            userStringSubmit = userText.getText();
            System.out.println(userStringSubmit);
            window.close();
        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,userText, submit);
        vBox.setAlignment(Pos.CENTER);
        scene = new Scene(vBox);

        popupWindows();

        return userStringSubmit;
    }

    public int deleteTeam (List<Teams> teamsList){
        titleName = "Delete team";
        featureText = new Label("Which team do you wanna delete?");

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

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindows();

        return userIntSubmit;
    }

    public Teams updateTeam(Teams team){
        titleName = "Change name ";
        featureText = new Label("Select a new namn for the team");

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

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,hBox,submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);
        popupWindows();
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
        titleName = "Change name";
        featureText = new Label("Select which team to change namn on");

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
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);
        popupWindows();
        return tempTeam;
    }

    public Teams updateTextArea(List<Teams> teamsList){
        titleName = "Change name";
        featureText = new Label("Select which team to change namn on");

        Label explainText1 = new Label("Select team: ");
        Label explainText2 = new Label("Write new name: ");

        Text currentName = new Text();
        TextField newName = new TextField();

        comboBox = new ComboBox();
        for (Teams team : teamsList){
            comboBox.getItems().add(team);
        }




        /*Button select = new Button("Select");
        select.setOnAction(e->{



            currentName.setText(tempTeam.getName());

        });

         */

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            tempTeam = (Teams) comboBox.getValue();
            tempTeam.setName(newName.getText());
            window.close();
        });

        HBox overHBox = new HBox(3);
        overHBox.getChildren().addAll(explainText1,comboBox);
        overHBox.setAlignment(Pos.CENTER);

        HBox underHBox = new HBox(3);
        underHBox.getChildren().addAll(explainText2, currentName, newName, submit);
        underHBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,overHBox,underHBox);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindows();
        return tempTeam;
    }

    public void assignPlayerToTeam(List<Teams> teamsList, List<Player> playList){
        titleName = "Assign player";
        featureText = new Label("Assign player to a team");

        ComboBox comboBoxTeams = new ComboBox();
        for (Teams team : teamsList){
            comboBoxTeams.getItems().add(team);
        }
        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playList){
            comboBoxPlayer.getItems().add(player);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            error.setText("");
            tempTeam = (Teams) comboBoxTeams.getValue();
            Player tempPlayer = (Player) comboBoxPlayer.getValue();
            if(tempPlayer.getTeams() == null){
                tempPlayer.setTeams(tempTeam);
                teamId = tempTeam.getId();
                playerId = tempPlayer.getId();
                window.close();
            }
            else {
                error.setText("Spelaren har redan ett lag");
            }
        });
        Button close = new Button("Close");
        close.setOnAction(e-> window.close());

        HBox overHBox = new HBox(3);
        overHBox.getChildren().addAll(comboBoxPlayer,comboBoxTeams);
        overHBox.setAlignment(Pos.CENTER);

        HBox underHBox = new HBox(3);
        underHBox.getChildren().addAll(submit, close);
        underHBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,overHBox,underHBox,error);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindows();


    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    //public int breakOutInt ()
}
