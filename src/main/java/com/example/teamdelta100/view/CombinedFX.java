package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CombinedFX extends Application {
    private PlayerMenu pm = new PlayerMenu();
    private TeamFX teamFX = new TeamFX();
    private PersonalFX personalFX = new PersonalFX();
    private MatchTab matchTab = new MatchTab();
    private GamesFX gamesFX = new GamesFX();
    private LogInWindows logInWindows = new LogInWindows(personalFX);
    private Scene tabScene;
    private TabPane tabPane;

    @Override
    public void start(Stage stage) throws Exception {
        teamFX.setPlayerMenu(pm);
        gamesFX.setPlayerMenu(pm);
        gamesFX.setTeamFX(teamFX);

        BorderPane tabLayout = new BorderPane();

        tabPane = new TabPane();

        tabPane.getTabs().add(personalFX.PersonalTab());
        tabPane.getTabs().add(pm.playerTab());
        tabPane.getTabs().add(teamFX.teamTab());
        tabPane.getTabs().add(gamesFX.gameTab());
        tabPane.getTabs().add(matchTab.createMatchTab());

        tabLayout.setCenter(tabPane);

        tabScene = new Scene(tabLayout, 900, 500);
        logInWindows.LogIn(stage,tabScene);

        swapSceneSetup(stage);

    }
    public void swapSceneSetup(Stage stage){
        teamFX.setWindow(stage);
        teamFX.setTabScene(tabScene);
        teamFX.setLogInWindows(logInWindows);
        gamesFX.setWindow(stage);
        gamesFX.setTabScene(tabScene);
        gamesFX.setLogInWindows(logInWindows);
        matchTab.setWindow(stage);
        matchTab.setTabScene(tabScene);
        matchTab.setLogInWindows(logInWindows);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


