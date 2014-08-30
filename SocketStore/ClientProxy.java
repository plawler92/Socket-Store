import java.net.*;  //the package for networking code
import java.io.*;   //the package for input/output, similar to iostream
import java.util.*; //utility package to get the current date and time

public class ClientProxy {
   public static void main(String[] args) throws Exception{
      ServerInterface server = new Store();
      InetAddress ip;
      try{
        ip = InetAddress.getLocalHost();
        System.out.println("Current IP address : " + ip.getHostAddress());
      }catch(Exception e){}


      //create the server socket passing the port number to listen for. The TCPServer
      //will create this socket and sit and listen for requests on the port
      ServerSocket welcomeSocket = new ServerSocket(55556);

      //usually a server runs 24/7
        while(true)
        {
            Socket mySocket = welcomeSocket.accept();

            DataOutputStream outToClient = new DataOutputStream(mySocket.getOutputStream());

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            String requestFromClient = inFromClient.readLine();

            System.out.println("Request: " + requestFromClient);

            //splits incoming requests into seperate strings
            String[] s = requestFromClient.split(":");

            //examines the string for which method is being called
            if(s[0].equalsIgnoreCase("pressOrder")){
              server.pressOrder(s[1], s[2]);
              outToClient.writeBytes("Request Accepted\n");
            }
            else if(s[0].equalsIgnoreCase("changePersonState")){
              server.changePersonState();
              outToClient.writeBytes("Request Accepted\n");
            }
            else if(s[0].equalsIgnoreCase("updateUI")){
              String str = server.updateUI();
              outToClient.writeBytes(str + "\n");
            }
            else if(s[0].equalsIgnoreCase("orderNew")){
              server.orderNew(s[1],s[2],s[3]);
              outToClient.writeBytes("Request Accepted\n");
            }
            //if the request doesn't match anything else or is denied for some other reason
            else{
              outToClient.writeBytes("Invalid Request\n");
            }

            mySocket.close();
        }
   }

}
