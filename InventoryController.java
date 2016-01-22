/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMVCControllers;
import JavaMVCViews.*;
import JavaMVCModels.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Stephen
 */
public class InventoryController {
public InventoryModel model = new InventoryModel();

public void startApplication() {
InventoryView view = new InventoryView();   
view.setVisible(true);
//this is the first function called.. it calls the buildData fuinction in the model and makes the gui visible
    try {
        model.buildData();
    } catch (IOException ex) {
        Logger.getLogger(InventoryController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    

    


    public void printList(int id, String description, float price, int shelfAmt, int backAmt) {
//this is sent from the model

InventoryView view = new InventoryView(); 

view.updateList(id,description,price,shelfAmt,backAmt);
    }

    public String lookupProduct(int selected) {
return model.getProd(selected);//string from model to view
    }

    public void updateDatabase(int selected, String text, String text0, String text1, String text2, String text3, String text4, String text5) throws IOException {
        //from view to model
model.updateDatabase(selected, text, text0, text1, text2, text3, text4, text5);
    }

    public void clearList() {
    //from model to view
        InventoryView view = new InventoryView(); 
    view.ClearLists();

    }

    public void deleteProduct(int selected) throws IOException {
//from view to model
        model.delProduct(selected);
    }

    public void moveBackstock(int selectedIndex, String text) throws IOException {
int numberMoved = Integer.parseInt(text);
model.moveBackstock(selectedIndex, numberMoved);
    }

    public void OrderProd(int selectedIndex, String text) throws IOException {
int CasesOrdered = Integer.parseInt(text);
model.orderProd(selectedIndex, CasesOrdered);
    }

    public void addProduct(String text, String text0) {
int quantity = Integer.parseInt(text0);
        model.addSaleProd(text, quantity);
    }

    public void saletolist(int id, String description, float price, float runningTotal, float taxTotal) {
          System.out.println("cont" + description);
             InventoryView view = new InventoryView(); 
             
             view.sale(id, description, price, runningTotal, taxTotal);
        
    }

    public void saleWarning(String warning) {
        InventoryView view = new InventoryView(); 
    view.saleWarning(warning);
    }

    public void endSale() throws IOException {
model.endSale();
    }
}

