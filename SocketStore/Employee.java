//requests made from eomployees get sent here

public class Employee extends State{
   private static Employee instance = null;
   private Employee(){

   }
   //used so only one instance is created/stored
   public static Employee getInstance(){
      if(instance == null){
         instance = new Employee();
      }
      return instance;
   }

   //restocks the store with new items
   public String pressOrder(String item, String amount, String inventory){

      String[] str = inventory.split("\n");
      String[] str_item = str[Integer.parseInt(item)].split(" ");

      //finds the correct store item and updates it with the amount to be added
      str_item[2] = Integer.toString(Integer.parseInt(str_item[2]) + Integer.parseInt(amount));

      //puts the updated information back in the original string
      str[Integer.parseInt(item)] = str_item[0] + " " + str_item[1] + " " + str_item[2];

      String full_string = "";
      for(int i = 0; i < str.length; i++){

         full_string += str[i] + "\n";

      }
      //returns the full, update inventory
      return full_string;

   }

   //changes from employee state to customer state
   public State changePersonState(){
      State s = Customer.getInstance();
      return s;
   }

}

