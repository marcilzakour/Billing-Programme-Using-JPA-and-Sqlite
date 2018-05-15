/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import entities.Bill;
import entities.BillType;
import entities.Storage;
import entities.controllers.BillJpaController;
import entities.controllers.CustomerJpaController;
import entities.controllers.DailyRecordsJpaController;
import entities.controllers.ItemJpaController;
import entities.controllers.ProductJpaController;
import entities.controllers.StorageJpaController;
import javax.persistence.Persistence;

/**
 *
 * @author Marcil
 */
public class Main {

    public final static String JPA_UNIT_NAME = "APPU";

    public static void main2(String[] args) throws Exception {
        
//        StorageJpaController storageController = new StorageJpaController(Persistence.createEntityManagerFactory(JPA_UNIT_NAME));
//        ItemJpaController itemController = new ItemJpaController(Persistence.createEntityManagerFactory(JPA_UNIT_NAME));
//        ProductJpaController productController = new ProductJpaController(Persistence.createEntityManagerFactory(JPA_UNIT_NAME));
//        CustomerJpaController customerController = new CustomerJpaController(Persistence.createEntityManagerFactory(JPA_UNIT_NAME));
//        BillJpaController billController = new BillJpaController(Persistence.createEntityManagerFactory(JPA_UNIT_NAME));
//        DailyRecordsJpaController dailyRecordsController = new DailyRecordsJpaController(Persistence.createEntityManagerFactory(JPA_UNIT_NAME));
//       
//        
//        Storage storage = storageController.findStorage(1l);
//        
//        Bill companyBill = new Bill();
//        companyBill.setBillType(BillType.companyBill);
//        companyBill.setOwner(null);
//        billController.create(null);
//        
//        if(storage == null){
//            storage = new Storage();
//            storageController.create(storage);
//        }
        
//        Customer customer = new Customer("مارسيل");
//        customerController.create(customer);
//        
//        Item item = new Item("سومسليكس", 50, 2);
//        itemController.create(item);
//        
//        
//        Bill bill = new Bill();
//        billController.create(bill);
//
//        
//        
//        bill.setOwner(customer);
//        bill.addItem(item);
//        
//        billController.edit(bill);
//        
//        DailyRecords dailyRecords = new DailyRecords();
//        
//        dailyRecordsController.create(dailyRecords);
//        dailyRecords.getRecords().add(item);
//        dailyRecordsController.edit(dailyRecords);
                
//        System.out.println(dailyRecordsController.findDailyRecordsEntities());
        
    }

}
