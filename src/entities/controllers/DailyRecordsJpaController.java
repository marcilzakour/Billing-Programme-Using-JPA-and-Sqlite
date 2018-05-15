/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.controllers;

import entities.DailyRecords;
import entities.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author Marcil
 */
public class DailyRecordsJpaController implements Serializable {

    public DailyRecordsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DailyRecords dailyRecords) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dailyRecords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DailyRecords dailyRecords) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dailyRecords = em.merge(dailyRecords);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = dailyRecords.getId();
                if (findDailyRecords(id) == null) {
                    throw new NonexistentEntityException("The dailyRecords with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DailyRecords dailyRecords;
            try {
                dailyRecords = em.getReference(DailyRecords.class, id);
                dailyRecords.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dailyRecords with id " + id + " no longer exists.", enfe);
            }
            em.remove(dailyRecords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DailyRecords> findDailyRecordsEntities() {
        return findDailyRecordsEntities(true, -1, -1);
    }

    public List<DailyRecords> findDailyRecordsEntities(int maxResults, int firstResult) {
        return findDailyRecordsEntities(false, maxResults, firstResult);
    }

    private List<DailyRecords> findDailyRecordsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DailyRecords.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DailyRecords findDailyRecords(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DailyRecords.class, id);
        } finally {
            em.close();
        }
    }

    public int getDailyRecordsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DailyRecords> rt = cq.from(DailyRecords.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public DailyRecords findRecordsByDay(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        EntityManager em = getEntityManager();
        TypedQuery<DailyRecords> query = em.createQuery("SELECT s FROM DailyRecords s WHERE :theDate = s.dayDate", DailyRecords.class);
        query.setParameter("theDate", date);
        List<DailyRecords> ret = query.getResultList();
        if(ret.isEmpty())return null;
        else return ret.get(0);
    }

}
