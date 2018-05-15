/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Marcil
 */
/*this class represents an item which is used in both Bills and Storages
  the item contains a specific product a long with its quantity and price
*/
@Entity
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*used to refere to a product
      we are using String instead of Product because the user should be able to 
      create items with products that are not yet existed.
    */
    private String productName;
    /*the price of the product*/
    private int cost;
    /*the amount*/
    private int quantity;
    /*finalTotal allows making discounts on the total item*/
    private int finalTotal;

    /**
     * Get the value of finalTotal
     *
     * @return the value of finalTotal
     */
    public int getFinalTotal() {
        return finalTotal;
    }

    /**
     * Set the value of finalTotal
     *
     * @param finalTotal new value of finalTotal
     */
    public void setFinalTotal(int finalTotal) {
        this.finalTotal = finalTotal;
    }

    public Item(Long id, String productName, int cost, int quantity, int finalTotal) {
        this.id = id;
        this.productName = productName;
        this.cost = cost;
        this.quantity = quantity;
        this.finalTotal = finalTotal;
    }

    
    public Item(String productName, int cost, int quantity, int finalTotal) {
        this();
        this.productName = productName;
        this.cost = cost;
        this.quantity = quantity;
        this.finalTotal = finalTotal;
    }


    public Item(String productName, int cost, int quantity) {
        this();
        this.productName = productName;
        this.cost = cost;
        this.quantity = quantity;
        this.finalTotal = cost * quantity;
    }
    ///SETTERS AND GETTERS
    /**
     * Get the value of cost
     *
     * @return the value of cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Set the value of cost
     *
     * @param cost new value of cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Get the value of productName
     *
     * @return the value of productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Set the value of productName
     *
     * @param productName new value of productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Item() {
    }

    /**
     * Get the value of quantity
     *
     * @return the value of quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the value of quantity
     *
     * @param quantity new value of quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * calculate the cost of this item
     *
     * @return total cost of this item
     */
    public int calcTotalCost() {
        return cost * quantity;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BillItem[ id=" + id + " ]";
    }

}
