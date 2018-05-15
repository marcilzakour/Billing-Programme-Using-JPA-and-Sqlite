/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Marcil
 */
/*
    this class represent a product which can be added to storage 
*/
@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    /*the price may be fluctuating over time however the previous records should not be deleted*/
    private int currentPrice;
    /*the amount in the storage*/
    private int storedQuantity;
    /*an integer used to show the most important products first*/ 
    private int priority;
    ///SETTERS AND GETTERS
    /**
     * Get the value of priority
     *
     * @return the value of priority
     */
    public int getPriority() {
        return priority;
    }

    public void increasePriority(int by){
        priority += by;
    }
    /**
     * Set the value of priority
     *
     * @param priority new value of priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Product(String id, int currentPrice) {
        this.id = id;
        this.currentPrice = currentPrice;
    }

    
    /**
     * Get the value of storedQuantity
     *
     * @return the value of storedQuantity
     */
    public int getStoredQuantity() {
        return storedQuantity;
    }

    /**
     * Set the value of storedQuantity
     *
     * @param storedQuantity new value of storedQuantity
     */
    public void setStoredQuantity(int storedQuantity) {
        this.storedQuantity = storedQuantity;
    }

    public Product() {
    }
    public Product(String id){
        this(id,0,0);
    }
    public Product(String id, int currentPrice,int storedQuantity) {
        this.id = id;
        this.currentPrice = currentPrice;
        this.storedQuantity = storedQuantity;
        this.priority = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   
    
    /**
     * Get the value of currentPrice
     *
     * @return the value of currentPrice
     */
    public int getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Set the value of currentPrice
     *
     * @param currentPrice new value of currentPrice
     */
    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public void increaseQuantity(int quantity) {
        this.storedQuantity += quantity;
    }
    
}
