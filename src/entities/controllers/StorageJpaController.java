/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.controllers;

import entities.Storage;
import entities.controllers.exceptions.NonexistentEntityException;
import entities.controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Marcil
 */
public class StorageJpaController implements Serializable {

    public StorageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Storage storage) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(storage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStorage(storage.getId()) != null) {
                throw new PreexistingEntityException("Storage " + storage + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Storage storage) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            storage = em.merge(storage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = storage.getId();
                if (findStorage(id) == null) {
                    throw new NonexistentEntityException("The storage with id " + id + " no longer exists.");
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
            Storage storage;
            try {
                storage = em.getReference(Storage.class, id);
                storage.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The storage with id " + id + " no longer exists.", enfe);
            }
            em.remove(storage);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Storage> findStorageEntities() {
        return findStorageEntities(true, -1, -1);
    }

    public List<Storage> findStorageEntities(int maxResults, int firstResult) {
        return findStorageEntities(false, maxResults, firstResult);
    }

    private List<Storage> findStorageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Storage.class));
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

    public Storage findStorage(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Storage.class, id);
        } finally {
            em.close();
        }
    }

    public int getStorageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Storage> rt = cq.from(Storage.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
