package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/*
    Klassen skapar Popup rutor som gör de möjligt för användare att skapa,ändra och ta bort i databasen
    Klaseen gör de möjligt att assign och remova player till/från teams
    De finns 6 metoder i klassen
    * Skapa popup fönstret
    * Skapa ett team objekt
    * Assign player till team
    * Remova player från team
    * Deleta ett team objekt
    * Ändra ett team objekt
 */
public class TeamPopup {
    private String userStringSubmit;
    private int userIntSubmit;
    private ComboBox comboBox;
    private Scene scene;
    private String titleName;
    private Label featureText;
    private int teamId;
    private int playerId;
    private Teams tempTeam;
    private Player player = new Player();
    private Text error = new Text();
    private Label explainText1;
    private Label explainText2;
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;
    private VBox vBox1;

    //Metod: Skapa standarden för mina popup fönster
    public Stage popupWindows(){
        Stage window = new Stage();
        window.setTitle(titleName);
        window.setHeight(165);
        window.setWidth(320);
        window.setOnCloseRequest(e-> {
            e.consume();
        });

        return window;
    }

    // Tar fram ett string och skicka tillbaka det
    public String addTeam (){
        titleName = "Add team";
        featureText = new Label("What is your Team name?");

        // sätt upp fönstret
        Stage window = popupWindows();

        // skapa ett sätt för användare att skriva in text
        TextField userText = new TextField();
        userText.setMaxWidth(100);

        //skapa en knapp som spara de användare har skriv i text rutan
        Button submit = new Button("Submit");
        submit.setOnAction(e->
        {
            error.setText("");

            if(!userText.getText().equals("")) // kollar så att något har skrivit in
            {
                userStringSubmit = userText.getText();
                window.close();
            }
            else
            {
                error.setText("Writes a name");
            }
        });

        //Knapp för att stänga fönstret ifall man trycker fel
        Button close = new Button("Close");
        close.setOnAction(e-> {
            error.setText("");
            userStringSubmit = null;
            window.close();
            }
        );

        //Box för att lägga in text och knappar
        hBox2 = new HBox(5);
        hBox2.getChildren().addAll(submit,close);
        hBox2.setAlignment(Pos.CENTER);

        vBox1 = new VBox(5);
        vBox1.getChildren().addAll(featureText,userText, hBox2, error);
        vBox1.setAlignment(Pos.CENTER);

        //vissa upp fönstret
        scene = new Scene(vBox1);
        window.setScene(scene);
        window.showAndWait();

        return userStringSubmit;
    }

    // Tar fram ett team objekt och player som går att assin till varandra
    public void assignPlayerToTeam(List<Teams> teamsList, List<Player> playList){
        //Info text och titel på fönstret
        titleName = "Assign player";
        featureText = new Label("Assign player to a team");
        explainText1 = new Label("Select player: ");
        explainText2 = new Label("Select team: ");

        //Sätta upp fönstret
        Stage window = popupWindows();

        //Möjligthet för användaren att se vad för teams objekt de finns och för användaren att kunna välja något
        ComboBox comboBoxTeams = new ComboBox();
        for (Teams team : teamsList){
            comboBoxTeams.getItems().add(team);
        }

        comboBoxTeams.setPrefWidth(115);

        //Möjligthet för användaren att se vad för player objekt de finns och för användaren att kunna välja något
        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playList){
            comboBoxPlayer.getItems().add(player);
        }

        comboBoxPlayer.setPrefWidth(115);

        //skapa en knapp för att sluta föra föreningen.
        Button submit = new Button("Submit");
        submit.setOnAction(e->
        {
            error.setText("");
            // Tar ut objekten som användare har valt
            tempTeam = (Teams) comboBoxTeams.getValue();
            player = (Player) comboBoxPlayer.getValue();

            if (player != null && tempTeam != null) // kollar så att något har valts
            {
                if (player.getTeams() == null) // kollar så att player inte redan är med i ett lag
                {
                    //Lägger in värden id från dom två objekterna
                    player.setTeams(tempTeam);
                    teamId = tempTeam.getId();
                    playerId = player.getId();

                    if (playerId != 0 && teamId != 0) // kollar så att allt funkar innan stängen
                    {
                        window.close();
                    }
                    else {
                        error.setText("Select a team and player!");
                    }
                }
                else {
                    error.setText("The player already has a team");
                }
            }
            else {
                error.setText("The player already has a team");
            }
        });

        //skapar en knapp som stänger fönstret ifall man inte vill ändra något
        Button close = new Button("Close");
        close.setOnAction(e-> {
                    error.setText("");
                    playerId = 0;
                    teamId = 0;
                    window.close();
                }

        );

        //Skapar box och läggar inte allt i dom för att skapa en layout
        VBox textVBox = new VBox(5);
        textVBox.getChildren().addAll(explainText1,explainText2);
        textVBox.setAlignment(Pos.CENTER);

        VBox comboBoxVBox = new VBox(2);
        comboBoxVBox.getChildren().addAll(comboBoxPlayer, comboBoxTeams);
        comboBoxVBox.setAlignment(Pos.CENTER);

        hBox1 = new HBox(3);
        hBox1.getChildren().addAll(textVBox,comboBoxVBox);
        hBox1.setAlignment(Pos.CENTER);

        hBox2 = new HBox(3);
        hBox2.getChildren().addAll(submit, close);
        hBox2.setAlignment(Pos.CENTER);

        vBox1 = new VBox(5);
        vBox1.getChildren().addAll(featureText, hBox1, hBox2,error);
        vBox1.setAlignment(Pos.CENTER);

        //vissa upp fönstret
        scene = new Scene(vBox1);
        window.setScene(scene);
        window.showAndWait();
    }

    //Tar fram ett team objekt och player objekt som är assigna till varandra så att man kan sära på dom
    public void removePlayerFromTeam(List<Player> playList){
        //Info text och titel på fönstret
        titleName = "Remove player";
        featureText = new Label("Remove a player from a team");
        explainText1 = new Label("Select player: ");
        explainText2 = new Label("Team Id: ");

        //Sätta upp fönstret
        Stage window = popupWindows();

        //Visar vad teams objekt heter
        Text teamName = new Text();
        teamName.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        //Möjligthet för användaren att se vad för player objekt som har en koppling till ett teams objekt
        ComboBox comboBoxPlayer = new ComboBox();
        for (Player player : playList){
            if(player.getTeams() != null ) {
                comboBoxPlayer.getItems().add(player);
            }
        }

        //När användare välj ett player objekt så spara de objekt, namet på teams som den tillhör tas fram och team objekt spara
        comboBoxPlayer.setOnAction(e->{
            player = (Player) comboBoxPlayer.getValue();
            teamName.setText(String.valueOf(player.getId()));
            tempTeam =  player.getTeams();
        });

        //Skapar en submitt knapp som spara id för player och team objekt som användare vald
        Button submit = new Button("Submit");
        submit.setOnAction(e-> {
            error.setText("");
            if(comboBoxPlayer.getValue()!=null) {   // kollar så att något har valt
                error.setText("");
                teamId = tempTeam.getId();
                playerId = player.getId();
                window.close();
            }
            else {
                error.setText("Choose a player");
            }
        });

        //skapar en close knapp som stänger fönstret
        Button close = new Button("Close");
        close.setOnAction(e-> {
                    error.setText("");
                    teamId = 0;
                    playerId = 0;
                    window.close();
                }
        );


        //upprättare box för att skapa en layout
        hBox1 = new HBox(8);
        hBox1.getChildren().addAll(explainText1, comboBoxPlayer);
        hBox1.setAlignment(Pos.CENTER);

        hBox2 = new HBox(2);
        hBox2.getChildren().addAll(explainText2, teamName);
        hBox2.setAlignment(Pos.CENTER);

        hBox3 = new HBox(5);
        hBox3.getChildren().addAll(submit,close);
        hBox3.setAlignment(Pos.CENTER);

        vBox1 = new VBox(5);
        vBox1.getChildren().addAll(featureText, hBox1, hBox2,hBox3,error);
        vBox1.setAlignment(Pos.CENTER);

        //visa upp fönstret
        scene = new Scene(vBox1);

        window.setScene(scene);
        window.showAndWait();
    }

    //tar fram ett id på en team objekt och skickar tillbaka det
    public int deleteTeam (List<Teams> teamsList){
        //Info text
        titleName = "Delete team";
        featureText = new Label("Which team do you wanna delete?");

        //upprättning av fönstret
        Stage window = popupWindows();

        //möjlighet för användra att göra ett val av team objekt
        comboBox = new ComboBox();
        for (Teams team : teamsList){
            int id = team.getId();
            comboBox.getItems().add(id);
        }

        //Skapar en knapp som spara ID från objekt som har valt och skickar tillbaka de värdet
        Button submit = new Button("Submit");
        submit.setOnAction(e->
        {
            error.setText("");

            if(comboBox.getValue() != null) {   //Kollar så att ett val har gjort
                userIntSubmit = (int) comboBox.getValue();
                window.close();
            }
            else{
                error.setText("Choose a team!");
            }
        });

        //skapar en stäng av knapp
        Button close = new Button("Close");
        close.setOnAction(e-> {
            userIntSubmit = 0;
            error.setText("");
            window.close();
            }

        );

        //skapar box för att skapa en layout
        hBox1 = new HBox(3);
        hBox1.getChildren().addAll(submit,close);
        hBox1.setAlignment(Pos.CENTER);

        vBox1 = new VBox(5);
        vBox1.getChildren().addAll(featureText, comboBox, hBox1, error);
        vBox1.setAlignment(Pos.CENTER);

        scene = new Scene(vBox1);
        window.setScene(scene);
        window.showAndWait();

        return userIntSubmit;
    }

    //Tar fram ett team objekt och justerar de för att sen skicka tillbaka det
    public Teams updateTeamName(List<Teams> teamsList){
        //Info text
        titleName = "Change name";
        featureText = new Label("Select which team to change namn on");
        explainText1 = new Label("Select team: ");
        explainText2 = new Label("Write new name: ");

        //upprättning av fönster
        Stage window = popupWindows();

        //Möjlighet för att göra ett val på team objekt som finns i databasen
        comboBox = new ComboBox();
        for (Teams team : teamsList){
            comboBox.getItems().add(team);
        }

        //Text värden på nuvarande namn och möjlighet för att ändra namn
        Text currentName = new Text();
        TextField newName = new TextField();

        //Skapa en knapp som verkställer ändringen
        Button submit = new Button("Submit");
        submit.setOnAction(e->
        {
            error.setText("");
            tempTeam = (Teams) comboBox.getValue();     // tar fram ett team objekt från comboboxen

            if (tempTeam != null) {                     //kollar såatt ett val har gjort
                tempTeam.setName(newName.getText());    // justerar namnet på objektet
                window.close();
            }
            else {
                error.setText("Select a team!");
            }
        });

        //skapra en stänga av knapp
        Button close = new Button("Close");
        close.setOnAction(e-> {
            error.setText("");
            window.close();
            }

        );

        //skapra en layout
        hBox1 = new HBox(3);
        hBox1.getChildren().addAll(submit,close);
        hBox1.setAlignment(Pos.CENTER);

        hBox2 = new HBox(3);
        hBox2.getChildren().addAll(explainText1,comboBox);
        hBox2.setAlignment(Pos.CENTER);

        hBox3 = new HBox(3);
        hBox3.getChildren().addAll(explainText2, currentName, newName);
        hBox3.setAlignment(Pos.CENTER);

        vBox1 = new VBox(5);
        vBox1.getChildren().addAll(featureText, hBox2, hBox3, hBox1, error);
        vBox1.setAlignment(Pos.CENTER);

        scene = new Scene(vBox1);
        window.setScene(scene);
        window.showAndWait();

        return tempTeam;
    }


    //Getter och Setter
    public int getTeamId() {
        return teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

}
