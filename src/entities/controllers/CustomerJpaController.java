/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Bill;
import entities.Customer;
import entities.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Marcil
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) {
        if (customer.getBills() == null) {
            customer.setBills(new ArrayList<Bill>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Bill> attachedBills = new ArrayList<Bill>();
            for (Bill billsBillToAttach : customer.getBills()) {
                billsBillToAttach = em.getReference(billsBillToAttach.getClass(), billsBillToAttach.getId());
                attachedBills.add(billsBillToAttach);
            }
            customer.setBills(attachedBills);
            em.persist(customer);
            for (Bill billsBill : customer.getBills()) {
                Customer oldOwnerOfBillsBill = billsBill.getOwner();
                billsBill.setOwner(customer);
                billsBill = em.merge(billsBill);
                if (oldOwnerOfBillsBill != null) {
                    oldOwnerOfBillsBill.getBills().remove(billsBill);
                    oldOwnerOfBillsBill = em.merge(oldOwnerOfBillsBill);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getId());
            List<Bill> billsOld = persistentCustomer.getBills();
            List<Bill> billsNew = customer.getBills();
            List<Bill> attachedBillsNew = new ArrayList<Bill>();
            for (Bill billsNewBillToAttach : billsNew) {
                billsNewBillToAttach = em.getReference(billsNewBillToAttach.getClass(), billsNewBillToAttach.getId());
                attachedBillsNew.add(billsNewBillToAttach);
            }
            billsNew = attachedBillsNew;
            customer.setBills(billsNew);
            customer = em.merge(customer);
            for (Bill billsOldBill : billsOld) {
                if (!billsNew.contains(billsOldBill)) {
                    billsOldBill.setOwner(null);
                    billsOldBill = em.merge(billsOldBill);
                }
            }
            for (Bill billsNewBill : billsNew) {
                if (!billsOld.contains(billsNewBill)) {
                    Customer oldOwnerOfBillsNewBill = billsNewBill.getOwner();
                    billsNewBill.setOwner(customer);
                    billsNewBill = em.merge(billsNewBill);
                    if (oldOwnerOfBillsNewBill != null && !oldOwnerOfBillsNewBill.equals(customer)) {
                        oldOwnerOfBillsNewBill.getBills().remove(billsNewBill);
                        oldOwnerOfBillsNewBill = em.merge(oldOwnerOfBillsNewBill);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = customer.getId();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<Bill> bills = customer.getBills();
            for (Bill billsBill : bills) {
                billsBill.setOwner(null);
                billsBill = em.merge(billsBill);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
