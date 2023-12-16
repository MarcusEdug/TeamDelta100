package com.example.teamdelta100.entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//CRUD-operationer
@Entity
@Table (name = "games")
public class Games {
    //Primary key
    @Id
    //Id genereras av databasen
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;
    @Column (length = 50)
    private String gameName;

    @Column (name = "player_id")
    private int playerId;
    private String playerName;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "games" )
    private List<Teams> numberOfTeamsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "games" )
    private List<Player> numberOfPlayerList = new ArrayList<>();






    //OneToMany - Ett spel till många spelare/lag
    //Fetch - fråga alltid efter entiteter och children.
    //Cascade - sparar entitet och children på en gång
    //Mapped - Förhindrar den här sidan att skapa en extra tabell för relational mapping
    //Mapped - säger till hibernate att den motsatta sidan har kontroll, inget behov av att skapa tabeller (behövs den här?)


 /*   @JoinColumn (name = "player_id")
    @JoinColumn (name = "team_id")*/
    //private Player player;
    // private Teams teams;

    //Tom konstruktor
    public Games() {
    }



    //Konstruktor med allt
    public Games(int gameId, String gameName, int playerId, String playerName, List<Games> gamesToChose, Player player /*Teams teams*/) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.playerId = playerId;
        this.playerName = playerName;
        /*  this.gamesToChose = gamesToChose;*/
        /*this.player = player;*/
        /*this.teams = teams;*/
    }

    //Konstruktor med allt utom gameId. Behövs playerId
    public Games(String gameName, int playerId, String playerName, List<Games> gamesToChose, Player player /*Teams teams*/) {
        this.gameName = gameName;
        this.playerId = playerId;
        this.playerName = playerName;
        /*this.gamesToChose = gamesToChose;*/
        /*this.player = player;*/
        /*this.teams = teams;*/
    }

    public Games(String gameName) {
        this.gameName = gameName;
    }

    public Games(int id, String genre) {
    }

    public void addPlayer (Player player){
        player.setGames(this);
    }

    public void addTeams (Teams team){
        team.setGames(this);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /*  public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }*/

 /*   public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }*/

    public void setGameId(int id) {
        this.gameId = id;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    //För att få ut text i comboboxen vid uppdatering av namn
    @Override
    public String toString() {
        return gameName;
    }

    public List<Teams> getNumberOfTeamsList() {
        return numberOfTeamsList;
    }

    public void setNumberOfTeamsList(List<Teams> numberOfTeamsList) {
        this.numberOfTeamsList = numberOfTeamsList;
    }

    public List<Player> getNumberOfPlayerList() {
        return numberOfPlayerList;
    }

    public void setNumberOfPlayerList(List<Player> numberOfPlayerList) {
        this.numberOfPlayerList = numberOfPlayerList;
    }


}