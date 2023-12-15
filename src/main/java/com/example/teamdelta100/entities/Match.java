package com.example.teamdelta100.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Match objekt som kan placeras i databasen.
// Evelina Daun


@Entity
@Table(name="matches")
public class Match {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchId")
    private int matchId;

    @Column(name = "playerOrTeam")
    private String playerOrTeam;  // "team",  "player"


    /*
    // sätta in plats 0 - id 1 och plats 1 - id 2 ?
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "playerId")
    private List<Player> playerList = new ArrayList<>();

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "teamId")
    private List<Teams> teamsList = new ArrayList<>();
     */

    // TA BORT OCH ANVÄNDA LISTORNA ISTÄLLET  ???
    @Column(name = "playerTeamOneId")  // Player eller Team id
    private int playerTeamOneId;
    @Column(name = "playerTeamTwoId")
    private int playerTeamTwoId;

    // TA BORT OCH ANVÄNDA LISTORNA ISTÄLLET ???
    @Column(name = "playerTeamOneName") // Player eller Team namn
    private String playerTeamOneName;
    @Column(name = "playerTeamTwoName")
    private String playerTeamTwoName;

    @Column(name = "matchDate")
    private LocalDate matchDate;

    @Column(name = "playedNotPlayed")
    private String played; // "Played", "Not played"

    @Column(name = "resultOne")
    private int resultOne; // Lag eller spelare 1

    @Column(name = "resultTwo")
    private int resultTwo; // Lag eller spelare 2

    @Column(name = "winner")
    private String winner; // Team eller Player namnet


    // Tom konstruktor
    public Match(){}


    // Konstruktor med allt utom matchId - Primary key
    public Match(String playerOrTeam, int playerTeamOneId, int playerTeamTwoId,
                 String playerTeamOneName, String playerTeamTwoName, LocalDate matchDate,
                 String played, int resultOne, int resultTwo, String winner) {
        this.playerOrTeam = playerOrTeam;
        this.playerTeamOneId = playerTeamOneId;
        this.playerTeamTwoId = playerTeamTwoId;
        this.playerTeamOneName = playerTeamOneName;
        this.playerTeamTwoName = playerTeamTwoName;
        this.matchDate = matchDate;
        this.played = played;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.winner = winner;
    }


    // Konstruktor med allt inkluderat
    public Match(int matchId, String playerOrTeam, int playerTeamOneId, int playerTeamTwoId,
                 String playerTeamOneName, String playerTeamTwoName, LocalDate matchDate,
                 String played, int resultOne, int resultTwo, String winner) {
        this.matchId = matchId;
        this.playerOrTeam = playerOrTeam;
        this.playerTeamOneId = playerTeamOneId;
        this.playerTeamTwoId = playerTeamTwoId;
        this.playerTeamOneName = playerTeamOneName;
        this.playerTeamTwoName = playerTeamTwoName;
        this.matchDate = matchDate;
        this.played = played;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.winner = winner;
    }

    // Metod: toString - Skriva ut matcherna med namn och id för spelare/lag
    @Override
    public String toString() {
        return "Match: " + playerTeamOneName +
                " vs. " + playerTeamTwoName +
                ", Date: " + matchDate ;
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

    public int getPlayerTeamOneId() {
        return playerTeamOneId;
    }

    public void setPlayerTeamOneId(int playerTeamOneId) {
        this.playerTeamOneId = playerTeamOneId;
    }

    public int getPlayerTeamTwoId() {
        return playerTeamTwoId;
    }

    public void setPlayerTeamTwoId(int playerTeamTwoId) {
        this.playerTeamTwoId = playerTeamTwoId;
    }

    public String getPlayerTeamOneName() {
        return playerTeamOneName;
    }

    public void setPlayerTeamOneName(String playerTeamOneName) {
        this.playerTeamOneName = playerTeamOneName;
    }

    public String getPlayerTeamTwoName() {
        return playerTeamTwoName;
    }

    public void setPlayerTeamTwoName(String playerTeamTwoName) {
        this.playerTeamTwoName = playerTeamTwoName;
    }

    /*
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

     */
}
