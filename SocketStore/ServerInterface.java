public interface ServerInterface{
   //methods the server will use
   //sends a request to the server to order certain items
   public void pressOrder(String item, String amount) throws Exception;

   //tells the server that the state has change customer->employee or
   //eomployee->customer
   public void changePersonState() throws Exception;

   //update the Gui with new information
   public String updateUI() throws Exception;

   //adds new items to the store
   public void orderNew(String name, String price, String amount) throws Exception;
}
