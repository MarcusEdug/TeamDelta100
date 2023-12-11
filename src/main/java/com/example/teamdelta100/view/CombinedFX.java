package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CombinedFX extends Application {
    FX fx = new FX();

    private Stage stage;
    private TabPane tabPane;
    @Override
    public void start(Stage stage) throws Exception {
    stage.setTitle("test");
        BorderPane root = new BorderPane();

        tabPane = new TabPane();
        Tab tab2 = new Tab("Game");
        Tab tab3 = new Tab("Players");
        Tab tab4 = new Tab("Matchs");


        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(fx.teamTab());
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab4);


        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800,500);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
