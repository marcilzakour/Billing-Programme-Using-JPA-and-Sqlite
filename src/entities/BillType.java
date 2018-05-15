package entities;

/**
 *
 * @author Marcil
 */
/*type of the bill
   we may have:
   1) customer bill which is between the customer and the user (the customer buys products from the user
   2) company bill which is between the user and the company (the user buys products from the company)
   3) None is used as a temporary type when the type of the bill is decided yet.
*/
public enum BillType {
    customerBill,companyBill,NONE
}
