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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/*
Klass: Skapar och popups som interagerar och hämtar information från användaren
Gör det möjligt att visa, lägga till, ta bort, uppdatera
-objekt från games, player och teams
 */

public class GamePopup {
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
    private Games tempGame;
    private Text errorMessage = new Text();
    private Label boxText1;
    private Label boxText2;
    private Player player;
    private Teams teams;

    //Metod: Skapar layout för popup-fönstren
    public void popupWindow() {
        window = new Stage();
        window.setTitle(titleName);
        window.setMinWidth(200);
        window.setScene(scene);
        window.showAndWait();
    }

    //Tar emot en string från användaren och returnerar den
    public String addGame() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add game");
        window.setMinWidth(200);

        //Skapar textruta att skriva i namnet på spelet i
        TextField userText = new TextField();

        //Skapar knapp för att spara det inmatade värdet
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            userStringInput = userText.getText();
            System.out.println(userStringInput);
            window.close();
        });

        //Lägger till textrutan och knapparna
        VBox vBox = new VBox();
        vBox.getChildren().addAll(userText, submit);
        vBox.setAlignment(Pos.CENTER);

        //Visar upp fönstret
        scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

        return userStringInput;
    }

    //Tar fram ett id från games-objekt och skickar tillbaka
    public int deleteGame(List<Games> gamesList) {
        //Information till användaren
        titleName = "Delete game";
        featureText = new Label("Choose a game-id you want to delete");

        //comboBox med id på de inlagda spelen
        comboBox = new ComboBox<>();
        for (Games games : gamesList) {
            int id = games.getGameId();
            comboBox.getItems().add(id);
        }

        //Knapp för att spara det Id som användaren valt och returnerar
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            userIntInput = (int) comboBox.getValue();
            window.close();
        });

        //Lägger till textrutan, combobox och knappen
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindow();

        return userIntInput;
    }

    //Tar fram ett games-objekt och skapar möjlighet att uppdatera, för att sedan returnera
    public Games updateGameName(List<Games> gamesList) {
        titleName = "Change name";
        featureText = new Label("Select which game you want to change");

        Label explainText1 = new Label("Select game: ");
        Label explainText2 = new Label("Select a new name: ");

        //Text på nuvarande namn och det man vill uppdatera till
        Text currentName = new Text();
        TextField newName = new TextField();

        //combobox för att låta användaren välja bland de objekt som finns i databas
        comboBox = new ComboBox();
        for (Games games : gamesList) {
            comboBox.getItems().add(games);
        }

        //Skapar ändringar användaren valt
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            tempGame = (Games) comboBox.getValue(); //Hämtar värdet
            tempGame.setGameName(newName.getText()); //Justerar värdet
            window.close();
        });

        //Skapar layout för box, textrutor samt knappar
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

    //Hämtar player-objekt samt games-objekt och lägger ihop de med varandra
    public void assignPlayerToGame(List<Games> gamesList, List<Player> playerList) {
        titleName = "Assign player";
        featureText = new Label("Assign player to a game");

        //Användaren får välja vilket spel de vill lägga till player i
        ComboBox comboBoxGames = new ComboBox();
        for (Games games : gamesList) {
            comboBoxGames.getItems().add(games);
        }

        //Väljer vilken spelar som ska läggas till
        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playerList) {
            comboBoxPlayer.getItems().add(player);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            errorMessage.setText("");
            //Hämtar värdet på spelet som användaren valde
            tempGame = (Games) comboBoxGames.getValue();
            //Hämtar värdet på player som användaren valde
            Player tempPlayer  = (Player) comboBoxPlayer.getValue();
            if (tempPlayer.getGames() == null) { //Kollar så att player inte är tillagt i annat spel
                gameId = tempGame.getGameId();
                playerId = tempPlayer.getId();
                window.close();
            } else {
                errorMessage.setText("Player is already assigned to a game");
            }
        });
        Button close = new Button("Close");
        close.setOnAction(e -> window.close());

        //Skapar layout för boxar, knappar samt textrutor
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

    //Tar fram team-objekt samt games-objekt och lägger ihop de med varandra
    public void assignTeamToGame(List<Games> gamesList, List<Teams> teamList) {
        titleName = "Assign team";
        featureText = new Label("Assign team to a game");

        //Låter användaren välja vilket spel som laget ska läggas till i
        ComboBox comboBoxGames = new ComboBox();
        for (Games games : gamesList) {
            comboBoxGames.getItems().add(games);
        }
        //Låter användaren välja vilket lag som ska läggas till
        ComboBox comboBoxTeam = new ComboBox<>();
        for (Teams teams : teamList) {
            comboBoxTeam.getItems().add(teams);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e-> {
            errorMessage.setText("");
            //Tar fram objekten som användaren valt
            tempGame = (Games) comboBoxGames.getValue();
            Teams tempTeams = (Teams) comboBoxTeam.getValue();
            if (tempTeams.getGames() == null) { //Kollar så att inte laget är tillagt i ett spel

                //Lägger till värden med hjälp av id från objekten
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

        //Skapar layout med boxar, knappar samt textrutor
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

    //Tar fram ett player-objekt som är kopplade till games
    //-för att sedan koppla de ifrån varandra
    public void removePlayerFromGame (List<Player> playerList) {
        titleName = "Remove player";
        featureText = new Label("Remove a player from game");

        boxText1 = new Label("Select player: ");
        boxText2 = new Label("Assigned to:  ");

        //Låter användaren välja vilken spelar som ska tas bort
        ComboBox playerComboBox = new ComboBox();
        for (Player player : playerList) {
            if (player.getGames() != null) {
                playerComboBox.getItems().add(player);
            }
        }
        Text gameName = new Text();
        gameName.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        //Hämtar värdet från användaren och sparar valt värde tillfälligt
        playerComboBox.setOnAction(e-> {
            player = (Player) playerComboBox.getValue();
            gameName.setText("Game-ID " + gameId);
            tempGame = player.getGames();
        });

        HBox upperHbox = new HBox(8);
        upperHbox.getChildren().addAll(boxText1,playerComboBox);
        upperHbox.setAlignment(Pos.CENTER);

        HBox lowerHbox = new HBox(8);
        lowerHbox.getChildren().addAll(boxText2,gameName);
        lowerHbox.setAlignment(Pos.CENTER);

        Button submit = new Button("Submit");
        submit.setOnAction(e-> {
            //När användaren trycker på knappen
            //Kontrolleras om något är valt
            //Id hämtas och vald spelare tas bort
            errorMessage.setText("");
            if (playerComboBox.getValue() != null) {
                errorMessage.setText("");
                gameId = tempGame.getGameId();
                playerId = player.getId();
                window.close();
            } else {
                errorMessage.setText("Chose a player");
            }
        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText,upperHbox,lowerHbox,submit,errorMessage);

        scene = new Scene(vBox);

        window.setScene(scene);
        window.showAndWait();
    }

    //Tar fram ett teams-objekt som är kopplade till games
    //-för att sedan koppla de ifrån varandra
    public void removeTeamFromGame (List<Teams> teamsList) {
        titleName = "Remove team";
        featureText = new Label("Remove team from game");

        boxText1 = new Label("Select team");
        boxText2 = new Label("Assigned to: ");

        //Låter användaren välja vilket lag som ska tas bort
        ComboBox teamComboBox = new ComboBox();
        for (Teams teams : teamsList) {
            if (teams.getGames() != null) {
                teamComboBox.getItems().add(teams);
            }
        }
        Text gameName = new Text();
        gameName.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        //Hämtar värdet och sparar det tillfälligt
        teamComboBox.setOnAction(e-> {
            teams = (Teams) teamComboBox.getValue();
            gameName.setText("Game-ID: " + gameId);
            tempGame = teams.getGames();
        });

        HBox upperHbox = new HBox(8);
        upperHbox.getChildren().addAll(boxText1, teamComboBox);
        upperHbox.setAlignment(Pos.CENTER);

        HBox lowerHbox = new HBox(2);
        lowerHbox.getChildren().addAll(boxText2,gameName);
        lowerHbox.setAlignment(Pos.CENTER);

        Button submit = new Button("Submit");
        submit.setOnAction(e-> {
            //När användaren trycker på knappen
            //Kontrolleras om något är valt
            //Id hämtas och valt lag tas bort
            errorMessage.setText("");
            if (teamComboBox.getValue() != null) {
                errorMessage.setText("");
                gameId = tempGame.getGameId();
                teamId = teams.getId();
                window.close();
            } else {
                errorMessage.setText("Chose a team");
            }
        });

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(featureText,upperHbox,lowerHbox,submit,errorMessage);
        vbox.setAlignment(Pos.CENTER);

        scene = new Scene(vbox);

        window.setScene(scene);
        window.showAndWait();
    }

    //Setters & Gettes för id
    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getTeamId() {
        return teamId;
    }

}
