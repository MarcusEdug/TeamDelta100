package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class TeamPopup {
    private String userStringSubmit;
    private int userIntSubmit;
    //private Stage window;
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

    public Stage popupWindows(){
        Stage window = new Stage();
        window.setTitle(titleName);
        window.setHeight(165);
        window.setWidth(320);
        window.setOnCloseRequest(e-> {
            e.consume();
        });

        return window;
    }

    public String addTeam (){
        Stage window = popupWindows();
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
        window.setScene(scene);
        window.showAndWait();



        return userStringSubmit;
    }

    public int deleteTeam (List<Teams> teamsList){
        Stage window = popupWindows();
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
        window.setScene(scene);
        window.showAndWait();


        return userIntSubmit;
    }

    public Teams updateTeam(Teams team){
        Stage window = popupWindows();
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
        window.setScene(scene);
        window.showAndWait();

        return tempTeam;
    }

    public Teams choosTeam (List<Teams> teamsList){
        Stage window = popupWindows();
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
        window.setScene(scene);
        window.showAndWait();

        return tempTeam;
    }

    public Teams updateTeamName(List<Teams> teamsList){
        Stage window = popupWindows();
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
        window.setScene(scene);
        window.showAndWait();

        return tempTeam;
    }


    public void assignPlayerToTeam(List<Teams> teamsList, List<Player> playList){
        Stage window = popupWindows();
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

        window.setScene(scene);
        window.showAndWait();


    }

    private Player player = new Player();

    public void removePlayerFromTeam(List<Player> playList){
        Stage window = popupWindows();
        titleName = "Remove player";
        featureText = new Label("Remove a player from a team");

        explainText1 = new Label("Select player: ");
        explainText2 = new Label("Team name: ");

        /*ComboBox comboBoxTeams = new ComboBox();
        for (Teams team : teamsList){
            comboBoxTeams.getItems().add(team);
        }

         */
        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playList){
            if(player.getTeams() != null ) {
                comboBoxPlayer.getItems().add(player);
            }
        }
        Text teamName = new Text();
        teamName.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        comboBoxPlayer.setOnAction(e->{
            player = (Player) comboBoxPlayer.getValue();
            teamName.setText("Is assign to team numnber: " + player.getId());
            tempTeam =  player.getTeams();
        });

        HBox overHBox = new HBox(8);
        overHBox.getChildren().addAll(explainText1, comboBoxPlayer);
        overHBox.setAlignment(Pos.CENTER);

        HBox underHBox = new HBox(2);
        underHBox.getChildren().addAll(explainText2, teamName);
        underHBox.setAlignment(Pos.CENTER);

        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            error.setText("");
            if(comboBoxPlayer.getValue()!=null) {
                error.setText("");
                teamId = tempTeam.getId();
                playerId = player.getId();
                window.close();
            }
            else {
                error.setText("Choose a player");
            }
        });
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,overHBox,underHBox,submit,error);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        window.setScene(scene);
        window.showAndWait();

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

    public Teams getTempTeam() {
        return tempTeam;
    }

    public void setTempTeam(Teams tempTeam) {
        this.tempTeam = tempTeam;
    }
}
