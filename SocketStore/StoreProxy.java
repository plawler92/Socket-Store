import java.net.*;  //the package for networking code
import java.io.*;   //the package for input/output, similar to iostream

public class StoreProxy implements ServerInterface{
   String ip;
   public StoreProxy(String address){
      ip = address;
   }

   //sends requests to the server
   public void pressOrder(String item, String amount) throws Exception{
      Socket clientSocket = new Socket(ip, 55556);

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


      outToServer.writeBytes("pressOrder:" + item + ":" + amount + "\n");

      String responseFromServer = inFromServer.readLine();

      System.out.println(responseFromServer);

      clientSocket.close();
   }

   public void changePersonState() throws Exception{
      Socket clientSocket = new Socket(ip, 55556);

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      outToServer.writeBytes("changePersonState\n");

      System.out.println(inFromServer.readLine());
      clientSocket.close();
   }

   public String updateUI() throws Exception{
      Socket clientSocket = new Socket(ip, 55556);

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      outToServer.writeBytes("updateUI\n");

      String line = inFromServer.readLine();
      String responseFromServer = "";
      while(line != null){
         responseFromServer += line + "\n";
         line = inFromServer.readLine();
      }

      clientSocket.close();

      return responseFromServer;
   }

   public void orderNew(String name, String price, String amount) throws Exception{
      Socket clientSocket = new Socket(ip, 55556);

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      outToServer.writeBytes("orderNew:" + name + ":" + price + ":" + amount + "\n");

      String responseFromServer = inFromServer.readLine();
      System.out.println(responseFromServer);

      clientSocket.close();
   }

}
