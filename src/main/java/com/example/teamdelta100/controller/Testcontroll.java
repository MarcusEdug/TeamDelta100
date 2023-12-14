package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Teams;
import com.example.teamdelta100.entities.TestPlay;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Testcontroll {

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    public boolean save (TestPlay player){
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
    public List<TestPlay> getAll(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<TestPlay> TestPlayListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<TestPlay> resultList = entityManager.createQuery("FROM TestPlay", TestPlay.class);
            TestPlayListToReturn.addAll(resultList.getResultList());
            transaction.commit();
            if(printOut){
                for (TestPlay player :
                        TestPlayListToReturn) {
                    System.out.println(player.getId() + ". " + player.getName());
                }
            }
            return TestPlayListToReturn;
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
    public boolean deleteTeams(TestPlay teams){
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
    public boolean updateTeams(TestPlay player){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(player);
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
}
