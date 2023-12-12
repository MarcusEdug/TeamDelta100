package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Teams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("har nått finally");
            entityManager.close();
        }
        return false;
    }
    public List<Teams> getAll(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Teams> teamsListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Teams> resultList = entityManager.createQuery("FROM Teams", Teams.class);
            teamsListToReturn.addAll(resultList.getResultList());
            transaction.commit();
            if(printOut){
                for (Teams teams :
                        teamsListToReturn) {
                    System.out.println(teams.getId() + ". " + teams.getName() + ". Antal medlemmar " + teams.getNumberOfPlayer());
                }
            }
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
            // If the entity is attached then remove customer, else merge(attach/update) entity and then remove
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
            // If the entity is attached then remove customer, else merge(attach/update) entity and then remove
            //entityManager.remove(entityManager.contains(Id) ? Id :entityManager.merge(Id));
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
    public List<Teams> tableUpdate(boolean printOut){
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
}