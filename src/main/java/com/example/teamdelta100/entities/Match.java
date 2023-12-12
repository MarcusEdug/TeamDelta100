package com.example.teamdelta100.entities;

import javax.persistence.*;
import java.time.LocalDate;

// Klasskommmentar
//


@Entity
@Table(name="matches")
public class Match {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchId")
    private int matchId;

    @Column(name = "PlayerOrTeam")
    private String playerOrTeam;  // Player or Team "team" "player"

    @Column(name = "playerTeamOne") // id player team
    private int playerTeamOneId;
    @Column(name = "playerTeamTwo") // id player team
    private int playerTeamTwoId;

    @Column(name = "playerTeamOne") // Namnet på team/player
    private String playerTeamOne;
    @Column(name = "playerTeamTwo") //  Namnet på team/player
    private String playerTeamTwo;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @Column(name = "Played/NotPlayed")
    private Boolean played; // True avgjord, False oavgjord

    @Column(name = "resultOne")
    private int resultOne; // Lag eller spelare 1

    @Column(name = "resultTwo")
    private int resultTwo; // Lag eller spelare 2

    @Column(name = "winner")
    private String winner; // Team eller Player



    // Tom konstruktor
    public Match(){}

    // Konstruktor med allt
    public Match(int id, int r, LocalDate date){
        this.matchId = id;

        this.matchDate = date;
    }

    // Konstruktor med allt utom matchId - Primary key
    public Match(int r, LocalDate date){

        this.matchDate = date;
    }


    // Getters & Setters


    public int getMatchId() {
        return matchId;
    }


    public String getPlayerOrTeam() {
        return playerOrTeam;
    }

    public void setPlayerOrTeam(String playerOrTeam) {
        this.playerOrTeam = playerOrTeam;
    }

    public String getPlayerTeamOne() {
        return playerTeamOne;
    }

    public void setPlayerTeamOne(String playerTeamOne) {
        this.playerTeamOne = playerTeamOne;
    }

    public String getPlayerTeamTwo() {
        return playerTeamTwo;
    }

    public void setPlayerTeamTwo(String playerTeamTwo) {
        this.playerTeamTwo = playerTeamTwo;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public Boolean getPlayed() {
        return played;
    }

    public void setPlayed(Boolean played) {
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
}
