package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.entities.Games;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class GamePopup {

    GameController gameController = new GameController();
    private String userStringInput;
    private int userIntInput;
    private Stage window;
    private ComboBox comboBox;
    private Scene scene;
    private String titleName;
    private String gameName;
    private Label featureText;
    private int gameId;
    private int playerId;
    private int teamId;
    private Player tempPlayer;
    private Games tempGame;
    private Text errorMessage = new Text();

    public void popupWindow() {
        window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titleName);
        window.setMinWidth(200);
        window.setScene(scene);
        window.showAndWait();
    }

    public String addGame() {
        /*TextField userText = new TextField();
        userText.setMaxWidth(100);
        titleName = "Add game";
        featureText = new Label("What is the name of the game?");*/
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add game");
        window.setMinWidth(200);

        TextField userText = new TextField();
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            userStringInput = userText.getText();
            System.out.println(userStringInput);
            window.close();
        });


        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

        //popupWindow();

        return userStringInput;
    }

    public String addGameGenre(int id) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Game Genre");
        window.setMaxWidth(200);


        TextField userText = new TextField();
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            userStringInput = userText.getText();
            System.out.println(userStringInput);
            window.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

        return userStringInput;
    }

    public int deleteGame(List<Games> gamesList) {

        titleName = "Delete game";
        featureText = new Label("Which game do you want to delete?");

        comboBox = new ComboBox<>();
        for (Games games : gamesList) {
            int id = games.getGameId();
            comboBox.getItems().add(id);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            userIntInput = (int) comboBox.getValue();
            window.close();
        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindow();

        return userIntInput;
    }

    public Games updateGames(Games games) {
        titleName = "Change name";
        featureText = new Label("Select a new name for the game");

        tempGame = games;
        Text currentName = new Text(tempGame.getGameName());
        TextField newName = new TextField();

      /*  comboBox = new ComboBox<>();
        for (Games games : gamesList) {
            int id = games.getGameId();
            comboBox.getItems().add(id);
        }*/

        HBox hbox = new HBox();
        hbox.getChildren().addAll(currentName, newName);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            tempGame.setGameName(newName.getText());
            window.close();
        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);
        //popupWindow();

        return tempGame;
    }

    public Games updateGameName(List<Games> gamesList) {
        titleName = "Change name";
        featureText = new Label("Select which game you want to change");

        Label explainText1 = new Label("Select game: ");
        Label explainText2 = new Label("Select a new name: ");

        Text currentName = new Text();
        TextField newName = new TextField();

        comboBox = new ComboBox();
        for (Games games : gamesList) {
            comboBox.getItems().add(games);
        }
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            tempGame = (Games) comboBox.getValue();
            tempGame.setGameName(newName.getText());
            window.close();
        });

        HBox overHBox = new HBox(3);
        overHBox.getChildren().addAll(explainText1, comboBox);
        overHBox.setAlignment(Pos.CENTER);

        HBox underHBox = new HBox(3);
        underHBox.getChildren().addAll(explainText2, currentName, newName, submit);
        underHBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, overHBox, underHBox);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindow();
        return tempGame;
    }

    public void assignPlayerToGame(List<Games> gamesList, List<Player> playerList) {
        titleName = "Assign player";
        featureText = new Label("Assign player to a game");

        ComboBox comboBoxGames = new ComboBox();
        for (Games games : gamesList) {
            comboBoxGames.getItems().add(games);
        }

        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playerList) {
            comboBoxPlayer.getItems().add(player);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            errorMessage.setText("");
            tempGame = (Games) comboBoxGames.getValue();
            Player tempPlayer  = (Player) comboBoxPlayer.getValue();
            if (tempPlayer.getGames() == null) {
               gameId = tempGame.getGameId();
               playerId = tempPlayer.getId();
               window.close();
            } else {
                errorMessage.setText("Player is already assigned to a game");
            }
        });
        Button close = new Button("Close");
        close.setOnAction(e -> window.close());

        HBox upperHbox = new HBox(3);
        upperHbox.getChildren().addAll(comboBoxPlayer, comboBoxGames);
        upperHbox.setAlignment(Pos.CENTER);

        HBox lowerHbox = new HBox(3);
        lowerHbox.getChildren().addAll(submit, close);
        lowerHbox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, upperHbox, lowerHbox, errorMessage);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindow();
    }
    public void assignTeamToGame(List<Games> gamesList, List<Teams> teamList) {
        titleName = "Assign team";
        featureText = new Label("Assign team to a game");

        ComboBox comboBoxGames = new ComboBox();
        for (Games games : gamesList) {
            comboBoxGames.getItems().add(games);
        }
        ComboBox comboBoxTeam = new ComboBox<>();
        for (Teams teams : teamList) {
            comboBoxTeam.getItems().add(teams);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e-> {
            errorMessage.setText("");
            tempGame = (Games) comboBoxGames.getValue();
            Teams tempTeams = (Teams) comboBoxTeam.getValue();
            if (tempTeams.getGames() == null) {
                tempTeams.setGames(tempGame);
                gameId = tempGame.getGameId();
                teamId = tempTeams.getId();
                window.close();
            }
            else {
                errorMessage.setText("Team already assigned to a game");
            }
        });
        Button close = new Button("Close");
        close.setOnAction(e-> window.close());

        HBox upperHbox = new HBox(3);
        upperHbox.getChildren().addAll(comboBoxGames,comboBoxTeam);
        upperHbox.setAlignment(Pos.CENTER);

        HBox lowerHbox = new HBox(3);
        lowerHbox.getChildren().addAll(submit,close);
        lowerHbox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,upperHbox,lowerHbox,errorMessage);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindow();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
