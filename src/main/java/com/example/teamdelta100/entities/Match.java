package com.example.teamdelta100.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Match objekt som kan placeras i databasen. Kopplat med Teams och Players.
// Evelina Daun


@Entity
@Table(name="matches")
public class Match {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "playerOrTeam")
    private String playerOrTeam;  // "team",  "player"

    @Column(name = "idOne")
    private int idOne;

    @Column(name = "idTwo")
    private int idTwo;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "match")
    private List<Player> playerList = new ArrayList<>();

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "match")
    private List<Teams> teamsList = new ArrayList<>();


    @Column(name = "playerTeamNameOne") // Player eller Team namn
    private String playerTeamNameOne;


    @Column(name = "playerTeamNameTwo")
    private String playerTeamNameTwo;


    @Column(name = "matchDate")
    private LocalDate matchDate;

    @Column(name = "playedNotPlayed")
    private String played; // "Played", "Not Played"

    @Column(name = "resultOne")
    private int resultOne; // Lag eller spelare 1

    @Column(name = "resultTwo")
    private int resultTwo; // Lag eller spelare 2

    @Column(name = "winner")
    private String winner; // Team eller Player namnet


    // Tom konstruktor
    public Match(){}


    // Konstruktor med allt utom matchId - Primary key
    public Match(String playerOrTeam, LocalDate matchDate,
                 String played, int resultOne, int resultTwo, String winner) {
        this.playerOrTeam = playerOrTeam;
        this.matchDate = matchDate;
        this.played = played;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.winner = winner;
    }


    // Konstruktor med allt inkluderat


    public Match(String playerOrTeam, String playerTeamOneName, String playerTeamTwoName,
                 LocalDate matchDate, String played, int resultOne, int resultTwo, String winner) {
        this.playerOrTeam = playerOrTeam;
        this.playerTeamNameOne = playerTeamOneName;
        this.playerTeamNameTwo = playerTeamTwoName;
        this.matchDate = matchDate;
        this.played = played;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.winner = winner;
    }

    // Metod: Lägga till player
    public void addPlayer (Player player){
        player.setMatch(this);
        playerList.add(player);
    }

    // Metod: lägga till team
    public void addTeams (Teams team){
        team.setMatch(this);
        teamsList.add(team);
    }


    // Metod: toString - Skriva ut matcherna med namn och id för spelare/lag
    @Override
    public String toString() {
        return "Match: " + playerTeamNameOne + " vs. " + playerTeamNameTwo + ". Date: " + matchDate;
    }

    // Getters & Setters
    public int getMatchId() {
        return matchId;
    }

    public String getPlayerOrTeam() {
        return playerOrTeam;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public int getResultOne() {
        return resultOne;
    }

    public void setResultOne(int resultOne) {
        this.resultOne = resultOne;
    }

    public int getResultTwo() {
        return resultTwo;
    }

    public void setResultTwo(int resultTwo) {
        this.resultTwo = resultTwo;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String name) {
        this.winner = name;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Teams> getTeamsList() {
        return teamsList;
    }

    public void setTeamsList(List<Teams> teamsList) {
        this.teamsList = teamsList;
    }


    public String getPlayerTeamNameOne() {
        return playerTeamNameOne;
    }

    public void setPlayerTeamNameOne(String playerTeamOneName) {
        this.playerTeamNameOne = playerTeamOneName;
    }

    public String getPlayerTeamNameTwo() {
        return playerTeamNameTwo;
    }

    public void setPlayerTeamNameTwo(String playerTeamTwoName) {
        this.playerTeamNameTwo = playerTeamTwoName;
    }

    public int getIdOne(){
        return playerList.get(0).getId();
    }
}