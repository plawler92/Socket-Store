import java.io.*;
import java.util.*;

//store class held on the server
public class Store implements ServerInterface{

   private State personState;
   BufferedReader br;
   File file;

   String inventory;


   public Store(){
      //opens up the store inventory file
      File f = new File("store.txt");

      //reads the file into the inventory string
      try{
         file = f;
         inventory = "";
         br = new BufferedReader(new FileReader(file));

         String line = br.readLine();
         while(line != null){
            String[] s = line.split(";;");
            inventory +=s [0] + " " + s[1] + " " + s[2] + "\n";
            line = br.readLine();
         }
         br.close();
      }catch(Exception e){
         System.out.println("error");
      }

      //default state is the customer state
      personState = Customer.getInstance();

   }

   //switches from one state to the other
   public void changePersonState(){

      personState = personState.changePersonState();

   }

   public void pressOrder(String num, String amount){

      //sends the inventory string along with which item should be update and by how many units
      inventory = personState.pressOrder(num, amount, inventory);

      //displays transactions serverside
      System.out.println(inventory);

      //writes the update inventory to the file
      String writeString = "";
      String[] str = inventory.split("\n");
      for(int i = 0; i < str.length; i++){
         String[] st = str[i].split(" ");
         writeString += st[0] + ";;" + st[1] + ";;" + st[2] + "\n";
      }
      try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(writeString);
      bw.close();
      }catch(Exception e){}

   }

   //sends the inventory to the Client Gui to update amount of labels/checkboxes/textfields
   public String updateUI(){
      return inventory;
   }

   //updates the inventory with new items to be added
   public void orderNew(String name, String price, String amount){

      //adds the new item to the end of the inventory
      inventory += name + " " + price + " " + amount + "\n";

      //displays the new inventory
      System.out.println(inventory);

      //writes the new inventory to the inventory file
      String writeString = "";
      String[] str = inventory.split("\n");
      for(int i = 0; i < str.length; i++){
         String[] st = str[i].split(" ");
         writeString += st[0] + ";;" + st[1] + ";;" + st[2] + "\n";
      }
      try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(writeString);
      bw.close();
      }catch(Exception e){}
   }
}
