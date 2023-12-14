package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CombinedFX extends Application {
    TeamFX teamFX = new TeamFX();
    MatchTab matchTab = new MatchTab(teamFX);
    GamesFX gamesFX = new GamesFX();
    private Stage window;
    private Stage stage;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws Exception {


        window = stage;
        stage.setTitle("test");
        BorderPane root = new BorderPane();

        tabPane = new TabPane();


        //tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(teamFX.teamTab());
        tabPane.getTabs().add(gamesFX.gameTab());
        tabPane.getTabs().add(matchTab.createAndReturnTabMatch());


        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800,500);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
