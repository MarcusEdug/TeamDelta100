package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Hantera popup fönster och kommunikation med controller mot databasen
// Evelina Daun


public class MatchPopUp {
    private MatchController matchController;

    // Label för rubrik
    private Label title = new Label();

    // Labels för formulär
    private Label playerTeamOne = new Label("Player/Team 1: ");
    private Label playerTeamTwo = new Label("Player/Team 2: ");
    private Label dateLabel = new Label("Match Date: ");
    private Label playedNotPlayed = new Label("Match played or not played: ");
    private Label resultLabelOne = new Label("Reslult Player/Team 1 :");
    private Label resultLabelTwo = new Label("Reslult Player/Team 2: ");
    private Label winnerLabel = new Label("Winner: ");

    private Match selected; // ÄNDRA


    // Konstruktor
    public MatchPopUp(MatchController matchController ) {
        this.matchController = matchController;
    }


    // Metod: Skapa upp match för både Player & Teams
    public void createMatch(String teamOrPlayer){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setMinWidth(450);
        popup.setMinHeight(450);

        changeLabels(teamOrPlayer);

        HBox comboBoxOne = new HBox();
        HBox comboBoxTwo = new HBox();
        ComboBox<Player> comboBoxOnePlayer = new ComboBox<>();
        ComboBox<Player> comboBoxTwoPlayer = new ComboBox<>();
        ComboBox<Teams> comboBoxOneTeam = new ComboBox<>();
        ComboBox<Teams> comboBoxTwoTeam = new ComboBox<>();

        // Om det är Player eller Teams
        if(teamOrPlayer.equals("player")){
            title.setText("Player vs. Player");
            popup.setTitle("Add Match Player vs. Player");
            comboBoxOne.getChildren().add(comboBoxOnePlayer);
            comboBoxTwo.getChildren().add(comboBoxTwoPlayer);

            List<Player> listPlayerOne = new ArrayList<>();
            for(Player p : listPlayerOne){
                // Player player = tempList.getName();
                //comboBoxOne.getItems().add(player);
            }

            List<Player> listPlayerTwo = new ArrayList<>();
            for(Player p : listPlayerTwo){
                // Player player = tempList.getName();
                //comboBoxOne.getItems().add(player);
            }
        }else{
            title.setText("Team vs. Team");
            popup.setTitle("Add Match Team vs. Team");
            comboBoxOne.getChildren().add(comboBoxOneTeam);
            comboBoxTwo.getChildren().add(comboBoxTwoTeam);

            // Team 1 & 2
            List<Teams> listPlayerOne = new ArrayList<>();
            for(Teams t : listPlayerOne){
                // Player player = tempList.getName();
                //comboBoxOne.getItems().add(player);
            }

            List<Teams> listPlayerTwo = new ArrayList<>();
            for(Teams t : listPlayerTwo){
                // Player player = tempList.getName();
                //comboBoxOne.getItems().add(player);
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

        rowOne.getChildren().addAll(playerTeamOne, comboBoxOne);
        rowTwo.getChildren().addAll(playerTeamTwo, comboBoxTwo);
        rowThree.getChildren().addAll(dateLabel, date);
        rowFour.getChildren().addAll(playedNotPlayed, played, notPlayed);
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
        Text warningText = new Text(); // Resultat, Datum, Played/Not Played

        createButton.setOnAction(event -> {
            // Kolla att spelare el lag är valt

             if(date.getValue() == null){
                warningText.setText("Pick a date and try again! ");
            }else{

                int idOne = 0;
                int idTwo = 0;
                String nameOne = "";
                String nameTwo = "";

                if(teamOrPlayer.equals("player")){
                    Player playerOne = comboBoxOnePlayer.getSelectionModel().getSelectedItem();
                    Player playerTwo = comboBoxTwoPlayer.getSelectionModel().getSelectedItem();
                    // idOne = 0; // playerOne.getId();
                    // idTwo; // playerTwo.getId();
                }else if (teamOrPlayer.equals("team")){
                    Teams teamOne = comboBoxOneTeam.getSelectionModel().getSelectedItem();
                    Teams teamTwo = comboBoxTwoTeam.getSelectionModel().getSelectedItem();
                    // idOne = teamOne.getId();
                    // idTwo = teamTwo.getId();
                }


                LocalDate tempDate = date.getValue();
                RadioButton tempPlayedSelected = (RadioButton) group.getSelectedToggle();
                String tempPlayed = tempPlayedSelected.getText();

                boolean testResultOne = testTextField(resultOne.getText());
                boolean testResultTwo = testTextField(resultTwo.getText());

                if(testResultOne && testResultTwo){
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

                    matchController.addMatch(new Match(teamOrPlayer, idOne, idTwo, nameOne, nameTwo, tempDate, tempPlayed, tempResultOne, tempResultTwo, tempWinner));
                    popup.close();
                }else{
                    // Lägga till en text
                    warningText.setText("Put numbers in the result field and try again!");
                }
            }


        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(15);
        box.getChildren().addAll( title, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, warningText, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att lägga till ett resultat för en match
    public void addResult(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setMinWidth(450);
        popup.setMinHeight(450);
        popup.setTitle("Add Result");

        resultLabelOne.setText("Result 1: ");
        resultLabelTwo.setText("Result 2: ");

        // Välja match att lägga till resultat på
        ListView<Match> listView = createMatchList();

        // Knapp välja match att lägga till resultat på
        Button chooseButton = new Button("Choose Match");

        Text chooseWarning = new Text();
        chooseWarning.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Vad som ska hända när man trycker på knappen
        chooseButton.setOnAction(event ->  {
            if(!listView.getSelectionModel().isEmpty()){
                chooseWarning.setText("");
                selected = listView.getSelectionModel().getSelectedItem();
                changeLabels(selected.getPlayerOrTeam());
            }else{
                chooseWarning.setText(" Choose a match to use. ");
            }
        });

        // Nya resultatet
        TextField resultOne = new TextField();    // Resultat player/team 1
        TextField resultTwo = new TextField();    // Resultat player/team 2
        TextField winnerField = new TextField();  // Vinnare - BYTA TILL LISTA MED 2

        HBox boxOne = new HBox();
        HBox boxTwo = new HBox();
        HBox boxThree = new HBox();
        boxOne.setSpacing(10);
        boxTwo.setSpacing(10);
        boxThree.setSpacing(10);
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
                selected.setResultOne(Integer.parseInt(resultOne.getText()));
                selected.setResultTwo(Integer.parseInt(resultOne.getText()));
                selected.setWinner(winnerField.getText());
                matchController.updateMatchObject(selected);
                popup.close();
            }
        });

        VBox box = new VBox();
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(listView, chooseWarning, chooseButton, boxOne, boxTwo, boxThree, warningText, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att ta bort en match
    public void deleteMatch(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setMinWidth(450);
        popup.setMinHeight(450);
        popup.setTitle("Remove Match");

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

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(15);
        box.getChildren().addAll(listView, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }

    // Metod: Popup deleted match objekt
    public void deletedPopup(Stage popup, Match selectedMatch){
        Stage popupDelete = new Stage();
        popupDelete.initModality(Modality.APPLICATION_MODAL);
        popupDelete.setMinWidth(400);
        popupDelete.setMinHeight(200);
        popupDelete.setTitle("Remove Match");

        Text text = new Text("You have deleted: \n" + selectedMatch.toString());
        text.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button deleted = new Button(" Ok ");
        deleted.setOnAction(event -> {
            popupDelete.close();
            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getChildren().addAll(text, deleted);
        Scene popupscene = new Scene(box);
        popupDelete.setScene(popupscene);
        popupDelete.showAndWait();
    }


    // Metod: Popup för att uppdatera match
    public void matchUpdate(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setMinWidth(600);
        popup.setMinHeight(550);
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

        ComboBox<Player> comboBoxPlayerOne = new ComboBox<>();
        ComboBox<Player> comboBoxPlayerTwo = new ComboBox<>();
        ComboBox<Teams> comboBoxTeamOne = new ComboBox<>();
        ComboBox<Teams> comboBoxTeamTwo = new ComboBox<>();

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

        Text warningNumbers = new Text();

        // Välja match att update
        chooseButton.setOnAction( event -> {
            selected = listView.getSelectionModel().getSelectedItem();
            changeLabels(selected.getPlayerOrTeam());

            if(selected.getPlayed().equals("team")){
               tempOne.getChildren().clear();
               tempTwo.getChildren().clear();
               tempOne.getChildren().add(comboBoxTeamOne);
               tempTwo.getChildren().add(comboBoxTeamTwo);
            }else{
                tempOne.getChildren().clear();
                tempTwo.getChildren().clear();
                tempOne.getChildren().add(comboBoxPlayerOne);
                tempTwo.getChildren().add(comboBoxPlayerTwo);
            }

            date.setValue(selected.getMatchDate());

            if(selected.getPlayed().equals("Played")){
                played.setSelected(true);
            }else{
                notPlayed.setSelected(true);
            }

            resultOne.setText(String.valueOf(selected.getResultOne()));
            resultTwo.setText(String.valueOf(selected.getResultOne()));
            winnerField.setText(selected.getWinner());

        });

        // Knapp: Uppdatera vald match
        Button updateButton = new Button("Update Match");
        updateButton.setOnAction(event -> {
            String nameOne = "";
            String nameTwo = "";
            int idOne = 0;
            int idTwo = 0;

            if(selected.getPlayerOrTeam().equals("player")){
                Player playerOne = comboBoxPlayerOne.getSelectionModel().getSelectedItem();
                Player playerTwo = comboBoxPlayerTwo.getSelectionModel().getSelectedItem();

                // Hämta id & namn
                //selected.setPlayerTeamOneId(playerOne.getId);
                //selected.setPlayerTeamOneId(playerOne.getId);
                //selected.setPlayerTeamOneName(playerOne.getName());
                //selected.setPlayerTeamOneName(playerTwo.getName());
            }else{
                Teams teamOne = comboBoxTeamOne.getSelectionModel().getSelectedItem();
                Teams teamTwo = comboBoxTeamTwo.getSelectionModel().getSelectedItem();

                // Hämta id & namn
               // selected.setPlayerTeamTwoId(teamOne.getId());
               // selected.setPlayerTeamTwoId(teamTwo.getId());
               // selected.setPlayerTeamOneName(teamOne.getName());
               // selected.setPlayerTeamOneName(teamTwo.getName());
            }

            // LocalDate tempDate = date.getValue();
            RadioButton tempPlayedSelected = (RadioButton) group.getSelectedToggle();
            // String tempPlayed = tempPlayedSelected.getText();
            // String tempResultOne = resultOne.getText();
            // String tempResultTwo = resultTwo.getText();
            // String tempWinner = winnerField.getText();

            selected.setMatchDate(date.getValue());
            selected.setPlayed(tempPlayedSelected.getText());

            // Kontrollera att innehållet är en int
            if(testTextField(resultOne.getText()) && testTextField(resultTwo.getText())){
                selected.setResultOne(Integer.parseInt(resultOne.getText()));
                selected.setResultTwo(Integer.parseInt(resultTwo.getText()));
                selected.setWinner(winnerField.getText());

                matchController.updateMatchObject(selected);
                popup.close();
            }else {
                warningNumbers.setText("Put numbers in the result field and try again!");
            }
        });

        rowOne.getChildren().addAll(playerTeamOne, tempOne);
        rowTwo.getChildren().addAll(playerTeamTwo, tempTwo);
        rowThree.getChildren().addAll(dateLabel, date);
        rowFour.getChildren().addAll(playedNotPlayed, played, notPlayed);
        rowFive.getChildren().addAll(resultLabelOne, resultOne);
        rowSix.getChildren().addAll(resultLabelTwo, resultTwo);
        rowSeven.getChildren().addAll(winnerLabel, winnerField);

        VBox box = new VBox();
        VBox formBox = new VBox();
        formBox.setSpacing(15);
        box.setSpacing(15);
        formBox.getChildren().addAll(title, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, warningNumbers, updateButton);
        box.setAlignment(Pos.CENTER);
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

}