//requests from customers get sent here

public class Customer extends State{
   private static Customer instance = null;
   private Customer(){

   }
   //creates and stores one instance of this state
   public static Customer getInstance(){
      if(instance == null){
         instance = new Customer();
      }
      return instance;
   }

   //updates the store with how many items less the customer ordered
   public String pressOrder(String item, String amount, String inventory){

      String[] str = inventory.split("\n");
      String[] str_item = str[Integer.parseInt(item)].split(" ");

      //takes from stock, if there are more requested than in stock the stock gets set to zero
      if(Integer.parseInt(amount) < Integer.parseInt(str_item[2])){
         str_item[2] = Integer.toString(Integer.parseInt(str_item[2]) - Integer.parseInt(amount));
      }
      else
         str_item[2] = "0";

      //puts updated info back in original string
      str[Integer.parseInt(item)] = str_item[0] + " " + str_item[1] + " " + str_item[2];

      String full_string = "";
      for(int i = 0; i < str.length; i++){

         full_string += str[i] + "\n";

      }
      //sends the update inventory back to the store
      return full_string;
   }

   //changes from customer state to employee state
   public State changePersonState(){
      State s = Employee.getInstance();
      return s;

   }
}
