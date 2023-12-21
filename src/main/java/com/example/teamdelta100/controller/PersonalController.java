package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Personal;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.view.PersonalFX;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalController {

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");


    public boolean save (Personal personal){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(personal);
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

    public List<Personal> getAll(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Personal> personalList = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Personal> resultList = entityManager.createQuery("FROM Personal ", Personal.class);
            personalList.addAll(resultList.getResultList());
            transaction.commit();
            if(printOut){
                for (Personal personal :
                        personalList) {
                    System.out.println(personal.getId() + ". " + personal.getPerName() + ". " + personal.getPerLname());
                }
            }
            return personalList;
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
    public List<Personal> getAll() {
        return getAll(true); // Set default value to true (print results)
    }

    public List<Personal> tableUpdate(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Personal> personalList = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Personal> resultList = entityManager.createQuery("FROM Personal ", Personal.class);
            personalList.addAll(resultList.getResultList());
            System.out.println(personalList.size());
            transaction.commit();
            return personalList;
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
    public boolean deletePersonalById(int Id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Personal personal = entityManager.find(Personal.class, Id);
            if(personal != null){
                entityManager.remove(personal);
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
    public Personal getPersonalById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Personal personal = entityManager.find(Personal.class, id);
            transaction.commit();
            return personal;
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
    public boolean updatePersonal(Personal personal){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(personal);
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
}
