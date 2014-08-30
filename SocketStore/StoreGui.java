import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

public class StoreGui extends JFrame implements ActionListener{
   JButton buy;
   JTextArea area;
   JButton order;
   JButton info;
   JPanel main;
   JPanel choosers;
   JPanel values;
   JPanel buttons;
   JPanel radios;
   JPanel labels;
   JRadioButton customer;
   JRadioButton employee;
   ArrayList<JLabel> inven_items;
   ArrayList<JCheckBox> check_boxes;
   ArrayList<JTextField> text_fields;
   String cust_order;
   ServerInterface server;

   public StoreGui(ServerInterface s){
      super("Store");
      server = s;
      cust_order = "";

      buildGui();
      setSize(400,250);
      setLocation(100,300);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   //used to update the required amount of labels/textfields/checkboxes
   private void updateGui(){
      getContentPane().remove(main);
      inven_items = new ArrayList<JLabel>();
      check_boxes = new ArrayList<JCheckBox>();
      text_fields = new ArrayList<JTextField>();
      try{
         String[] list = server.updateUI().split("\n");

      for(int i = 0; i < list.length; i++){
         check_boxes.add(new JCheckBox());
         text_fields.add(new JTextField());
         inven_items.add(new JLabel(list[i]));
         //text_fields.get(i).setEnabled(false);
      }
      choosers = new JPanel();
      choosers.setLayout(new GridLayout(list.length , 2));
      labels = new JPanel();
      labels.setLayout(new GridLayout(list.length, 2));
      for(int i = 0; i < check_boxes.size(); i++){
         labels.add(inven_items.get(i));
         choosers.add(check_boxes.get(i));
         choosers.add(text_fields.get(i));
      }
      }catch(Exception e){}

      JPanel main = new JPanel();
      main.setLayout(new GridLayout(1,2));
      main.add(labels);
      main.add(choosers);
      main.add(buttons);
      getContentPane().add(main);
      getContentPane().validate();

   }


   private void buildGui(){


      //sets up labels/checkboxes/textfields and adds them to Jpanels
      inven_items = new ArrayList<JLabel>();
      check_boxes = new ArrayList<JCheckBox>();
      text_fields = new ArrayList<JTextField>();
      try{
         String[] list = server.updateUI().split("\n");

      for(int i = 0; i < list.length; i++){
         check_boxes.add(new JCheckBox());
         text_fields.add(new JTextField());
         inven_items.add(new JLabel(list[i]));
         //text_fields.get(i).setEnabled(false);
      }
      choosers = new JPanel();
      choosers.setLayout(new GridLayout(list.length , 2));
      labels = new JPanel();
      labels.setLayout(new GridLayout(list.length, 2));
      for(int i = 0; i < check_boxes.size(); i++){
         labels.add(inven_items.get(i));
         choosers.add(check_boxes.get(i));
         choosers.add(text_fields.get(i));
      }
      }catch(Exception e){}


      //sets up the buttons, adds to Jpanel
      order = new JButton("Order");
      order.addActionListener(this);
      info = new JButton("Info");
      info.addActionListener(this);
      buttons = new JPanel();
      buttons.setLayout(new GridLayout(2,1));
      buttons.add(order);
      buttons.add(info);

      //adds radio buttons
      customer = new JRadioButton("Customer");
      customer.setSelected(true);
      customer.setEnabled(false);
      customer.addActionListener(this);
      employee = new JRadioButton("Employee");
      employee.addActionListener(this);
      radios = new JPanel();
      radios.setLayout(new GridLayout(1,2));
      radios.add(customer);
      radios.add(employee);


      //adds everything to the JFrame
      main = new JPanel();
      main.setLayout(new GridLayout(1,2));
      main.add(labels);
      main.add(choosers);
      main.add(buttons);
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(radios, BorderLayout.NORTH);
      getContentPane().add(main);

   }

   public void actionPerformed(ActionEvent e){
      try{
         //if order is pressed, send in an order requests for every selected checkbox
         //with an amount given by the corresponding textfield
      if(e.getSource() == order){

         for(int i = 0; i < check_boxes.size(); i++){
            if(check_boxes.get(i).isSelected()){

               server.pressOrder(Integer.toString(i), text_fields.get(i).getText());
               String[] str = inven_items.get(i).getText().split(" ");
               int new_amount = 0;

               //if in customer state, add information to the customer order and subtract
               //from amount in the JLabel
               if(customer.isSelected()){
                  if(Integer.parseInt(str[2]) > Integer.parseInt(text_fields.get(i).getText())){
                     new_amount = Integer.parseInt(str[2]) - Integer.parseInt(text_fields.get(i).getText());
                     cust_order += text_fields.get(i).getText() + " " + str[0] + " at $" +
                        Integer.toString(Integer.parseInt(str[1])*Integer.parseInt(text_fields.get(i).getText())) + " total." + "\n";
                  }
                  //error handling if you request more than the store has
                  else{
                     new_amount = 0;
                     cust_order += str[2] + " " + str[0] + " at $" +
                        Integer.toString(Integer.parseInt(str[1])*Integer.parseInt(str[2])) + " total." + "\n";
                  }
               }
               //if in employee state, add amount requested to amount in JLabel
               else if(employee.isSelected()){
                  new_amount = Integer.parseInt(str[2]) + Integer.parseInt(text_fields.get(i).getText());
               }
               //puts new_amount in JLabel
               inven_items.get(i).setText(str[0] + " " + str[1] + " " + Integer.toString(new_amount));
               check_boxes.get(i).setSelected(false);
               text_fields.get(i).setText("");
            }
         }
      }
      else if(e.getSource() == info){
         //if in employee state a request is sent to the server to add a new item to the store
         if(employee.isSelected()){
            String name = JOptionPane.showInputDialog("Enter Item Name: ");
            String price = JOptionPane.showInputDialog("Enter Price: ");
            String amount = JOptionPane.showInputDialog("Enter Amount to Order: ");
            server.orderNew(name, price, amount);
            //updates the GUI with new labels/checkboxes/textfields
            updateGui();
         }
         //if in customer state, shows your order info in Joptionpanes
         else if(customer.isSelected()){
            System.out.println(cust_order);
            JOptionPane.showMessageDialog(null, cust_order, "Your Order", JOptionPane.PLAIN_MESSAGE);
         }
      }
      //changes to customer state
      else if(e.getSource() == customer && customer.isSelected()){
         //change state pattern

         employee.setSelected(false);
         employee.setEnabled(true);
         customer.setEnabled(false);
         server.changePersonState();
         info.setLabel("Info");
      }
      //changes to employee state
      else if(e.getSource() == employee && employee.isSelected()){
         //change state pattern
         customer.setSelected(false);
         customer.setEnabled(true);
         employee.setEnabled(false);
         server.changePersonState();
         info.setLabel("Add Item");
      }
      }catch(Exception a){}
   }
}
