package com.example.teamdelta100.entities;

import javax.persistence.*;

// Klasskommmentar
//


@Entity
@Table(name="matches")
public class Match {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchId")
    private int matchId;

    @Column(name = "resultOne")
    private int resultOne; // Lag eller spelare 1

    @Column(name = "resultTwo")
    private int resultTwo; // Lag eller spelare 2

    @Column(name = "winner")
    private String name; // Team eller Player

    @Column(name = "match_date")
    private int matchDate;
    // Kolla upp vad som ska skrivas här
    // Kolla vad som ska stå i table

    @Column(name = "Decided/Not Decided")
    private Boolean decided; // True avgjord, False oavgjord

    @Column(name = "Player or Team")
    private String playerOrTeam;  // Player or Team


    // Tom konstruktor
    public Match(){}

    // Konstruktor med allt
    public Match(int id, int r, int date){
        this.matchId = id;

        this.matchDate = date;
    }

    // Konstruktor med allt utom matchId - Primary key
    public Match(int r, int date){

        this.matchDate = date;
    }


    // Getters & Setters
    public int getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(int matchDate) {
        this.matchDate = matchDate;
    }
}
