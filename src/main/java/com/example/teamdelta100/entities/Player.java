package com.example.teamdelta100.entities;

import javax.persistence.*;

@Entity
@Table (name = "PLAYER")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "first_name")
    private String playerName;

    @Column(name = "last_name")
    private String playerLastname;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "email")
    private String email;

    //Alex har lagt till den här
    @ManyToOne
    @JoinColumn (name = "team_id")
    private Teams teams;

    @ManyToOne
    @JoinColumn (name = "game_id")
    private Games games;

    @ManyToOne
    @JoinColumn (name = "match_id")
    private Match match;




    public Player() {

    }

    public Player(int id, String nickname, String address, String postalCode,
                  String city, String country, String email, String playerName, String playerLastname) {
        this.Id = id;

        this.nickname = nickname;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.email = email;
        this.playerName = playerName;
        this.playerLastname = playerLastname;
    }

    @Override
    public String toString() {
        return playerName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = Id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerLastname() {
        return playerLastname;
    }

    public void setPlayerLastname(String playerLastname) {
        this.playerLastname = playerLastname;
    }

    //Alex har skapat en här
    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }



    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public Player(String playerName) {
    }

}


