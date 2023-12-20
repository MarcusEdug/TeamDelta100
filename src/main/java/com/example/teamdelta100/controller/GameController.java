package com.example.teamdelta100.controller;
import com.example.teamdelta100.entities.Games;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameController {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    // Create
    public boolean save(Games games) {
        // Boiler plate
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(games); //Persist = spara
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Read
    public List<Games> getAll(boolean printOut) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Games> gamesListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Games> resultList = entityManager.createQuery("FROM Games", Games.class);
            gamesListToReturn.addAll(resultList.getResultList());
            transaction.commit();
            if (printOut) {
                for (Games games :
                        gamesListToReturn) {
                    System.out.println(games.getGameId() + ". " + games.getGameName());
                }
            }
            return gamesListToReturn;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Update
    public boolean updateGames(Games games) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(games);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.getMessage();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Delete med ID
    public boolean deleteGameById(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Hämtar games entity från databasen med gameId
            //Kollar om entiteten finns som property eller identifier
            Games games = entityManager.find(Games.class, gameId);

            //Denna funkar ej för att den kolla om en entity finns men entiteten är en integer.
            //contains-metoden vill ha ett entity-objekt, inte en property eller identifier av en entity
            //entityManager.remove(entityManager.contains(gameId) ? gameId : entityManager.merge(gameId));

            //Kollar om games finns i databasen och tar sedan bort efter id
            if (games != null) {
                //Ta bort spel
                entityManager.remove(games);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
    public boolean removePlayerFromGame(int gameId, int playerId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Player> selectPlayer= Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Games> selectGames = Optional.ofNullable(entityManager.find(Games.class, gameId));

            if (selectGames != null && selectPlayer != null) {
                Player player = selectPlayer.get();
                Games games = selectGames.get();

                games.getPlayerList().remove(0);
                games.setPlayerName(null);
                games.setPlayerId(0);
                player.setGames(null);
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
    public boolean removeTeamFromGame (int teamId, int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Teams> selectTeam = Optional.ofNullable(entityManager.find(Teams.class, teamId));
            Optional<Games> selectGames = Optional.ofNullable(entityManager.find(Games.class, gameId));

            if (selectGames != null && selectTeam != null) {
                Teams teams = selectTeam.get();
                Games games = selectGames.get();

                games.getTeamsList().remove(0);
                games.setTeamName(null);
                games.setTeamId(0);
                teams.setGameName(null);
                teams.setGames(null);


                /*
                Player player = selectPlayer.get();
                team = selectTeam.get();
                team.getNumberOfPlayerList().remove(0);
                player.setTeams(null);
                player.setTeamName(null);
                transaction.commit();
                 */
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    public List<Games> tableUpdate() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Games> gamesListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Games> resultList = entityManager.createQuery("FROM Games", Games.class);
            gamesListToReturn.addAll(resultList.getResultList());
            return gamesListToReturn;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return null;
    }

    public boolean addPlayerToGame(int playerId, int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        Games games;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Player> selectPlayer = Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Games> selectGames = Optional.ofNullable(entityManager.find(Games.class, gameId));

            Player player = selectPlayer.get();
            games = selectGames.get();
            games.setPlayerId(player.getId());
            games.setPlayerName(player.getPlayerName());
            games.addPlayer(player);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    public boolean addTeamToGame(int teamsId, int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        Games games;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Teams> selectTeam = Optional.ofNullable(entityManager.find(Teams.class, teamsId));
            Optional<Games> selectGame = Optional.ofNullable(entityManager.find(Games.class, gameId));

            Teams teams = selectTeam.get();
            games = selectGame.get();
            teams.setGameName(games.getGameName());
            games.setTeamId(teams.getId());
            games.setTeamName(teams.getName());
            games.addTeams(teams);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
}


