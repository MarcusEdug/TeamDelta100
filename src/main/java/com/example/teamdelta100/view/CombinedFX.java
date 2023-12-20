package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
    Klass skapar en Tab stagen som visar upp alla tab som vi har gjort (Gjord av AR,ED,MS, JD)
    Klassen inne håller två metoder
    * Start
    * Ändra Stage från login till tab, med en log out knapp
 */

public class CombinedFX extends Application {
    private PlayerMenu pm = new PlayerMenu();
    private TeamFX teamFX = new TeamFX();
    private PersonalFX personalFX = new PersonalFX();
    private MatchTab matchTab = new MatchTab();
    private GamesFX gamesFX = new GamesFX();
    private LogInWindows logInWindows = new LogInWindows(personalFX);
    private Scene tabScene;
    private TabPane tabPane;

    //Metod: våran start metod som hantera de som vissar på våran tab stage
    @Override
    public void start(Stage stage) throws Exception {
        //Skickar in objekt
        teamFX.setPlayerMenu(pm);
        gamesFX.setPlayerMenu(pm);
        gamesFX.setTeamFX(teamFX);

        //Sätter up en tabLayout
        BorderPane tabLayout = new BorderPane();

        //skapar en TabPane
        tabPane = new TabPane();

        //Lägger in alls tabar i tabPane
        tabPane.getTabs().add(personalFX.PersonalTab());
        tabPane.getTabs().add(pm.playerTab());
        tabPane.getTabs().add(teamFX.teamTab());
        tabPane.getTabs().add(gamesFX.gameTab());
        tabPane.getTabs().add(matchTab.createMatchTab());

        //sätter inte tabPane i layouten
        tabLayout.setCenter(tabPane);

        //skapar tabScene och skickar in den och tabstage i login metod i login objekt
        tabScene = new Scene(tabLayout, 900, 500);
        logInWindows.LogIn(stage,tabScene);
        swapStageSetup(stage);

    }

    //Gör de möjligt att alla obejkt att ha en logout knapp som tar dom till samma ställe
    public void swapStageSetup(Stage stage){
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


