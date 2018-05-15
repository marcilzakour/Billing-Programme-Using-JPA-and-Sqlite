/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Marcil
 */
/*this class represents the warehouse of the user*/
@Entity
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    /*the storage contain many products
      we maintain productdts to allow modifying the existing items in the storage
    */
    @OneToMany
    private List<Product> products;
    /*the items maps the products into quantities*/
    @OneToMany
    private List<Item> items;
    /*the total amount of many*/
    private BigInteger cache;


    public Storage() {
        this.id = 1l;
        this.products = new ArrayList<>();
        this.items = new ArrayList<>();
        this.cache = BigInteger.ZERO;
        
    }
    ///SETTERS AND GETTERS
    /**
     * Get the value of cache
     *
     * @return the value of cache
     */
    public BigInteger getCache() {
        return cache;
    }

    /**
     * Set the value of cache
     *
     * @param cache new value of cache
     */
    public void setCache(BigInteger cache) {
        this.cache = cache;
    }

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Get the value of products
     *
     * @return the value of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Set the value of products
     *
     * @param products new value of products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storage)) {
            return false;
        }
        Storage other = (Storage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Storage[ id=" + id + " ]";
    }

    public synchronized void modifyCacheBy(int finalTotal) {
         setCache(getCache().add(new BigInteger(finalTotal+"")));
    }

}
