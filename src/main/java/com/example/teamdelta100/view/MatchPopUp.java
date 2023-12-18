package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Hanterar popup fönster och kommunikation med controller.
// Evelina Daun


public class MatchPopUp {
    private MatchController matchController;
    private TeamFX teamFX; // TeamFX objekt för att nå listan med Teams
    private PlayerMenu playerMenu;
    private Match selected; // ÄNDRA

    private Label title = new Label(); //  Label för rubrik

    // Labels för formulär
    private Label playerTeamOne = new Label("Player/Team 1: ");
    private Label playerTeamTwo = new Label("Player/Team 2: ");
    private Label dateLabel = new Label("Match Date: ");
    private Label playedLabel = new Label("Match played or not played: ");
    private Label resultLabelOne = new Label("Reslult Player/Team 1 :");
    private Label resultLabelTwo = new Label("Reslult Player/Team 2: ");
    private Label winnerLabel = new Label("Winner: ");

    private ComboBox<Player> comboBoxPlayerOne = new ComboBox<>();
    private ComboBox<Player> comboBoxPlayerTwo = new ComboBox<>();
    private ComboBox<Teams> comboBoxTeamOne = new ComboBox<>();
    private ComboBox<Teams> comboBoxTeamTwo = new ComboBox<>();


    // Konstruktor
    public MatchPopUp(MatchController matchController, TeamFX teamFX) {
        this.matchController = matchController;
        this.teamFX = teamFX;
        this.playerMenu = new PlayerMenu();
    }


    // Metod: Skapa upp match för både Player & Teams
    public void createMatch(String teamOrPlayer){
        Stage popup = new Stage();
        popup.setMinWidth(450);
        popup.setMinHeight(450);
        changeLabels(teamOrPlayer);

        HBox hboxOne = new HBox();
        HBox hboxtwo = new HBox();

        // Om det är Player eller Teams
        if(teamOrPlayer.equals("player")){
            title.setText("Player vs. Player");
            popup.setTitle("Add Match Player vs. Player");
            hboxOne.getChildren().add(comboBoxPlayerOne);
            hboxtwo.getChildren().add(comboBoxPlayerTwo);

           comboBoxPlayerOne.getItems().clear(); // Tömma listan innan den fylls på nytt
           comboBoxPlayerTwo.getItems().clear();

            for(Player p : playerMenu.playerDatabaseList()){ // Fylla på listorna
                comboBoxPlayerOne.getItems().add(p);
                comboBoxPlayerTwo.getItems().add(p);
            }

        }else{
            title.setText("Team vs. Team");
            popup.setTitle("Add Match Team vs. Team");
            hboxOne.getChildren().add(comboBoxTeamOne);
            hboxtwo.getChildren().add(comboBoxTeamTwo);

            comboBoxTeamOne.getItems().clear(); // Tömma listan innan den fylls på nytt
            comboBoxTeamTwo.getItems().clear();

            for(Teams t : teamFX.teamDatabaseList()){ // Fylla på listorna
                comboBoxTeamOne.getItems().add(t);
                comboBoxTeamTwo.getItems().add(t);
            }
        }

        // Matchens datum
        DatePicker date = new DatePicker();

        // Om matchen är avgjord
        RadioButton played = new RadioButton("Played");
        RadioButton notPlayed = new RadioButton("Not Played");
        ToggleGroup group = new ToggleGroup();
        played.setToggleGroup(group);
        notPlayed.setToggleGroup(group);
        group.selectToggle(notPlayed);

        // Resultat
        TextField resultOne = new TextField();
        TextField resultTwo = new TextField();

        // Vinnare
        TextField winnerField = new TextField();

        // Lägga in i egna HBoxar för att få en egen rad
        HBox rowOne = new HBox();       // Team/Player 1
        HBox rowTwo = new HBox();       // Team/Player 2
        HBox rowThree = new HBox();     // Date
        HBox rowFour = new HBox();      // Played or Not Played
        HBox rowFive = new HBox();      // Result 1
        HBox rowSix = new HBox();       // Result 2
        HBox rowSeven = new HBox();     // Winner

        rowOne.getChildren().addAll(playerTeamOne, hboxOne);
        rowTwo.getChildren().addAll(playerTeamTwo, hboxtwo);
        rowThree.getChildren().addAll(dateLabel, date);
        rowFour.getChildren().addAll(playedLabel, played, notPlayed);
        rowFive.getChildren().addAll(resultLabelOne, resultOne);
        rowSix.getChildren().addAll(resultLabelTwo, resultTwo);
        rowSeven.getChildren().addAll(winnerLabel, winnerField);

        // Knappar
        Button createButton = new Button("Create Match");
        Button cancel = createCancelButton(popup);
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createButton, cancel);
        buttonBox.setSpacing(10);

        // Varningstext
        Text warningText = new Text();

        createButton.setOnAction(event -> {
            // KOLLA ATT SPELARE/LAG ÄR VALT!

            String nameOne = "";
            String nameTwo = "";

             if(date.getValue() == null){
                warningText.setText("Pick a date and try again! ");
             }else{
                 Player playerOne = new Player();
                 Player playerTwo = new Player();
                 Teams teamOne = new Teams();
                 Teams teamTwo = new Teams();

                if(teamOrPlayer.equals("player")){
                    playerOne = comboBoxPlayerOne.getSelectionModel().getSelectedItem();
                    playerTwo = comboBoxPlayerTwo.getSelectionModel().getSelectedItem();
                    nameOne = playerOne.getPlayerName();
                    nameTwo = playerTwo.getPlayerName();

                }else if (teamOrPlayer.equals("team")){
                    teamOne = comboBoxTeamOne.getSelectionModel().getSelectedItem();
                    teamTwo = comboBoxTeamTwo.getSelectionModel().getSelectedItem();

                    nameOne = teamOne.getName();
                    nameTwo = teamTwo.getName();

                    // Lägga in att matchnamnet syns för båda teams
                    String matchName = nameOne + " vs. " + nameTwo;
                    // teamOne.setMatchName(matchName);
                    // teamTwo.setMatchName(matchName);
                }

                LocalDate tempDate = date.getValue();
                String tempPlayed = ((RadioButton) group.getSelectedToggle()).getText();

                 // Om resultatfälten är antingen tomma eller innehåller siffror
                if(testTextField(resultOne.getText()) && testTextField(resultTwo.getText())){
                    warningText.setText("");

                    int tempResultOne = 0;
                    int tempResultTwo = 0;

                    if(!resultOne.getText().isEmpty()){
                        tempResultOne = Integer.parseInt(resultOne.getText());
                    }

                    if(!resultTwo.getText().isEmpty()){
                        tempResultTwo = Integer.parseInt(resultTwo.getText());
                    }

                    String tempWinner = winnerField.getText();
                    Match tempMatch = new Match (teamOrPlayer, nameOne, nameTwo, tempDate, tempPlayed, tempResultOne, tempResultTwo, tempWinner);
                    matchController.addMatch(tempMatch);

                    if(teamOrPlayer.equals("player")){
                        matchController.addPlayerToMatch(tempMatch.getMatchId(), playerOne.getId());
                        matchController.addPlayerToMatch(tempMatch.getMatchId(), playerTwo.getId());
                    }else{
                        matchController.addTeamToMatch(tempMatch.getMatchId(), teamOne.getId());
                        matchController.addTeamToMatch(tempMatch.getMatchId(), teamTwo.getId());
                    }

                
                    popup.close();
                }else{
                    warningText.setText("Put numbers in the result field and try again!");
                }
             }
        });

        VBox box = vboxFormat();
        box.getChildren().addAll( title, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, warningText, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att lägga till ett resultat för en match
    public void addResult(){
        Stage popup = new Stage();
        popup.setMinWidth(450);
        popup.setMinHeight(450);
        popup.setTitle("Add Result");

        RadioButton played = new RadioButton("Played");
        RadioButton notPlayed = new RadioButton("Not Played");
        ToggleGroup group = new ToggleGroup();
        played.setToggleGroup(group);
        notPlayed.setToggleGroup(group);
        group.selectToggle(notPlayed);

        resultLabelOne.setText("Result 1: ");
        resultLabelTwo.setText("Result 2: ");

        // Välja match att lägga till resultat på
        ListView<Match> listView = createMatchList();

        // Knapp välja match att lägga till resultat på
        Button chooseButton = new Button("Choose Match");

        Text chooseWarning = new Text();
        chooseWarning.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField resultOne = new TextField();    // Resultat player/team 1
        TextField resultTwo = new TextField();    // Resultat player/team 2
        TextField winnerField = new TextField();  // Vinnare - BYTA TILL LISTA MED 2

        // Vad som ska hända när man trycker på knappen
        chooseButton.setOnAction(event ->  {
            if(!listView.getSelectionModel().isEmpty()){
                chooseWarning.setText("");
                selected = listView.getSelectionModel().getSelectedItem();
                changeLabels(selected.getPlayerOrTeam());

                if(selected.getPlayed().equals("Played")){
                    played.setSelected(true);
                }else{
                    notPlayed.setSelected(true);
                }

                resultOne.setText(String.valueOf(selected.getResultOne()));
                resultTwo.setText(String.valueOf(selected.getResultTwo()));
                winnerField.setText(selected.getWinner());
            }else{
                chooseWarning.setText(" Choose a match to use. ");
            }
        });

        HBox boxZero = new HBox();
        HBox boxOne = new HBox();
        HBox boxTwo = new HBox();
        HBox boxThree = new HBox();
        boxZero.setSpacing(10);
        boxOne.setSpacing(10);
        boxTwo.setSpacing(10);
        boxThree.setSpacing(10);
        boxZero.getChildren().addAll(playedLabel, played, notPlayed);
        boxOne.getChildren().addAll(resultLabelOne, resultOne);
        boxTwo.getChildren().addAll(resultLabelTwo, resultTwo);
        boxThree.getChildren().addAll(winnerLabel, winnerField);

        Text warningText = new Text();
        warningText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Knapp
        Button addButton = new Button("Add Result");
        Button cancel = createCancelButton(popup);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(addButton, cancel);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        addButton.setOnAction(event -> {
            if(resultOne.getText().isEmpty() ||  resultTwo.getText().isEmpty() || winnerField.getText().isEmpty()){
                warningText.setText(" Fill in result form and try again! ");
            }else{
                selected.setPlayed(((RadioButton) group.getSelectedToggle()).getText());
                selected.setResultOne(Integer.parseInt(resultOne.getText()));
                selected.setResultTwo(Integer.parseInt(resultOne.getText()));
                selected.setWinner(winnerField.getText());
                matchController.updateMatchObject(selected);
                popup.close();
            }
        });

        VBox box = vboxFormat();
        box.getChildren().addAll(listView, chooseWarning, chooseButton, boxZero, boxOne, boxTwo, boxThree, warningText, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att ta bort en match
    public void deleteMatch(){
        Stage popup = new Stage();
        popup.setWidth(450);
        popup.setHeight(450);
        popup.setTitle("Remove Match");
        title.setText("Choose match to delete: ");

        // Skapa upp en lista att visa för användaren
        ListView<Match> listView = createMatchList();

        // Knappar
        Button removeButton = new Button("Delete Match");
        Button cancel = createCancelButton(popup);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(removeButton, cancel);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        // Vad som händer vid knapptryck
        removeButton.setOnAction(event -> {
            Match selectedMatch = listView.getSelectionModel().getSelectedItem();
            matchController.deleteMatchObject(selectedMatch);
            deletedPopup(popup, selectedMatch);
        });

        VBox box = vboxFormat();
        box.getChildren().addAll(listView, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }

    // Metod: Popup deleted match objekt
    public void deletedPopup(Stage popup, Match selectedMatch){
        Stage popupDelete = new Stage();
        popupDelete.setWidth(400);
        popupDelete.setHeight(200);
        popupDelete.setTitle("Remove Match");

        Text text = new Text("You have deleted: \n" + selectedMatch.toString());
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Button deleted = new Button(" Ok ");
        deleted.setOnAction(event -> {
            popupDelete.close();
            popup.close();
        });

        VBox box = vboxFormat();
        box.getChildren().addAll(text, deleted);
        Scene popupscene = new Scene(box);
        popupDelete.setScene(popupscene);
        popupDelete.showAndWait();
    }


    // Metod: Popup för att uppdatera match
    public void matchUpdate(){
        Stage popup = new Stage();
        popup.setWidth(600);
        popup.setHeight(550);
        popup.setTitle("Update Match");

        // Skapa upp en lista att visa för användaren
        ListView<Match> listView = createMatchList();

        // Välj match att ändra
        Button chooseButton = new Button("Choose Match");
        Button cancel = createCancelButton(popup);
        HBox buttonBoxOne = new HBox();
        buttonBoxOne.getChildren().addAll(chooseButton, cancel);
        buttonBoxOne.setSpacing(10);
        buttonBoxOne.setAlignment(Pos.CENTER);

        TextField resultOne = new TextField();
        TextField resultTwo = new TextField();
        TextField winnerField = new TextField();
        DatePicker date = new DatePicker();
        RadioButton played = new RadioButton("Played");
        RadioButton notPlayed = new RadioButton("Not Played");
        ToggleGroup group = new ToggleGroup();
        played.setToggleGroup(group);
        notPlayed.setToggleGroup(group);

        // Lägga in i egna HBoxar för att få en egen rad
        HBox rowOne = new HBox();       // Team/Player 1
        HBox rowTwo = new HBox();       // Team/Player 2
        HBox tempOne = new HBox();      // Används för att ändra innehåll i lista ett
        HBox tempTwo = new HBox();      // Används för att ändra innehåll i lista två
        HBox rowThree = new HBox();     // Date
        HBox rowFour = new HBox();      // Played or Not Played
        HBox rowFive = new HBox();      // Result 1
        HBox rowSix = new HBox();       // Result 2
        HBox rowSeven = new HBox();     // Winner

        Text warningText = new Text();

        // Välja match att uppdatera
        chooseButton.setOnAction( event -> {

            selected = listView.getSelectionModel().getSelectedItem();
            changeLabels(selected.getPlayerOrTeam());

            if(selected.getPlayerOrTeam().equals("team")){
                title.setText("Team vs. Team");
                comboBoxTeamOne.getItems().clear();
                comboBoxTeamTwo.getItems().clear();

                for(Teams t : teamFX.teamDatabaseList()){
                    comboBoxTeamOne.getItems().add(t);
                    comboBoxTeamTwo.getItems().add(t);
                }
                tempOne.getChildren().clear();
                tempTwo.getChildren().clear();
                tempOne.getChildren().add(comboBoxTeamOne);
                tempTwo.getChildren().add(comboBoxTeamTwo);
                /*
                comboBoxTeamOne.getSelectionModel().select(selected.getTeamsList().get(0).getId());
                comboBoxTeamTwo.getSelectionModel().select(selected.getTeamsList().get(1).getId());

                 */
            }else{
                title.setText("Player vs. Player");
                comboBoxPlayerOne.getItems().clear();
                comboBoxPlayerTwo.getItems().clear();

                for(Player p : playerMenu.playerDatabaseList()){
                    comboBoxPlayerOne.getItems().add(p);
                    comboBoxPlayerTwo.getItems().add(p);
                }

                tempOne.getChildren().clear();
                tempTwo.getChildren().clear();
                tempOne.getChildren().add(comboBoxPlayerOne);
                tempTwo.getChildren().add(comboBoxPlayerTwo);

                /*
                comboBoxPlayerOne.getSelectionModel().select(selected.getPlayerList().get(0).getId());
                comboBoxPlayerTwo.getSelectionModel().select(selected.getPlayerList().get(1).getId());

                 */
            }

            date.setValue(selected.getMatchDate());

            if(selected.getPlayed().equals("Played")){
                played.setSelected(true);
            }else{
                notPlayed.setSelected(true);
            }

            resultOne.setText(String.valueOf(selected.getResultOne()));
            resultTwo.setText(String.valueOf(selected.getResultTwo()));
            winnerField.setText(selected.getWinner());
        });

        // Knapp: Uppdatera vald match
        Button updateButton = new Button("Update Match");
        updateButton.setOnAction(event -> {

            // Om det är en player eller teams match som är vald
            if(selected.getPlayerOrTeam().equals("player")){
                Player playerOne = comboBoxPlayerOne.getSelectionModel().getSelectedItem(); // Hämta valt Player objekt
                Player playerTwo = comboBoxPlayerTwo.getSelectionModel().getSelectedItem();

                // Ta bort match objekt ifrån teams
                /*
                selected.getTeamsList().get(0).setMatch(null);
                selected.getTeamsList().get(1).setMatch(null);
                 */
                selected.setPlayerTeamNameOne(playerOne.getPlayerName());
                selected.setPlayerTeamNameTwo(playerTwo.getPlayerName());

                List<Player> empty = new ArrayList<>();
                selected.setPlayerList(empty);
                selected.addPlayer(playerOne);
                selected.addPlayer(playerTwo);
            }else{
                Teams teamOne = comboBoxTeamOne.getSelectionModel().getSelectedItem(); // Hämta valt Teams objekt
                Teams teamTwo = comboBoxTeamTwo.getSelectionModel().getSelectedItem();

                // Ta bort match objekt från players
                /*
                selected.getPlayerList().get(0).setMatch(null);
                selected.getPlayerList().get(1).setMatch(null);

                 */

                selected.setPlayerTeamNameOne(teamOne.getName());
                selected.setPlayerTeamNameTwo(teamTwo.getName());

                List<Teams> empty = new ArrayList<>();
                selected.setTeamsList(empty);
                selected.addTeams(teamOne);
                selected.addTeams(teamTwo);
            }

            RadioButton tempPlayedSelected = (RadioButton) group.getSelectedToggle();
            selected.setPlayed(tempPlayedSelected.getText());

            selected.setMatchDate(date.getValue());

            // Kontrollera att resultaten är i sifferformat
            // LÄGGA IN SAMMA SOM CREATE MATCH KONTROLL OM ""
            if(testTextField(resultOne.getText()) && testTextField(resultTwo.getText())){
                selected.setResultOne(Integer.parseInt(resultOne.getText()));
                selected.setResultTwo(Integer.parseInt(resultTwo.getText()));
                selected.setWinner(winnerField.getText());

                matchController.updateMatchObject(selected);
                popup.close();
            }else {
                warningText.setText("Put numbers in the result field and try again!");
            }
        });

        // Lägga till formuläret i egna rader genom HBoxar
        rowOne.getChildren().addAll(playerTeamOne, tempOne);
        rowTwo.getChildren().addAll(playerTeamTwo, tempTwo);
        rowThree.getChildren().addAll(dateLabel, date);
        rowFour.getChildren().addAll(playedLabel, played, notPlayed);
        rowFive.getChildren().addAll(resultLabelOne, resultOne);
        rowSix.getChildren().addAll(resultLabelTwo, resultTwo);
        rowSeven.getChildren().addAll(winnerLabel, winnerField);

        VBox box = vboxFormat(); // VBox för hela popuprutan
        VBox formBox = new VBox(); // VBox för formuläret för att uppdatera
        formBox.setSpacing(15);

        // Lägga till innehållet för formuläret samt i den stora VBoxen
        formBox.getChildren().addAll(title, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, warningText, updateButton);
        box.getChildren().addAll(listView, buttonBoxOne, formBox);

        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Lägga till en avbryt knapp för att hoppa ur popupfönstret utan att behöva göra en ändring
    public Button createCancelButton(Stage popup){
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            popup.close();
        });
        return cancelButton;
    }


    // Metod: Förändra label texter efter om det är Team eller Player
    public void changeLabels(String inString){
        if(inString.equals("player")){
            playerTeamOne.setText("Player 1: ");
            playerTeamTwo.setText("Player 2: ");
            resultLabelOne.setText("Result Player 1: ");
            resultLabelTwo.setText("Result Player 2: ");
        }else{
            playerTeamOne.setText("Team 1: ");
            playerTeamTwo.setText("Team 2: ");
            resultLabelOne.setText("Result Team 1: ");
            resultLabelTwo.setText("Result Team 2: ");
        }
    }


    // Metod: Kontrollera om innehållet i en TextField är numeriskt
    // Return: True (Om strängen innehåller siffror) , False (Om strängen inte innehåller siffror)
    public boolean testTextField(String inString){

        if(inString.equals("")){ // Ifall användaren inte vill mata in något vid skapandet av match
            return true;
        }else {
            try{
                int testInt = Integer.parseInt(inString);
                return true;
            }catch(NumberFormatException ex){
                return false;
            }
        }
    }


    // Metod: Skapa upp en ListView med innehållet från Match listan
    public ListView<Match> createMatchList(){
        ListView<Match> listView = new ListView<Match>();
        List<Match> matchList = matchController.getAllMatchObjects();
        ObservableList<Match> obList = FXCollections.observableArrayList(matchList);
        listView.setItems(obList);
        listView.setPrefHeight(230);
        listView.setPrefWidth(230);


        listView.setCellFactory(param -> new ListCell<Match>(){
            protected void updateItem(Match m, boolean empty){
                super.updateItem(m, empty);
                if(empty || m == null){
                    setText(null);
                } else {
                    setText(m.toString());
                }
            }
        });
        return listView;
    }


    // Metod: Utseende för VBox
    public VBox vboxFormat(){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        vbox.setPadding(new Insets(20));
        return vbox;
    }
}