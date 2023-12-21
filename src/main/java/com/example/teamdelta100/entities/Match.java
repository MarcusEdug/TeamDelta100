package com.example.teamdelta100.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
* Match - Match objekt som kan placeras i databasen. Kopplat med Teams och Players.
* @Author: Evelina Daun
 */


@Entity
@Table(name="matches")
public class Match {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "playerOrTeam") // Om matchen innehåller Teams eller Players
    private String playerOrTeam;   // Värde: "team", "player"

    // Koppling mellan Player och Match
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "match")
    private List<Player> playerList = new ArrayList<>();

    // Koppling mellan Teams och Match
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "match")
    private List<Teams> teamsList = new ArrayList<>();

    @Column(name = "nameOne")
    private String nameOne; // Player eller Team 1 namn

    @Column(name = "nameTwo")
    private String nameTwo; // Player eller Team 1 namn

    @Column(name = "matchDate")
    private LocalDate matchDate; // Matchens datum

    @Column(name = "playedNotPlayed")
    private String played;  // Värde: "Played", "Not Played"

    @Column(name = "resultOne")
    private int resultOne;  // Resultatet för Team/Player 1

    @Column(name = "resultTwo")
    private int resultTwo;  // Resultatet för Team/Player 2

    @Column(name = "winner")
    private String winner;  // Vinnaren av matchen


    // Tom konstruktor
    public Match(){}

    // Konstruktor med allt inkluderat
    public Match(int matchId, String playerOrTeam, List<Player> playerList, List<Teams> teamsList,
                 String playerTeamNameOne, String playerTeamNameTwo, LocalDate matchDate, String played,
                 int resultOne, int resultTwo, String winner) {
        this.matchId = matchId;
        this.playerOrTeam = playerOrTeam;
        this.playerList = playerList;
        this.teamsList = teamsList;
        this.nameOne = playerTeamNameOne;
        this.nameTwo = playerTeamNameTwo;
        this.matchDate = matchDate;
        this.played = played;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.winner = winner;
    }

    // Konstruktor med allt utom primary key och listor
    public Match(String playerOrTeam, String playerTeamOneName, String playerTeamTwoName,
                 LocalDate matchDate, String played, int resultOne, int resultTwo, String winner) {
        this.playerOrTeam = playerOrTeam;
        this.nameOne = playerTeamOneName;
        this.nameTwo = playerTeamTwoName;
        this.matchDate = matchDate;
        this.played = played;
        this.resultOne = resultOne;
        this.resultTwo = resultTwo;
        this.winner = winner;
    }


    // Metod: Lägga till Player i Match
    public void addPlayer (Player player){
        player.setMatch(this);   // Lägga till Match objekt
        playerList.add(player);  // Lägga till Player i listan
    }

    // Metod: Lägga till Team i Match
    public void addTeams (Teams team){
        team.setMatch(this);  // Lägga till Match objekt
        teamsList.add(team);  // Lägga till Team i listan
    }


    // Metod: toString - Skriva ut matcherna med namn för spelare/lag och datum
    @Override
    public String toString() {
        return "Match: " + nameOne + " vs. " + nameTwo + ". Date: " + matchDate;
    }


    // Getters & Setters
    public int getMatchId() {
        return matchId;
    }

    public String getPlayerOrTeam() { return playerOrTeam; }

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

    public void setTeamsList(List<Teams> teamsList) { this.teamsList = teamsList; }

    public String getNameOne() {
        return nameOne;
    }

    public void setNameOne(String nameOne) {
        this.nameOne = nameOne;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public void setNameTwo(String nameTwo) {
        this.nameTwo = nameTwo;
    }
}