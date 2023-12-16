package com.example.teamdelta100.view;

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

public class TeamPopup {
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

    private Label explainText1;
    private Label explainText2;

    public void popupWindows(){
        window = new Stage();
        window.setTitle(titleName);
        window.setHeight(165);
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

        explainText1 = new Label("Select team: ");
        explainText2 = new Label("Write new name: ");

        Text currentName = new Text();
        TextField newName = new TextField();

        comboBox = new ComboBox();
        for (Teams team : teamsList){
            comboBox.getItems().add(team);
        }

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

        explainText1 = new Label("Select player: ");
        explainText2 = new Label("Select team: ");

        ComboBox comboBoxTeams = new ComboBox();
        for (Teams team : teamsList){
            comboBoxTeams.getItems().add(team);
        }
        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playList){
            comboBoxPlayer.getItems().add(player);
        }

        comboBoxPlayer.setPrefWidth(115);
        comboBoxTeams.setPrefWidth(115);

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            error.setText("");
            tempTeam = (Teams) comboBoxTeams.getValue();
            Player tempPlayer = (Player) comboBoxPlayer.getValue();
            if(tempPlayer.getTeams() == null){
                tempPlayer.setTeams(tempTeam);
                teamId = tempTeam.getId();
                playerId = tempPlayer.getId();
                if(playerId != 0 || teamId != 0){
                    window.close();
                }
                else {
                    error.setText("Select a team and player!");
                }
            }
            else {
                error.setText("The player already has a team");
            }
        });
        Button close = new Button("Close");
        close.setOnAction(e-> {
            playerId = 0;
            teamId = 0;
            window.close();
                }

        );


        VBox textVBox = new VBox(2);
        textVBox.getChildren().addAll(explainText1,explainText2);
        textVBox.setAlignment(Pos.CENTER);

        VBox comboBoxVBox = new VBox(2);
        comboBoxVBox.getChildren().addAll(comboBoxPlayer, comboBoxTeams);
        comboBoxVBox.setAlignment(Pos.CENTER);

        HBox overHBox2 = new HBox(3);
        overHBox2.getChildren().addAll(textVBox,comboBoxVBox);
        overHBox2.setAlignment(Pos.CENTER);

        HBox buttonHBox = new HBox(3);
        buttonHBox.getChildren().addAll(submit, close);
        buttonHBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,overHBox2,buttonHBox,error);
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


}
