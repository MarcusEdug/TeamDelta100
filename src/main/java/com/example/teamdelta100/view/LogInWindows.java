package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Personal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
    Klassen skapar en Login Stage
    Klassen har två metoder:
    * Skapa LogIn stagen
    * Skapa en ListView
 */

public class LogInWindows {
    private PersonalFX personalFX;
    private String title;
    private Text error = new Text();
    private Personal selectPersonal;

    //Konduktor som tar in peronsal
    public LogInWindows(PersonalFX personalFX) {
        this.personalFX = personalFX;
    }

    //Metod:Skapar en Login/start Stage som vissar när man starta programet
    public void LogIn (Stage stage, Scene tabScene){
        //skapar stagen
        Stage logInStage = new Stage();
        logInStage.setTitle("Program Delta");

        // skapar en Layout
        BorderPane Layout = new BorderPane();
        Layout.setPadding(new Insets(10,10,10,10));
        Layout.setMinHeight(48);
        Layout.setMinWidth(120);

        //Skapar en Vbox för att få allt att ligga på rad
        VBox vbox = new VBox(5);
        vbox.setMinHeight(24);
        vbox.setMinWidth(60);

        //skapar en listView
        ListView personalList = personalUser();

        //skapar login knappen som tar värdet på de användar objekt som användare har valt i listview och så öppnar den upp tab stagen
        Button logIn = new Button("Log in");
        logIn.setOnAction(e-> {
            error.setText("");
            selectPersonal = (Personal) personalList.getSelectionModel().getSelectedItem(); // tar in objekt som har valts i liteview

            if (selectPersonal != null) {                   // kollar så något har valts
                title = selectPersonal.getPerName();
                stage.setTitle("Program Delta " + title);
                stage.setScene(tabScene);                   //byter stage
                stage.show();                               //öppnar de nya tabstage
                logInStage.close();                         //stänger log in stage
            }
            else {
                error.setText("Select a user!");
            }
        });

        //lägger in listeview, knappen och error text i Vbox
        vbox.getChildren().addAll(personalList,logIn,error);
        vbox.setAlignment(Pos.CENTER);

        Layout.setCenter(vbox); // lägger in allt i layouten

        //öppnar login stagen
        Scene returnScene = new Scene(Layout);
        logInStage.setScene(returnScene);
        logInStage.show();

    }

    //Metod: Skapar en ListView
    public ListView personalUser(){
        ObservableList<Personal> personal = FXCollections.observableList(personalFX.personalDatabaseList());
        ListView<Personal> listView = new ListView<>(personal);
        listView.setPrefHeight(personal.size() * 24);


        return listView;
    }

}
