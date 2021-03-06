/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.controllers;

import entities.Bill;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Customer;
import entities.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import entities.BillType;
/**
 *
 * @author Marcil
 */
public class BillJpaController implements Serializable {

    public BillJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bill bill) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer owner = bill.getOwner();
            if (owner != null) {
                owner = em.getReference(owner.getClass(), owner.getId());
                bill.setOwner(owner);
            }
            em.persist(bill);
            if (owner != null) {
                owner.getBills().add(bill);
                owner = em.merge(owner);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bill bill) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bill persistentBill = em.find(Bill.class, bill.getId());
            Customer ownerOld = persistentBill.getOwner();
            Customer ownerNew = bill.getOwner();
            if (ownerNew != null) {
                ownerNew = em.getReference(ownerNew.getClass(), ownerNew.getId());
                bill.setOwner(ownerNew);
            }
            bill = em.merge(bill);
            if (ownerOld != null && !ownerOld.equals(ownerNew)) {
                ownerOld.getBills().remove(bill);
                ownerOld = em.merge(ownerOld);
            }
            if (ownerNew != null && !ownerNew.equals(ownerOld)) {
                ownerNew.getBills().add(bill);
                ownerNew = em.merge(ownerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = bill.getId();
                if (findBill(id) == null) {
                    throw new NonexistentEntityException("The bill with id " + id + " no longer exists.");
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
            Bill bill;
            try {
                bill = em.getReference(Bill.class, id);
                bill.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bill with id " + id + " no longer exists.", enfe);
            }
            Customer owner = bill.getOwner();
            if (owner != null) {
                owner.getBills().remove(bill);
                owner = em.merge(owner);
            }
            em.remove(bill);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bill> findBillEntities() {
        return findBillEntities(true, -1, -1);
    }

    public List<Bill> findBillEntities(int maxResults, int firstResult) {
        return findBillEntities(false, maxResults, firstResult);
    }

    private List<Bill> findBillEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bill.class));
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

    public Bill findBill(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bill.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bill> rt = cq.from(Bill.class);
            cq.select(em.getCriteriaBuilder().count(rt));

            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Bill> findBills(Predicate... conditions) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bill> rt = cq.from(Bill.class);
            cq.select(rt);
            cq.where(conditions);
            
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Bill> findBills(BillType billType){
        EntityManager em = getEntityManager();
        TypedQuery<Bill> query = em.createQuery("SELECT b FROM Bill b WHERE b.billType= :btype",Bill.class);
        query.setParameter("btype", billType);
        return query.getResultList();
    }
}
