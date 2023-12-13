package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CombinedFX extends Application {

    private Stage stage;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Main");
        BorderPane bPane = new BorderPane();

        tabPane = new TabPane();
        GamesFX fx = new GamesFX();
        Tab tab2 = new Tab("Games");
        Tab tab3 = new Tab("Players");
        Tab tab4 = new Tab("Matches");

        //tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(fx.gameTab());
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
}
