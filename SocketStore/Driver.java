import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Driver{


   public static void main(String args[]) throws Exception{

      //Creates the store client-side, need to pass in ip address/port
      String ip = JOptionPane.showInputDialog("Enter ip address: ");
      ServerInterface server = new StoreProxy(ip);

      StoreGui g = new StoreGui(server);


   }
}

