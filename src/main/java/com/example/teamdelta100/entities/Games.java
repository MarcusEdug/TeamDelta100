package com.example.teamdelta100.entities;
import javax.persistence.*;
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

    @Column (length = 50)
    private String gameGenre;

    @Column (name = "player_id")
    private int playerId;

    //OneToMany - Ett spel till många spelare/lag
    //Fetch - fråga alltid efter entiteter och children.
    //Cascade - sparar entitet och children på en gång
    //Mapped - Förhindrar den här sidan att skapa en extra tabell för relational mapping
    //Mapped - säger till hibernate att den motsatta sidan har kontroll, inget behov av att skapa tabeller (behövs den här?)

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "player" + "teams")
    //private List<Games> gamesToChose = new ArrayList<>();

 /*   @JoinColumn (name = "player_id")
    @JoinColumn (name = "team_id")*/
    //private Player player;
    // private Teams teams;

    //Tom konstruktor
    public Games() {
    }



    //Konstruktor med allt
    public Games(int gameId, String gameName, String gameGenre, int playerId, List<Games> gamesToChose, Player player /*Teams teams*/) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameGenre = gameGenre;
        this.playerId = playerId;
        /*  this.gamesToChose = gamesToChose;*/
        /*this.player = player;*/
        /*this.teams = teams;*/
    }

    //Konstruktor med allt utom gameId. Behövs playerId
    public Games(String gameName, String gameGenre, int playerId, List<Games> gamesToChose, Player player /*Teams teams*/) {
        this.gameName = gameName;
        this.gameGenre = gameGenre;
        this.playerId = playerId;
        /*this.gamesToChose = gamesToChose;*/
        /*this.player = player;*/
        /*this.teams = teams;*/
    }

    public Games(String gameName) {
        this.gameName = gameName;
    }

    public Games(int id, String genre) {
    }


    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

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

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public int getGameId() {
        return gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    //För att få ut text i comboboxen vid uppdatering av namn
    @Override
    public String toString() {
        return gameName;
    }

  /*  public List<Games> getGamesToChose() {
        return gamesToChose;
    }

    public void setGamesToChose(List<Games> gamesToChose) {
        this.gamesToChose = gamesToChose;
    }*/

}