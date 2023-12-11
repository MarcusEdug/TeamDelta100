package com.example.teamdelta100.entities;


import javax.persistence.*;


@Entity
@Table(name="matches")
public class Match {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchId")
    private int matchId;

    @Column(name = "result")
    private int result;

    @Column(name = "match_date")
    private int matchDate;
    // Kolla upp vad som ska skrivas här
    // Kolla vad som ska stå i table

    // Tom konstruktor
    public Match(){}

    // Konstruktor med allt
    public Match(int id, int r, int date){
        this.matchId = id;
        this.result = r;
        this.matchDate = date;
    }

    // Konstruktor med allt utom matchId - Primary key
    public Match(int r, int date){
        this.result = r;
        this.matchDate = date;
    }


    // Getters & Setters
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(int matchDate) {
        this.matchDate = matchDate;
    }
}
