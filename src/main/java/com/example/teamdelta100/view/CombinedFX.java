package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CombinedFX extends Application {

    PlayerMenu pm = new PlayerMenu();
    TeamFX teamFX = new TeamFX();
    MatchTab matchTab = new MatchTab(teamFX);
    GamesFX gamesFX = new GamesFX();
    LogInWindows logInWindows = new LogInWindows(pm);
    private Scene loginScene;
    private Scene tabScene;
    private TabPane tabPane;
    Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        teamFX.setPlayerMenu(pm);
        gamesFX.setPlayerMenu(pm);
        pm.createPlayer();


        stage.setTitle("test");
        BorderPane tabLayout = new BorderPane();

        window = stage;
        tabPane = new TabPane();

        tabPane.getTabs().add(pm.playerTab());
        tabPane.getTabs().add(teamFX.teamTab());
        tabPane.getTabs().add(gamesFX.gameTab());
        tabPane.getTabs().add(matchTab.createAndReturnTabMatch());



        tabLayout.setCenter(tabPane);

        tabScene = new Scene(tabLayout, 800, 500);

        loginScene = logInWindows.LogIn(stage,tabScene);

        swapSceneSetup(stage);
        stage.setScene(tabScene);
        stage.show();

    }

    public void swapSceneSetup(Stage stage){
        teamFX.setWindow(stage);
        teamFX.setLoginScrene(loginScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

