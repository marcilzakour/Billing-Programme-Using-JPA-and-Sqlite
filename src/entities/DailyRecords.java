/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Marcil
 */

/*
    This class represents a record in the daily payments table
    each instance represents a specific day
*/
@Entity
public class DailyRecords implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*list of records saved for a specific day*/
    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> records;

    public DailyRecords() {
        this.records = new ArrayList<>();
        
        dayDate = new Date();
        
        dayDate.setHours(0);
        dayDate.setMinutes(0);
        dayDate.setSeconds(0);
    }

    
    /*the date of the records*/
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dayDate;
    ///GETTERS AND SETTERS
    /**
     * Get the value of dayDate
     *
     * @return the value of dayDate
     */
    public Date getDayDate() {
        return dayDate;
    }

    /**
     * Set the value of dayDate
     *
     * @param dayDate new value of dayDate
     */
    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    /**
     * Get the value of records
     *
     * @return the value of records
     */
    public List<Item> getRecords() {
        return records;
    }

    /**
     * Set the value of records
     *
     * @param records new value of records
     */
    public void setRecords(List<Item> records) {
        this.records = records;
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
        if (!(object instanceof DailyRecords)) {
            return false;
        }
        DailyRecords other = (DailyRecords) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DailyRecords[ id=" + id + " ]";
    }

}
