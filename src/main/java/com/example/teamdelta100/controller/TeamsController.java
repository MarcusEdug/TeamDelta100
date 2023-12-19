package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamsController implements Share {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

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
    public List<Teams> getAll(){
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
    public Teams getTeamsById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Teams teamToReturn = entityManager.find(Teams.class, id);
            transaction.commit();
            return teamToReturn;
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
    public boolean deleteTeams(Teams teams){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.contains(teams) ? teams:entityManager.merge(teams));
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
    public boolean deleteTeamsById(int Id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Teams teams = entityManager.find(Teams.class, Id);
            if(teams != null){
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
    public List<Teams> tableUpdate(){
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
    public boolean addPlayerToTeams(int playerId, int teamsId) {
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
                    player.setTeamName(team.getName());
                    team.addPlayer(player);
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
    public boolean removePlayerToTeams(int playerId, int teamsId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        /*entityManager.getTransaction().begin();

        Teams teams = entityManager.find(Teams.class, 1L);
        teams.getNumberOfPlayerList().remove(0);

        entityManager.getTransaction().commit();
        entityManager.close();

         */

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
