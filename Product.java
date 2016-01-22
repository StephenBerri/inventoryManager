/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMVCModels;

/**
 *
 * @author Stephen
 */
public class Product {
    int id;
    String description;
    float price;
    int shelfSpace;
    int shelfAmt;
    int backAmt;
    int cseAmt;

    public Product(int prodCode, String desc, float unitPrice, int space, int shelf, int back, int cse ) {
       id = prodCode;
       description = desc;
       price = unitPrice;
       shelfSpace = space;
       shelfAmt = shelf;
       backAmt = back;
       cseAmt = cse;
        
    }

    Product() {

    }
    
}
