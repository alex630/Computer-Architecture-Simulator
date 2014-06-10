/**
 * This class simulates the output (printer) of our simulator.
 * It prints the data available to the label.
 */

package simulator;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Output extends javax.swing.JPanel {
    
    private JPanel jPanel = new javax.swing.JPanel();
    private JLabel jLabel = new javax.swing.JLabel();
    private String OUTPUT;
    public String STATUS="1"; //0: BUSY 1:AVAIBLE
    
    public Output() {
        
       OUTPUT = "";
       
       initComponents();
     
    }
    
    /**
     * This method get data to be printed from the registers.And shows as a label.
     * @param s data to be printed.
     */
    public void GetData(String s, boolean numeric) {
        STATUS = "0";
        int i = Integer.parseInt(s, 2);
        char c = (char) i;
        if (c==13) 
            OUTPUT += String.format("%s","<br>_ ");
        else {
            if (!numeric)
                OUTPUT = OUTPUT + String.format("%s",c);
            else
                OUTPUT = OUTPUT + String.format("%s",i) + "<br>";
        }
            //OUTPUT += String.format("%s%s",(numeric)?i:c, (numeric)?"<br>":"");
        
        
        jLabel.setText("<html>" + OUTPUT + "</html>");
        STATUS = "1";
     
    }
    
    public void Clear(){
         OUTPUT = "";
    }
    
   
    
    /**
     * This is the initialization for the printer panel. 
     */
    public void initComponents() {
        
        this.add(jPanel);
        this.add(jLabel);
         
        jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Printer", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.LEADING, new java.awt.Font("Verdana", 1, 11), java.awt.Color.blue)); // NOI18N
        jPanel.setFont(new java.awt.Font("Verdana", 1, 8)); // NOI18N
        jLabel.setFont(new java.awt.Font("Verdana", 1, 11));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 88, Short.MAX_VALUE)
            .addComponent(jLabel)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jLabel)
        );
        
         jPanel.setPreferredSize(new Dimension(180, 710));
     
     }
    
}
