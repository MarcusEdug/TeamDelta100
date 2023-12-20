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

    @Column (name = "team_id")
    private int teamId;
    private String teamName;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "games" )
    private List<Teams> teamsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "games" )
    private List<Player> playerList = new ArrayList<>();

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
    public Games(int gameId, String gameName, int playerId, String playerName, List<Games> gamesToChose, Player player, int teamId, String teamName, Teams teams) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.playerId = playerId;
        this.playerName = playerName;
        this.teamId = teamId;
        this.teamName = teamName;

    }

    //Konstruktor med allt utom gameId.

    public Games(String gameName, int playerId, String playerName, int teamId, String teamName, List<Teams> teamsList, List<Player> playerList) {
        this.gameName = gameName;
        this.playerId = playerId;
        this.playerName = playerName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamsList = teamsList;
        this.playerList = playerList;
    }

    public Games(String gameName) {
        this.gameName = gameName;
    }

    public Games(int id, String genre) {
    }

    public void addPlayer (Player player){
        player.setGames(this);
        playerList.add(player);
    }

    public void addTeams (Teams team){
        team.setGames(this);
        teamsList.add(team);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    /*    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;*/

    /*public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;*/

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

/*    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }*/
    //För att få ut text i comboboxen vid uppdatering av namn
    @Override
    public String toString() {
        return gameName;
    }

    public List<Teams> getTeamsList() {
        return teamsList;
    }

    public void setTeamsList(List<Teams> numberOfTeamsList) {
        this.teamsList = numberOfTeamsList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> numberOfPlayerList) {
        this.playerList = numberOfPlayerList;
    }


}