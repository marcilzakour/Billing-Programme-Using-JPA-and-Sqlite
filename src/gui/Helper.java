/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.BillType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcil
 */
/*this class contains static methods used for validation, debugging and logging purposes*/
/*note error message boolean parameter is added for future use*/
public class Helper {
    //existed for future use and constraints
    private static final String productRegex = ".";
    private static final String costRegex = ".";
    private static final String quantityRegex = ".";
    private static final Pattern productPattern = Pattern.compile(productRegex);
    private static final Pattern costPattern = Pattern.compile(costRegex);
    private static final Pattern quantityPattern = Pattern.compile(quantityRegex);
    public static  PrintStream out;
    //check if product name is none-empty
    public static boolean isValidName(String ProducName) {
        return ProducName.length() > 0;
//        return productPattern.matcher(ProducName).matches();
    }
    // check is cost is a none-negative integer
    public static boolean isValidCost(String cost) {
        try {
            return Integer.parseInt(cost) >= 0;
        } catch (NumberFormatException ex) {
            return false;
        }
//        return costPattern.matcher(cost).matches();
    }
    //check if quantity is an integer
    public static boolean isValidQuantity(String quantity) {
        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
//         return quantityPattern.matcher(quantity).matches();
    }
    //raise an error message dialog to the user and add the result to the log file
    public static void error(Object ex) {
        if(ex instanceof Exception){Helper.exeption((Exception)ex);}
        
        JOptionPane.showMessageDialog(null, "error: " + ex, "Error!", JOptionPane.ERROR_MESSAGE);
    }
    //show normal message
    public static void message(Object msg) {

        JOptionPane.showMessageDialog(null, "تنبيه:" + msg);
    }
    
    //wrapper arount the original function to add log info
    public static boolean isValidCost(String cost, boolean errorMessage) {
        if (isValidCost(cost)) {
            return true;
        }
        error("المبالغ المالية يجب ان يكون أرقام موجبة");
        return false;
    }
    //wrapper arount the original function to add log info	
    public static boolean isValidQuantity(String quantity, boolean errorMessage) {
        if (isValidQuantity(quantity)) {
            return true;
        }
        error("الكميات يجب ان تكون أرقام موجبة");
        return false;
    }
    //wrapper arount the original function to add log info
    public static boolean isValidName(String ProducName, boolean errorMessage) {
        if (isValidName(ProducName)) {
            return true;
        }
        error("الاسم غير صحيح");
        return false;
    }
    //show message to the user to confirm creating new product
    public static boolean confirmCreateProduct(String productName) {

        return JOptionPane.showConfirmDialog(null, "المنتج " + productName + " غير موجود هل تريد اضافته؟") == JOptionPane.YES_OPTION;
    }
    //show message to the user to confirm creating new customer/company
    public static boolean confirmCreateCustomer(String customerName) {
        return JOptionPane.showConfirmDialog(null, "الزبون " + customerName + " غير موجود هل تريد اضافته؟") == JOptionPane.YES_OPTION;
    }
    //add bill to the storage
    public static boolean confirmAddBillItemsToStorage(String name, BillType billType) {
        String x = "";
        if (billType == BillType.companyBill) {
            x = "هل تريد اضافة سجلات فاتورة الشركة " + name + " الى المستودع؟";
        } else if (billType == BillType.customerBill) {
            x = "هل تريد انقاص محتويات فاتورة الزبون " + name + " من مواد المستودع؟";
        }
        return JOptionPane.showConfirmDialog(null, x) == JOptionPane.YES_OPTION;
    }

    static void exeption(Exception ex) {

    }
    //write exception message to the log file
    static void logException(Exception ex) {
        out.println(new Date().toString()+"   :    "+ex+"\n------------------------------------------------------\n");
    }
}
