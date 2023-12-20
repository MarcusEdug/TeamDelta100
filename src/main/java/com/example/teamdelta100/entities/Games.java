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

    public int getGameId() {
        return gameId;
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

    @Override
    public String toString() {
        return gameName;
    }
}