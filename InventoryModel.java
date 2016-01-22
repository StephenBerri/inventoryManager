/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMVCModels;
import JavaMVCControllers.InventoryController;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Stephen
 */
public class InventoryModel {
    //class variables
static Product[] products = new Product[10000]; //the array of products in the database
static int emptyIndex;  //the first empty index in the database
    

    
    public void buildData () throws IOException{
        //check if files exists.. if not create it
        if(!(new File("C:\\Users\\Stephen\\My Documents\\Objects.txt").isFile())) {

            Files.createFile(Paths.get("C:\\Users\\Stephen\\My Documents\\Objects.txt"));
        }

       
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader("C:\\Users\\Stephen\\My Documents\\Objects.txt"))) {
            // Access the data from the file
            // Create a new StringBuilder
            StringBuilder string = new StringBuilder();
            
            // Read line-by-line
            String line = reader.readLine();
            String[] object;
            int numberOf = 0;

            while(numberOf < products.length) {
//cycle through the lines, filling variables from the strings in the file, until a null line is found
                if(line != null){        
                object = line.split("`");
                products[numberOf] = new Product(Integer.parseInt(object[0]),object[1],Float.parseFloat(object[2]),Integer.parseInt(object[3]),Integer.parseInt(object[4]),Integer.parseInt(object[5]), Integer.parseInt(object[6]));
                }
                else{//after a null line is full, fastforward the loop
                    emptyIndex = numberOf;
                    numberOf = products.length;
                    System.out.println("num of indexes:" + emptyIndex);
                }
                // Read the next line
                numberOf++;
                line = reader.readLine();
            }
popList();  //call popList
        } catch (Exception er) {

        }


    }
    public void popList(){
        //this generates the data that goes in the lists
   InventoryController controller = new InventoryController();
controller.clearList();
    //clears the lists in the view then cycles through the array sending the data to the view    
 for(int i = 0; i < products.length; i++){
     if (products[i] != null){
    System.out.println(products[i].id + "," + products[i].description + "," + products[i].price + "," + products[i].shelfAmt + "," + products[i].backAmt);


  controller.printList(products[i].id, products[i].description, products[i].price, products[i].shelfAmt, products[i].backAmt); 

 } 
 

    }
}
    
    
        public void writeData() throws IOException, FileNotFoundException
    {//writes the array data to the file
        boolean deleted = false;


FileWriter fileWriter = new FileWriter("C:\\Users\\Stephen\\My Documents\\Objects.txt");

BufferedWriter writer = new BufferedWriter(fileWriter);
 {
            //cycle through the array Writeing the data to the File
            for(int i = 0; i < products.length; i++){
            if(products[i] != null){//make sure the index contains data
                if(products[i].id != 0){//this if else checks for an index marked deleted.
            writer.write(Integer.toString(products[i].id) + "`" + products[i].description + "`" + Float.toString(products[i].price) + "`" + Integer.toString(products[i].shelfSpace) + "`" + Integer.toString(products[i].shelfAmt) + "`" + Integer.toString(products[i].backAmt) + "`" + Integer.toString(products[i].cseAmt));
//writer.write("hi");
            writer.newLine();
            }else{
                  deleted = true;  
                }}
            else{//if null fast forward the loop
                i = products.length;
            }}
//actually write the data to the file
writer.flush();
writer.close();
popList();

        } 
if(deleted = true){//if an index was marked deleted clear the arrays and eventually refill them 
    clearProducts();
}
    }

    public String getProd(int selected) {
//lookup the sent index and generate/return a string containing the information
String Prod;
System.out.println("prod lookup" + products[selected]);
Prod = Integer.toString(products[selected].id) + "`" + products[selected].description + "`" + Float.toString(products[selected].price) + "`" + Integer.toString(products[selected].shelfSpace) + "`" + Integer.toString(products[selected].shelfAmt) + "`" + Integer.toString(products[selected].backAmt) + "`" + Integer.toString(products[selected].cseAmt);
//Prod = "jkj`hjg`jhgf`hjg`jhhgf`jhgf`jh"; 
        System.out.println("ProductLookup called" + Prod);
return Prod;
    }

    public void updateDatabase(int selected, String id, String desc, String price, String shelfSpace, String shelfAmt, String backAmt, String caseAmt) throws IOException {
        //this function will change or create new indexes of the array
if(selected == -1){//-1 means create new/everything else is the index to be changed
    selected = emptyIndex;
    emptyIndex++;
    products[selected] = new Product();
}
products[selected].id = Integer.parseInt(id);
products[selected].description = desc;
products[selected].price = Float.parseFloat(price);
products[selected].shelfSpace = Integer.parseInt(shelfSpace);
products[selected].shelfAmt = Integer.parseInt(shelfAmt);
products[selected].backAmt = Integer.parseInt(backAmt);
products[selected].cseAmt = Integer.parseInt(caseAmt);

writeData();
popList();//writes the data to the file and recreates the lists
    }

    public void delProduct(int selected) throws IOException {
//a product id of zero tells the writedata to exclude it from the database
    products[selected].id = 0;
writeData();    
        
        
    }
public void clearProducts() throws IOException{
    //cycles through the array setting each index to null
for(int i = 0; i < emptyIndex + 5; i++){
    products[i] = null;
}
buildData();
}

    public void moveBackstock(int selectedIndex, int numberMoved) throws IOException {
products[selectedIndex].backAmt = products[selectedIndex].backAmt - numberMoved;
products[selectedIndex].shelfAmt = products[selectedIndex].shelfAmt + numberMoved;
writeData();
    }

    public void orderProd(int selectedIndex, int CasesOrdered) throws IOException {
int PiecesOrdered = CasesOrdered * products[selectedIndex].cseAmt;
int EmptyShelfSpace = products[selectedIndex].shelfSpace - products[selectedIndex].shelfAmt;

if(PiecesOrdered < EmptyShelfSpace){
products[selectedIndex].shelfAmt = products[selectedIndex].shelfAmt + PiecesOrdered;    
}else{
    products[selectedIndex].shelfAmt = products[selectedIndex].shelfSpace;
    int toBack = PiecesOrdered - EmptyShelfSpace;
    products[selectedIndex].backAmt = products[selectedIndex].backAmt + toBack;
}
writeData();
    }
    
    public static float runningTotal = 0;

    public void addSaleProd(String text, int quantity) {
int i = 0;
String warning;
   InventoryController controller = new InventoryController();
   
while(i < emptyIndex){
    if(text.equals(Integer.toString(products[i].id)) || text.toUpperCase().equals(products[i].description.toUpperCase())){
            System.out.println("Sold: " + products[i].description);
        if(quantity <= products[i].shelfAmt){
            products[i].shelfAmt = products[i].shelfAmt - quantity;
            runningTotal = runningTotal + products[i].price * quantity;
            float taxTotal = (float) (runningTotal * 1.08);
String desc = products[i].description + " * " + Integer.toString(quantity);
   controller.saletolist(products[i].id, desc, products[i].price * quantity, runningTotal, taxTotal);
   popList();
    i = emptyIndex + 5;
        }else{
            i = emptyIndex + 5;
            warning = "Not enough products on the shelf to complete the order";
            controller.saleWarning(warning);
              
        }
        
   
    }else{
   i++;
   
   if(i == emptyIndex){
       warning = "Product not Found";
       controller.saleWarning(warning);
   }
    }
    
}        
        
    }

    public void endSale() throws IOException {
writeData();
runningTotal = 0;

    }




}
    

