package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
    Den klass har hand om överförsningen av information mellan databasen och java
 */
public class TeamsController {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    //Spara teams objekt i database
    public boolean save (Teams player){
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try{
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.persist(player);
                transaction.commit();
                return true;
            } catch (Exception e){
                if(transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
            finally {
                entityManager.close();
            }
            return false;
        }

    //Ta bort teams objekt från database med hjälp av Id
    public boolean deleteTeamsById(int Id){
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                Teams teams = entityManager.find(Teams.class, Id);

                if(teams != null){
                    for(int i = 0; i < (teams.getNumberOfPlayerList().size()); i++){
                        System.out.println(teams.getName());
                        System.out.println(teams.getNumberOfPlayerList().get(i).getPlayerName());
                        System.out.println(i);
                        teams.getNumberOfPlayerList().get(i).setTeams(null);
                        teams.getNumberOfPlayerList().get(i).setTeamName(null);
                        teams.getNumberOfPlayerList().remove(i);
                    }
                    entityManager.remove(teams);
                }
                transaction.commit();
                return true;
            } catch (Exception e){
                if(transaction != null){
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            return false;
        }

    //Uppdatare Teams objekt som redan finns i databasen
    public boolean updateTeams(Teams teams){
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                entityManager.merge(teams);
                transaction.commit();
                return true;
            } catch (Exception e){
                if(transaction != null){
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            return false;
        }

    //Tar ut en lista från databasen
    public List<Teams> getAListOfDatabase(){
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            List<Teams> teamsListToReturn = new ArrayList<>();
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();
                TypedQuery<Teams> resultList = entityManager.createQuery("FROM Teams", Teams.class);
                teamsListToReturn.addAll(resultList.getResultList());
                transaction.commit();
                return teamsListToReturn;
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

    //Kopplar ihop ett teams objekt med ett player objekt
    public boolean addPlayerToTeams(int playerId, int teamsId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        Teams team;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (playerId != 0 && teamsId != 0) {
            Optional<Player> selectPlayer = Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Teams> selectTeam = Optional.ofNullable(entityManager.find(Teams.class, teamsId));


                        Player player = selectPlayer.get();
                        team = selectTeam.get();
                        player.setTeamName(team.getName());
                        team.addPlayer(player);
                        transaction.commit();

                        return true;
                    }
                } catch(Exception e){
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                } finally{
                    entityManager.close();
                }
                return false;
        }

        //kopplar ifrån ett Player objekt ifrån teams objekt
        public boolean removePlayerToTeams(int playerId, int teamsId) {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction transaction = null;
            Teams team;
            try {
                transaction = entityManager.getTransaction();
                transaction.begin();

                Optional<Player> selectPlayer = Optional.ofNullable(entityManager.find(Player.class, playerId));
                Optional<Teams> selectTeam = Optional.ofNullable(entityManager.find(Teams.class, teamsId));

                Player player = selectPlayer.get();
                team = selectTeam.get();
                team.getNumberOfPlayerList().remove(0);
                player.setTeams(null);
                player.setTeamName(null);
                transaction.commit();

                return true;

            } catch(Exception e){
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally{
                entityManager.close();
            }
            return false;
        }
    }
