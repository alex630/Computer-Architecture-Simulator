/**
 * This class simulates the Registers
 * Any kind of registers such as General Purpose Registers, 
 * Index Registers etc can be instantiated from this class
 * The bit length of register (bitLength) must be set while instantiating
 * If the register instantiated wants to be showed on the GUI, 
 * a boolean (visible) value must be set as true
 * Class involves radio-boxes to show the content as switches of the register
 * A label also shows the content of the register as decimal
 * A text box and Deposit button are added to load Register directly
 * The purpose of this Deposit button is to make testing easier.
 */


package simulator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Register extends javax.swing.JPanel {
    
    int bitLength;
    String name, value;
    boolean visible;
    private javax.swing.JRadioButton jrb;
     
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblValue;
    private javax.swing.JTextField txtDeposit;
    private javax.swing.JButton btnDeposit;
    private HashMap componentMap;
       

    /**
     * This constructor instantiates a new register 
     * @param l_name The name of the register; can be GPR1, IX2
     * @param l_bitLength The size of the register. 20 bits for GPRs, 13 bits for XRs  
     * @param l_visible If register will be showed on the GUI, this must be set as True
     */
    public Register(String l_name, int l_bitLength, boolean l_visible) {
        this.bitLength = l_bitLength;
        this.name = l_name;
        this.visible = l_visible;
        this.value = "0";        
        
        if (this.visible)  { 
            componentMap = new HashMap<String,Component>();
            initComponents();
       }
                 
    }
    
    public Register() {
        this.value = "0";    
        initComponents();
    }
    
    public void setRegName(String strName) {
        lblName.setText(strName);
        this.name = strName;
    }
    
    public void setBitLength(int l_bitLength) {
       this.bitLength = l_bitLength;
    }
    
     public void setVisible(boolean l_visible) {
       this.visible = l_visible;
    }
    
    /**
     * This function loads a value to registers.
     * @param l_value is string of 0s and 1s.
     * If length of this value is less than registers size, it is mapped with 0s
     * If length of the this value is bigger than registers size, 0s headed are omitted
     */
    public void set(String l_value) {
        this.value = (this.bitLength-l_value.length()>0) ? String.format("%0" + (this.bitLength-l_value.length())+ "d", 0) + l_value: l_value.substring(l_value.length()-bitLength, l_value.length()) ;
      
        if (this.visible)  showRadioBoxes();
       
    }
    
    /**
     * This method is used to set the first big a this register to "1" or "0" to represent the positive
     * or negative.
 
     * @param l_sign 
     */
    public void setSignBit(String l_sign){
        this.value = l_sign + this.value.substring(1,this.bitLength);
        
        if (this.visible)  showRadioBoxes();
    }
    
       /**
     * This method is used to set signal bit in any register. 
     * 
     * @param l_bit is the actual bits in the register. For example, if you want to change the first bit of R1, you should use 
     * R1.setBit(1,"1"), not R1.setBit(0,"1").
     * @param l_value 
     */
    
     public void setBit(int l_bit,String l_value){
        int registerlength = this.bitLength;
        
        String changedString = "";
        if (l_bit ==0){
           changedString = l_value+this.get().substring(1, registerlength);
        } else if (l_bit==this.bitLength){
           changedString = this.get().substring(0, registerlength-1)+l_value;
        } else {
           changedString = this.get().substring(0, l_bit-1)+l_value+this.get().substring(l_bit,registerlength);
        }
        
        //String test = changedString;
    
        this.set(changedString);
    }
    
    /**
     * This function returns the content of register 
     * @return value  the register holds as binary string 
     */
    public String get() {
        return this.value;
    }
   
    /**
     * @param Bit of the register data asked, starts from 1
     * @return content of the index asked
     * This function returns 1 bit from the content of register
     */
    public int get(int Bit) {
        return Integer.parseInt(this.value.substring(this.value.length()-Bit, this.value.length()-Bit + 1));
    }
    
   /**
    * This function reads the value register holds and set the associated radio boxes
    * Radio boxes are searched through the component and set selected if value is 1
    */
    public void showRadioBoxes() {
       // if negative value
      if (this.value.charAt(0) == '1')
          lblValue.setText("-" + String.format("%s",Integer.parseInt(this.value.substring(1, this.value.length()),2)));
      else
        lblValue.setText(String.format("%s",Integer.parseInt(this.value,2)));
        
       for(int i=0; i<this.bitLength;i++) 
           getComponentByName("jRB"  + String.format("%s",i+1)).setSelected((this.value.charAt(i) != '0'));
      }
    
    /**
     * This function places the component on the GUI
     * Switches (radio buttons) are placed automatically according to register bit length
     */
    public void initComponents() {
      
      Dimension d = new Dimension(33,18);
        
      lblName = new JLabel();
      lblValue = new JLabel();
      txtDeposit = new JTextField();
      btnDeposit = new javax.swing.JButton("Deposit");
      lblValue.setForeground(Color.red);
      this.add(lblName);
      
                 
      for(int i=1; i<=this.bitLength;i++) {
           jrb = new JRadioButton();
           jrb.setName("jRB" + i);
           jrb.setVisible(true);
           
           this.add(jrb);
           componentMap.put(jrb.getName(), jrb);
      }
      
      this.add(lblValue);
      lblName.setText("R");
      lblValue.setText("VAL");
      lblValue.setPreferredSize(d);
      this.add(txtDeposit);
      this.add(btnDeposit);
      
            
      txtDeposit.setColumns(3);
      btnDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositActionPerformed(evt);
            }
        });
       
    
    }
    
    /**
     * This function is added to make test easier
     * The value of the textbox is deposit directly on the register 
     * while this action is performed
     * @param evt 
     */
    private void btnDepositActionPerformed(java.awt.event.ActionEvent evt) {                                         
      String userInput = txtDeposit.getText();
      int userInputInt;
      String userInputBinary="";
      userInputInt = Math.abs(Integer.parseInt(userInput));
       userInputBinary = Long.toBinaryString(userInputInt);
     
      this.set(userInputBinary);
      //if negative
      if (userInput.charAt(0) == '-') this.setSignBit("1");
     
    }   
    
   
   /**
    * This function finds the radio button to be selected
    * @param name The name of the radio button
    * @return found radio button
    */ 
   public JRadioButton getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
                return (JRadioButton) componentMap.get(name);
        }
        else return null;
    }
   
   
}
