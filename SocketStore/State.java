//holds methods the other states will override

public class State{

   public State(){

   }
   public String pressOrder(String item, String amount, String inventory){

      return "";
   }
   public State changePersonState(){
      State s = new State();

      return s;
   }


}
