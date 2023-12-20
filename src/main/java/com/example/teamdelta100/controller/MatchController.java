package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Match;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Hantera kommunikationen med databasen för Match.
 * @Author: Evelina Daun
 */

public class MatchController {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    // Tom konstruktor
    public MatchController(){}


    // Metod: Lägga till match objekt i databasen
    // @Param: Match objekt att lägga till
    public void addMatch(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match); // Lägga in nytt objekt
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    // Metod: Uppdatera ett match objekt
    // @Param: Match objekt att uppdatera
    public void updateMatchObject(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(match); // Uppdatera objekt
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Ta bort match objekt
    // @Param: Match objekt att ta bort
    public void deleteMatchObject(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Match tempMatch = entityManager.find(Match.class, match.getMatchId()); // Hitta match objekt
            if(tempMatch.getPlayerOrTeam().equals("player")){  // Om det är en player match
                List<Player> players = tempMatch.getPlayerList(); // Hämta listan
                for(Player p : players){ // Gå igenom listan
                    p.setMatch(null);    // Sätta nytt match värde på player
                }
                tempMatch.getPlayerList().clear();
            }else{  // Om det är en teams match
                List<Teams> teams = tempMatch.getTeamsList();
                for(Teams t : teams){
                    t.setMatch(null);
                }
                tempMatch.getTeamsList().clear();
            }

            entityManager.remove(tempMatch);
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Hämta alla match objekt från databasen till en lista och skicka tillbaka
    public List<Match> getAllMatchObjects(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Match> returnList = new ArrayList<>();

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Match> resultList = entityManager.createQuery("FROM Match ", Match.class); // Hämta
            returnList.addAll(resultList.getResultList()); // Lägga till
            transaction.commit();
            return returnList;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    // Metod: Hämta player lista för ett specifikt match objekt - Används på grund av LAZY
    public List<Player> getAllPlayers(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Match tempMatch = entityManager.find(Match.class, match.getMatchId()); // Söka efter match med id
            List<Player> returnList = new ArrayList<>(tempMatch.getPlayerList()); // Hämta listan med players
            transaction.commit();
            return returnList;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }


    // Metod: Hämta teams lista för ett specifikt match objekt - Används på grund av LAZY
    public List<Teams> getAllTeams(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Match tempMatch = entityManager.find(Match.class, match.getMatchId()); // Söka efter Match med id
            List<Teams> returnList = new ArrayList<>(tempMatch.getTeamsList()); // Hämta listan med Players
            transaction.commit();
            return returnList;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }


    // Metod: Lägga till player till match objekt
    public void addPlayerToMatch(int matchId, int playerId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Player> player = Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Match> match = Optional.ofNullable(entityManager.find(Match.class, matchId));

           if(player.isPresent() && match.isPresent()){ // Om de finns
               Player tempPlayer = player.get();        // Hämta player & match
               Match tempMatch = match.get();
               tempMatch.addPlayer(tempPlayer);         // Lägga till player i match
           }
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Lägga till Team till Match objekt
    public void addTeamToMatch(int matchId, int teamsId,String matchName){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Teams> team = Optional.ofNullable(entityManager.find(Teams.class, teamsId));
            Optional<Match> match = Optional.ofNullable(entityManager.find(Match.class, matchId));

            if(team.isPresent() && match.isPresent()){ // Om de finns
                Teams tempTeam = team.get();           // Hämta team & match
                Match tempMatch = match.get();
                tempTeam.setMatchName(matchName);
                tempMatch.addTeams(tempTeam);          // Lägga till team i match
            }
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }
    public void addTeamToMatch(int matchId, int teamsId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Teams> team = Optional.ofNullable(entityManager.find(Teams.class, teamsId));
            Optional<Match> match = Optional.ofNullable(entityManager.find(Match.class, matchId));

            if(team.isPresent() && match.isPresent()){ // Om de finns
                Teams tempTeam = team.get();           // Hämta team & match
                Match tempMatch = match.get();
                tempMatch.addTeams(tempTeam);          // Lägga till team i match
            }
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    // Metod: Ta bort players från match objekt
    public void removeAllPlayers(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Match tempMatch = entityManager.find(Match.class, match.getMatchId());
            List<Player> players = tempMatch.getPlayerList();

            for(Player p : players){
                p.setMatch(null);
                entityManager.merge(p);
            }

            tempMatch.getPlayerList().clear();
            entityManager.merge(tempMatch);
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Ta bort teams från match objekt
    public void removeAllTeams(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Match tempMatch = entityManager.find(Match.class, match.getMatchId());
            List<Teams> teams = tempMatch.getTeamsList();

            for(Teams t : teams){
                t.setMatch(null);
                entityManager.merge(t);
            }

            tempMatch.getPlayerList().clear();
            entityManager.merge(tempMatch);
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

}