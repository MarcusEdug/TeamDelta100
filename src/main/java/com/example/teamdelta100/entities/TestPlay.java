package com.example.teamdelta100.entities;

import javax.persistence.*;

@Entity
@Table (name = "Player")
public class TestPlay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "player_name", length = 30)
    private String name;

    @ManyToOne
    @JoinColumn (name = "team_id")
    private Teams teams;



    public TestPlay() {
    }

    public TestPlay(String name) {
        this.name = name;
    }

    public TestPlay(String name, Teams teams) {
        this.name = name;
        this.teams = teams;
    }

    public TestPlay(int id, String name, Teams teams) {
        this.id = id;
        this.name = name;
        this.teams = teams;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teamId) {
        this.teams = teamId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
