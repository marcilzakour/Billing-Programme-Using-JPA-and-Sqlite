
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Marcil
 */
/*
    This class represents a customer, in the system customsers are bill owners
*/
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    /*ID used to uniquily define the entity*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    public Customer() {
    }

    
    public Customer(String id) {
        this.id = id;
    }
    /* list of bills owned by the customer (or company)*/
    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<Bill> bills;
    ///GETTERS AND SETTERS
    /**
     * Get the value of bills
     *
     * @return the value of bills
     */
    public List<Bill> getBills() {
        return bills;
    }

    /**
     * Set the value of bills
     *
     * @param bills new value of bills
     */
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Customer[ id=" + id + " ]";
    }


}
