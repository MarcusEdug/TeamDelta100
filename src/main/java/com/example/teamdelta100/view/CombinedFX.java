package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CombinedFX extends Application {
    GamesFX gamesFX = new GamesFX();
    private Stage window;
    private Stage stage;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws Exception {
        //this.stage = stage;
        window = stage;
        stage.setTitle("Piper Games");
        BorderPane bPane = new BorderPane();

        tabPane = new TabPane();
        Tab tab2 = new Tab("Teams");
        Tab tab3 = new Tab("Players");
        Tab tab4 = new Tab("Matches");

        tabPane.getTabs().add(gamesFX.gameTab());
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);

        bPane.setCenter(tabPane);

        Scene scene = new Scene(bPane, 800,500);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public GamesFX getGamesFX() {
        return gamesFX;
    }

    public void setGamesFX(GamesFX gamesFX) {
        this.gamesFX = gamesFX;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
}
