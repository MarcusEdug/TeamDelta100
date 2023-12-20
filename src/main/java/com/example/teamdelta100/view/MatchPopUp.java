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
import java.util.List;


// Hanterar popup fönster och kommunikation med controller.
// Evelina Daun


public class MatchPopUp {
    private MatchController matchController;
    private TeamFX teamFX = new TeamFX(); // För att nå lista med Teams objekt
    private PlayerMenu playerMenu = new PlayerMenu(); // För att nå lista med Player objekt
    private Match selected; // Vald match att hantera
    private Label title = new Label();

    // Labels för formulär
    private Label playerTeamOne = new Label("Player/Team 1: ");
    private Label playerTeamTwo = new Label("Player/Team 2: ");
    private Label dateLabel = new Label("Match Date: ");
    private Label playedLabel = new Label("Match played or not played: ");
    private Label resultLabelOne = new Label("Reslult Player/Team 1 :");
    private Label resultLabelTwo = new Label("Reslult Player/Team 2: ");
    private Label winnerLabel = new Label("Winner: ");


    // Konstruktor
    public MatchPopUp(MatchController matchController) {
        this.matchController = matchController;
    }


    // Metod: Popup fö att skapa en match
    public void createMatch(String teamOrPlayer){
        Stage popup = new Stage();
        popup.setWidth(450);
        popup.setHeight(450);
        popup.setTitle("Add Match");
        changeLabels(teamOrPlayer);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        HBox holderOne = new HBox(); // Innehålla lista med Players eller Teams
        HBox holderTwo = new HBox();

        ComboBox<Player> comboBoxPlayerOne = new ComboBox<>(); // Skapa upp listor
        ComboBox<Player> comboBoxPlayerTwo = new ComboBox<>();
        ComboBox<Teams> comboBoxTeamOne = new ComboBox<>();
        ComboBox<Teams> comboBoxTeamTwo = new ComboBox<>();

        if(teamOrPlayer.equals("player")){
            title.setText("Player vs. Player");
            comboBoxPlayerOne.setPromptText("Select Player ... ");
            comboBoxPlayerTwo.setPromptText("Select Player ... ");
            comboBoxPlayerOne.getItems().clear(); // Tömma listan innan den fylls på nytt
            comboBoxPlayerTwo.getItems().clear();
            holderOne.getChildren().add(comboBoxPlayerOne); // Lägga till i HBox
            holderTwo.getChildren().add(comboBoxPlayerTwo);

            for(Player p : playerMenu.playerDatabaseList()){ // Fylla på listorna
                comboBoxPlayerOne.getItems().add(p);
                comboBoxPlayerTwo.getItems().add(p);
            }

        }else{
            title.setText("Team vs. Team");
            comboBoxTeamOne.setPromptText("Select Team ... ");
            comboBoxTeamTwo.setPromptText("Select Team ... ");
            comboBoxTeamOne.getItems().clear();
            comboBoxTeamTwo.getItems().clear();
            holderOne.getChildren().add(comboBoxTeamOne);
            holderTwo.getChildren().add(comboBoxTeamTwo);

            for(Teams t : teamFX.teamDatabaseList()){
                comboBoxTeamOne.getItems().add(t);
                comboBoxTeamTwo.getItems().add(t);
            }
        }

        // Matchens datum
        DatePicker date = new DatePicker();
        date.setPromptText("YYYY-MM-DD");

        // Om matchen är avgjord
        RadioButton played = new RadioButton("Played");
        RadioButton notPlayed = new RadioButton("Not Played");
        ToggleGroup group = new ToggleGroup();
        played.setToggleGroup(group);
        notPlayed.setToggleGroup(group);
        group.selectToggle(notPlayed);

        TextField resultOne = new TextField();    // Resultat 1 & 2
        TextField resultTwo = new TextField();
        TextField winnerField = new TextField();  // Vinnare

        // Knappar
        Button createButton = new Button("Create Match");
        Button cancelButton = createCancelButton(popup);
        HBox buttonBox = new HBox(createButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        // Varningstext
        Text warningText = new Text();
        warningText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        createButton.setOnAction(event -> { // När skapa knappen trycks på
            String nameOne = "";
            String nameTwo = "";
            Player playerOne = new Player();
            Player playerTwo = new Player();
            Teams teamOne = new Teams();
            Teams teamTwo = new Teams();

             if(date.getValue() == null){ // Om datumvärde inte är inskrivet
                warningText.setText("Pick a date and try again! ");
             }else{
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

                    // Lägga in att matchnamnet syns för Teams
                    // teamOne.setMatchName(nameOne + " vs. " + nameTwo);
                    // teamTwo.setMatchName(nameOne + " vs. " + nameTwo);
                }

                 LocalDate tempDate = date.getValue(); // Hämta datum
                 String tempPlayed = ((RadioButton) group.getSelectedToggle()).getText(); // Hämta Played eller Not Played

                 // Kontrollera resultaten
                 if(!testResultField(resultOne.getText()) && !testResultField(resultTwo.getText())){
                     warningText.setText("Put numbers in the result field and try again!");
                 }else{
                     int tempResultOne = 0; // Om resultatfältet är tomt är 0 default värdet
                     int tempResultTwo = 0;

                     if(!resultOne.getText().isEmpty() && !resultTwo.getText().isEmpty()){ // Om resultatfältet inte är tomt hämtas värdet
                         tempResultOne = Integer.parseInt(resultOne.getText());
                         tempResultTwo = Integer.parseInt(resultTwo.getText());
                     }

                     String tempWinner = winnerField.getText(); // Hämta vinnaren

                     Match tempMatch = new Match (teamOrPlayer, nameOne, nameTwo, tempDate, tempPlayed, tempResultOne, tempResultTwo, tempWinner);
                     matchController.addMatch(tempMatch); // Lägga till i databasen

                     if(teamOrPlayer.equals("player")){
                         matchController.addPlayerToMatch(tempMatch.getMatchId(), playerOne.getId());
                         matchController.addPlayerToMatch(tempMatch.getMatchId(), playerTwo.getId());
                     }else if (teamOrPlayer.equals("team")){
                         matchController.addTeamToMatch(tempMatch.getMatchId(), teamOne.getId());
                         matchController.addTeamToMatch(tempMatch.getMatchId(), teamTwo.getId());
                     }
                     popup.close();
                }
             }
        });

        // Lägga in i egna HBoxar för att få en egen rad
        HBox rowOne = new HBox(playerTeamOne, holderOne);          // Team/Player 1
        HBox rowTwo = new HBox(playerTeamTwo, holderTwo);          // Team/Player 2
        HBox rowThree = new HBox(dateLabel, date);                 // Datum
        HBox rowFour = new HBox(playedLabel, played, notPlayed);   // Played eller Not Played
        HBox rowFive = new HBox(resultLabelOne, resultOne);        // Resultat 1
        HBox rowSix = new HBox(resultLabelTwo, resultTwo);         // Resultat 2
        HBox rowSeven = new HBox(winnerLabel, winnerField);        // Vinnare
        HBox rowEight = new HBox(warningText);                     // Varningstext

        rowOne.setSpacing(5);
        rowTwo.setSpacing(5);
        rowThree.setSpacing(5);
        rowFour.setSpacing(5);
        rowFive.setSpacing(5);
        rowSix.setSpacing(5);
        rowSeven.setSpacing(5);
        rowEight.setAlignment(Pos.CENTER); // Centrera varningstext

        VBox box = vboxFormat(); // Formatera VBoxen
        box.getChildren().addAll( title, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, rowEight, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att lägga till ett resultat för en match
    public void addResult(){
        Stage popup = new Stage();
        popup.setWidth(450);
        popup.setHeight(450);
        popup.setTitle("Add Result");

        RadioButton played = new RadioButton("Played");
        RadioButton notPlayed = new RadioButton("Not Played");
        ToggleGroup group = new ToggleGroup();
        played.setToggleGroup(group);
        notPlayed.setToggleGroup(group);
        group.selectToggle(notPlayed);

        resultLabelOne.setText("Result 1: ");
        resultLabelTwo.setText("Result 2: ");

        ListView<Match> listView = createMatchList(); // Lista med matcher

        Button chooseButton = new Button("Select Match"); // Välja match knapp

        // Varningstexter
        Text warningTextOne = new Text();
        Text warningTextTwo = new Text();
        warningTextOne.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        warningTextTwo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField resultOne = new TextField();    // Resultat player/team 1
        TextField resultTwo = new TextField();    // Resultat player/team 2
        TextField winnerField = new TextField();  // Vinnare

        chooseButton.setOnAction(event ->  {  // Välja match knappen
            if(!listView.getSelectionModel().isEmpty()){
                warningTextOne.setText("");
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
                warningTextOne.setText(" Select a match to use. ");
            }
        });

        HBox rowOne = new HBox(playedLabel, played, notPlayed);
        HBox rowTwo = new HBox(resultLabelOne, resultOne);
        HBox rowThree = new HBox(resultLabelTwo, resultTwo);
        HBox rowFour = new HBox(winnerLabel, winnerField);
        rowOne.setSpacing(5);
        rowTwo.setSpacing(5);
        rowThree.setSpacing(5);
        rowFour.setSpacing(5);

        // Knapp
        Button addButton = new Button("Add Result");
        Button cancel = createCancelButton(popup);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(addButton, cancel);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        addButton.setOnAction(event -> {
            if(resultOne.getText().isEmpty() ||  resultTwo.getText().isEmpty() || winnerField.getText().isEmpty()){
                warningTextTwo.setText(" Fill in result form and try again! ");
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
        box.getChildren().addAll(listView, warningTextOne, chooseButton, rowOne, rowTwo, rowThree, rowFour, warningTextTwo, buttonBox);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att ta bort en match
    public void deleteMatch(){
        Stage popup = new Stage();
        popup.setWidth(450);
        popup.setHeight(450);
        popup.setTitle("Delete Match");
        title.setText("Select match to delete: ");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Skapa upp en lista att visa för användaren
        ListView<Match> listView = createMatchList();

        // Knappar
        Button deleteButton = new Button("Delete Match");
        Button cancel = createCancelButton(popup);

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(deleteButton, cancel);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        Text warningText = new Text();
        warningText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //
        deleteButton.setOnAction(event -> {
            if(listView.getSelectionModel().isEmpty()){
                warningText.setText("Select match to delete.");
            }else{
                warningText.setText("");
                Match selectedMatch = listView.getSelectionModel().getSelectedItem();
                matchController.deleteMatchObject(selectedMatch);
                deletedPopup(popup, selectedMatch);
            }
        });

        VBox box = vboxFormat();
        box.getChildren().addAll(title, listView, warningText, buttonBox);
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
        text.setFont(Font.font("Arial", FontWeight.BOLD, 12));

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


    // Metod: Popup för att uppdatera en match
    public void matchUpdate(){
        Stage popup = new Stage();
        popup.setWidth(600);
        popup.setHeight(550);
        popup.setTitle("Update Match");

        ListView<Match> listView = createMatchList(); // Skapa upp en lista att visa för användaren

        // Knappar
        Button selectButton = new Button("Select Match");
        Button cancel = createCancelButton(popup);
        HBox buttonBoxOne = new HBox();
        buttonBoxOne.getChildren().addAll(selectButton, cancel);
        buttonBoxOne.setSpacing(10);
        buttonBoxOne.setAlignment(Pos.CENTER);

        ComboBox<Player> comboBoxPlayerOne = new ComboBox<>();
        ComboBox<Player> comboBoxPlayerTwo = new ComboBox<>();
        ComboBox<Teams> comboBoxTeamOne = new ComboBox<>();
        ComboBox<Teams> comboBoxTeamTwo = new ComboBox<>();

        DatePicker date = new DatePicker();
        RadioButton played = new RadioButton("Played");
        RadioButton notPlayed = new RadioButton("Not Played");
        ToggleGroup group = new ToggleGroup();
        played.setToggleGroup(group);
        notPlayed.setToggleGroup(group);
        TextField resultOne = new TextField();
        TextField resultTwo = new TextField();
        TextField winnerField = new TextField();

        HBox tempOne = new HBox();      // Används för att ändra innehåll i lista ett
        HBox tempTwo = new HBox();      // Används för att ändra innehåll i lista två

        Text warningText = new Text();
        warningText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Välja match att uppdatera
        selectButton.setOnAction( event -> {
            if(listView.getSelectionModel().isEmpty()){
                warningText.setText(" Select a Match to continue! ");

            }else{
                warningText.setText(""); // Tömma varningstexten
                selected = listView.getSelectionModel().getSelectedItem();
                changeLabels(selected.getPlayerOrTeam());
                tempOne.getChildren().clear();
                tempTwo.getChildren().clear();

                if(selected.getPlayerOrTeam().equals("team")){
                    title.setText("Team vs. Team");
                    tempOne.getChildren().add(comboBoxTeamOne);
                    tempTwo.getChildren().add(comboBoxTeamTwo);

                    comboBoxTeamOne.getItems().clear();
                    comboBoxTeamTwo.getItems().clear();

                    for(Teams t : teamFX.teamDatabaseList()){
                        comboBoxTeamOne.getItems().add(t);
                        comboBoxTeamTwo.getItems().add(t);
                    }

                    List<Teams> tempList = matchController.getAllTeams(selected);
                    comboBoxTeamOne.setValue(tempList.get(0));
                    comboBoxTeamTwo.setValue(tempList.get(1));
                }else{
                    title.setText("Player vs. Player");
                    tempOne.getChildren().add(comboBoxPlayerOne);
                    tempTwo.getChildren().add(comboBoxPlayerTwo);

                    comboBoxPlayerOne.getItems().clear(); // Tömma
                    comboBoxPlayerTwo.getItems().clear();

                    for(Player p : playerMenu.playerDatabaseList()){ // Lägga till akturell lista
                        comboBoxPlayerOne.getItems().add(p);
                        comboBoxPlayerTwo.getItems().add(p);
                    }

                    List<Player> tempList = matchController.getAllPlayers(selected); // Hämta tidigare valda players
                    comboBoxPlayerOne.setValue(tempList.get(0));
                    comboBoxPlayerTwo.setValue(tempList.get(1));
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
            }
        });

        Button updateButton = new Button("Update Match");

        updateButton.setOnAction(event -> {
            if(selected.getPlayerOrTeam().equals("player")){ // Om player
                matchController.removeAllPlayers(selected); // Ta bort de gamla Players objekten

                Player playerOne = comboBoxPlayerOne.getSelectionModel().getSelectedItem(); // Hämta nya player objekt
                Player playerTwo = comboBoxPlayerTwo.getSelectionModel().getSelectedItem();

                selected.setNameOne(playerOne.getPlayerName()); // Nytt namn
                selected.setNameTwo(playerTwo.getPlayerName());

                matchController.addPlayerToMatch(selected.getMatchId(), playerOne.getId()); // Sätta dit ny player
                matchController.addPlayerToMatch(selected.getMatchId(), playerTwo.getId());

            }else{ // Om teams
                matchController.removeAllTeams(selected);

                Teams teamOne = comboBoxTeamOne.getSelectionModel().getSelectedItem();
                Teams teamTwo = comboBoxTeamTwo.getSelectionModel().getSelectedItem();

                selected.setNameOne(teamOne.getName());
                selected.setNameTwo(teamTwo.getName());

                matchController.addTeamToMatch(selected.getMatchId(), teamOne.getId());
                matchController.addTeamToMatch(selected.getMatchId(), teamTwo.getId());
            }

            RadioButton tempPlayedSelected = (RadioButton) group.getSelectedToggle();
            selected.setPlayed(tempPlayedSelected.getText());

            if(date.getValue() == null){
                warningText.setText("Pick a date and try again!");
            }else{
                selected.setMatchDate(date.getValue());

                // Kontrollera att resultaten är i sifferformat eller tomt
                if(testResultField(resultOne.getText()) && testResultField(resultTwo.getText())){
                    int tempResultOne = 0; // Om resultatfältet är tomt är 0 default värdet
                    int tempResultTwo = 0;

                    if(!resultOne.getText().isEmpty() && !resultTwo.getText().isEmpty()){ // Om resultatfältet inte är tomt hämtas värdet
                        tempResultOne = Integer.parseInt(resultOne.getText());
                        tempResultTwo = Integer.parseInt(resultTwo.getText());
                    }
                    selected.setResultOne(tempResultOne);
                    selected.setResultTwo(tempResultTwo);

                    selected.setWinner(winnerField.getText());

                    matchController.updateMatchObject(selected);
                    popup.close();
                }else {
                    warningText.setText("Put numbers in the result field and try again!");
                }
            }
        });

        // Lägga in i egna HBoxar för att få en egen rad
        HBox rowOne = new HBox(playerTeamOne, tempOne);          // Team/Player 1
        HBox rowTwo = new HBox(playerTeamTwo, tempTwo);          // Team/Player 2
        HBox rowThree = new HBox(dateLabel, date);               // Date
        HBox rowFour = new HBox(playedLabel, played, notPlayed); // Played or Not Played
        HBox rowFive = new HBox(resultLabelOne, resultOne);      // Result 1
        HBox rowSix = new HBox(resultLabelTwo, resultTwo);       // Result 2
        HBox rowSeven = new HBox(winnerLabel, winnerField);      // Winner
        VBox rowEight = new VBox(warningText, updateButton);     // Varningstext & Knapp

        rowEight.setAlignment(Pos.CENTER);

        VBox box = vboxFormat();   // VBox för hela popuprutan
        VBox formBox = new VBox(); // VBox för formuläret för att uppdatera
        formBox.setSpacing(15);

        // Lägga till innehållet för formuläret samt i den stora VBoxen
        formBox.getChildren().addAll(rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, rowEight);
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


    // Metod: Förändra label texter efter Team och Player
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
    // @Return: True (Om strängen innehåller siffror) , False (Om strängen inte innehåller siffror)
    public boolean testResultField(String inString){

        if(inString.isEmpty()){ // Ifall användaren inte vill mata in något vid skapandet av match
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
        List<Match> matchList = matchController.getAllMatchObjects(); // Hämta alla Matcher
        ObservableList<Match> obList = FXCollections.observableArrayList(matchList);
        listView.setItems(obList);
        listView.setPrefHeight(230);
        listView.setPrefWidth(230);
        return listView;
    }


    // Metod: Utseende för VBox - grunden för alla popup fönster
    public VBox vboxFormat(){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        vbox.setPadding(new Insets(20));
        return vbox;
    }
}