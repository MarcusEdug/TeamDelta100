package com.example.teamdelta100;

import com.example.teamdelta100.view.CombinedFX;
import com.example.teamdelta100.view.FX;
import com.example.teamdelta100.view.InformationForm;
import com.example.teamdelta100.view.PlayerMenu;
import com.example.teamdelta100.view.InformationForm;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
       launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        /*FX fx = new FX();
        fx.start(stage);*/

        /*PlayerMenu pm = new PlayerMenu();
        pm.start(stage);*/

        CombinedFX fx = new CombinedFX();
        fx.start(stage);

        /*InformationForm infoForm = new InformationForm();
        infoForm.start(stage);*/

    }
}