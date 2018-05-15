package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Marcil
 */
/*
    This Class represents a bill and can be used for both customer bill and Company bill.
    Composition is used here over inheritance since the functionality of Both types of Bills
    are exactly the same.
 */
@Entity
public class Bill implements Serializable {
    /*ID used to uniquily define the entity*/
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*
        each customer may have more than one bill
    */
    @ManyToOne
    private Customer owner;

    /*list of the product items included in the bill*/
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;
    /*the payed amount of the final total*/
    private int payed;
    /*the toal sum of the items cost*/
    private int finalTotal;
    /*type of the bill mainly (customer or company) bill*/
    private BillType billType;

    ///GETTERS AND SETTERS
    /**
     * Get the value of billType
     *
     * @return the value of billType
     */
    public BillType getBillType() {
        return billType;
    }

    /**
     * Set the value of billType
     *
     * @param billType new value of billType
     */
    public void setBillType(BillType billType) {
        this.billType = billType;
    }

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

    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date releaseDate;

    /**
     * Get the value of releaseDate
     *
     * @return the value of releaseDate
     */
    public java.util.Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set the value of releaseDate
     *
     * @param releaseDate new value of releaseDate
     */
    public void setReleaseDate(java.util.Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Get the value of payed
     *
     * @return the value of payed
     */
    public int getPayed() {
        return payed;
    }

    /**
     * Set the value of payed
     *
     * @param payed new value of payed
     */
    public void setPayed(int payed) {
        this.payed = payed;
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
     * Get the value of owner
     *
     * @return the value of owner
     */
    public Customer getOwner() {
        return owner;
    }

    /**
     * Set the value of owner
     *
     * @param owner new value of owner
     */
    public void setOwner(Customer owner) {
        this.owner = owner;
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
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bill[ id=" + id + " ]";
    }
    /*add item to the bill*/
    public void addItem(Item item) {
        /*initialize the list for an empty bill*/
        if (getItems() == null) {
            setItems(new ArrayList<>());
        }
        getItems().add(item);
    }

}
