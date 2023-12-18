package com.example.teamdelta100.view.Games;

import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.entities.Games;
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
    private Label featureText;
    int id;
    Games tempGame;

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
}
