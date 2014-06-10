/**
 * This class is the main form of the CPU simulator project
 * It serves as the User Interface that simulates the console of the CS6461 Computer
 * It hold IPL button to start computer, switches as radio buttons for providing input
 * As the output of the computer, it shows basic registers and their current situations.
 * 
 */

package simulator;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class ControlPanel extends javax.swing.JFrame {
    
    boolean running;
    
    String strSwitches;
    String strCardReader;
    int runType, PROGRAM;
    boolean numericPrint=false;
    private RomLoader mBootLoader;
    
    
    // These Registers variables are instantited from Register Class
    // and used as Instruction Register, OPCODE register and ADDR register
    // other Registers (General Purpose Registers, Index Registers) are created on the GUI
    Register IR, OPCODE, ADDR, PC, CC,COUNT, MBR, MSR, MFR;
    
    // This variables are instantited from File class
    // and used to store Register File and Index Register File
    File XF, RF, I, T;
    
    public static Memory MEMORY;
    ALU ALU;
    
    Instruction Instructions; 
    
    PipelinedInstruction PipelinedSingleInstruction = new PipelinedInstruction(1,this); // thread for running a single Instructions
    
    /**
     * instructions running type
     * 1 - without pause
     * 2 - pause between instructions
     * 3 - pause between micro steps
     * 4 - pause for some time (Haven't implement yet)
     * 5 - single Instructions mode
     */
    
    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                char key = e.getKeyChar();
                if (key >= 'a' && key <= 'z') key=(char) (key-32);
                if ((key >= '1' && key <= 'Z')){
                    KEYBORD.jButtonActionPerformed(key);
                } else if (key == KeyEvent.VK_ENTER){
                    KEYBORD.jButtonCRActionPerformed();
                }
            } 
            return false;
        }
    }
                   
    public ControlPanel() {
        
        initComponents();
      
        OPCODE = new Register("OPCODE", 6, false);
        ADDR = new Register("ADDR", 8, false);
        IR = new Register("IR", 20, false);
        MBR = new Register("MBR", 20, false);
        PC = new Register("PC", 13, false);
        CC = new Register("CC", 4, false);
        COUNT = new Register("COUNT", 5, false);
        MSR = new Register("MSR", 20, false);
        MFR = new Register("MFR", 4, false);

        MEMORY = new Memory();
        ALU = new ALU();
        XF =  new File();
        RF = new File();
        I = new File();
        T = new File();
        mBootLoader=new RomLoader(this);
        
        jRbActionPerformed(null);
        
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
   
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jFrame5 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        btnIPL = new javax.swing.JToggleButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        GPR0 = new simulator.Register("R0", 20, true);
        GPR1 = new simulator.Register("R1", 20, true);
        FR2 = new simulator.Register("FR2", 20, true);
        GPR3 = new simulator.Register("R3", 20, true);
        IX1 = new simulator.Register("IX1", 13, true);
        IX2 = new simulator.Register("IX2", 13, true);
        IX3 = new simulator.Register("IX3", 13, true);
        MAR = new simulator.Register("MAR", 20, true);
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jRadioButton16 = new javax.swing.JRadioButton();
        jRadioButton17 = new javax.swing.JRadioButton();
        jRadioButton18 = new javax.swing.JRadioButton();
        jRadioButton19 = new javax.swing.JRadioButton();
        jRadioButton20 = new javax.swing.JRadioButton();
        btnSingleStep = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jMessages = new javax.swing.JTextArea();
        label = new javax.swing.JLabel();
        lblOpcode = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblPC = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblCC = new javax.swing.JLabel();
        btnClr = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lblCC2 = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        KEYBORD = new simulator.Input();
        PRINTER = new simulator.Output();
        GPR2 = new simulator.Register("R2", 20, true);
        FR1 = new simulator.Register("FR1", 20, true);
        jRbProgram1 = new javax.swing.JRadioButton();
        jRbProgram2 = new javax.swing.JRadioButton();
        jRbFloatProgram = new javax.swing.JRadioButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame5Layout = new javax.swing.GroupLayout(jFrame5.getContentPane());
        jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame5Layout.setVerticalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        btnIPL.setText("IPL");
        btnIPL.setToolTipText("");
        btnIPL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIPLActionPerformed(evt);
            }
        });

        GPR0.setBackground(java.awt.Color.lightGray);
        GPR0.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GPR0.setToolTipText("");
        GPR0.setBitLength(20);
        GPR0.setOpaque(false);
        GPR0.setRegName("R0");

        GPR1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GPR1.setRegName("R1");

        FR2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FR2.setRegName("FR2");

        GPR3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GPR3.setRegName("R3");

        IX1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        IX1.setRegName("IX1");

        IX2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        IX2.setRegName("IX2");

        IX3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        IX3.setRegName("IX3");

        MAR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        MAR.setRegName("MAR");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setToolTipText("");
        jPanel1.setName("aa"); // NOI18N

        jRadioButton1.setText("1");
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jRadioButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton2.setText("2");
        jRadioButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton3.setText("3");
        jRadioButton3.setToolTipText("");
        jRadioButton3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton4.setText("4");
        jRadioButton4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton5.setText("5");
        jRadioButton5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton6.setText("6");
        jRadioButton6.setToolTipText("");
        jRadioButton6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton7.setText("7");
        jRadioButton7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton8.setText("8");
        jRadioButton8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton9.setText("9");
        jRadioButton9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton10.setText("10");
        jRadioButton10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton11.setText("11");
        jRadioButton11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton12.setText("12");
        jRadioButton12.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton13.setText("13");
        jRadioButton13.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton14.setText("14");
        jRadioButton14.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton15.setText("15");
        jRadioButton15.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton16.setText("16");
        jRadioButton16.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton17.setText("17");
        jRadioButton17.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton18.setText("18");
        jRadioButton18.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton19.setText("19");
        jRadioButton19.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jRadioButton20.setText("20");
        jRadioButton20.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        btnSingleStep.setText("Run Single Instruction (Step by Step)");
        btnSingleStep.setToolTipText("Click here to run this instruction by micro steps");
        btnSingleStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSingleStepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSingleStep)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14)
                        .addComponent(jRadioButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton9)
                    .addComponent(jRadioButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jRadioButton11)
                    .addComponent(jRadioButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton13)
                    .addComponent(jRadioButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton16)
                    .addComponent(jRadioButton17)
                    .addComponent(jRadioButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton19)
                    .addComponent(jRadioButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(btnSingleStep))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMessages.setEditable(false);
        jMessages.setColumns(10);
        jMessages.setRows(5);
        jMessages.setAutoscrolls(false);
        jScrollPane1.setViewportView(jMessages);

        label.setBackground(new java.awt.Color(130, 125, 204));
        label.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        label.setText("FIELD ENGINEER'S CONSOLE");

        lblOpcode.setText("000000");

        jLabel13.setText("OPCODE:");

        jLabel14.setText("PC:");

        lblPC.setText("000000");

        jLabel15.setText("CC:");

        lblCC.setText("0000");

        btnClr.setText("Clear");
        btnClr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClrActionPerformed(evt);
            }
        });

        jLabel1.setText("INST 1");

        jTextField1.setBackground(new java.awt.Color(153, 255, 51));
        jTextField1.setText(" IF");

        jTextField2.setBackground(new java.awt.Color(153, 255, 51));
        jTextField2.setText(" ID");

        jTextField3.setBackground(new java.awt.Color(153, 255, 51));
        jTextField3.setText("EXE");

        jTextField6.setBackground(new java.awt.Color(153, 255, 51));
        jTextField6.setText("EXE");

        jTextField5.setBackground(new java.awt.Color(153, 255, 51));
        jTextField5.setText("ID");

        jTextField4.setBackground(new java.awt.Color(153, 255, 51));
        jTextField4.setText("IF");

        jLabel2.setText("INST 2");

        jLabel3.setText("INST 3");

        jTextField7.setBackground(new java.awt.Color(153, 255, 51));
        jTextField7.setText("IF");

        jTextField8.setBackground(new java.awt.Color(153, 255, 51));
        jTextField8.setText("ID");

        jTextField9.setBackground(new java.awt.Color(153, 255, 51));
        jTextField9.setText("EXE");

        jLabel17.setText("MFR:");

        lblCC2.setText("0000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(label)
                        .addGap(48, 48, 48))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblOpcode))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblPC)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClr))
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCC)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCC2))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField5)
                                            .addComponent(jTextField2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField1, jTextField2, jTextField3});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField7, jTextField8, jTextField9});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnClr)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lblOpcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPC, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(lblCC)
                            .addComponent(jLabel17)
                            .addComponent(lblCC2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        label1.setBackground(new java.awt.Color(130, 125, 204));
        label1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        label1.setText("REGISTERS");

        label2.setBackground(new java.awt.Color(130, 125, 204));
        label2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        label2.setText("Press IPL to run");

        GPR2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GPR2.setRegName("R2");

        FR1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FR1.setRegName("FR1");

        buttonGroup1.add(jRbProgram1);
        jRbProgram1.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jRbProgram1.setForeground(new java.awt.Color(104, 79, 232));
        jRbProgram1.setText("Program-1");
        jRbProgram1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRbActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRbProgram2);
        jRbProgram2.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jRbProgram2.setForeground(new java.awt.Color(104, 79, 232));
        jRbProgram2.setSelected(true);
        jRbProgram2.setText("Program-2");
        jRbProgram2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRbActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRbFloatProgram);
        jRbFloatProgram.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jRbFloatProgram.setForeground(new java.awt.Color(104, 79, 232));
        jRbFloatProgram.setText("Pipelined Program");
        jRbFloatProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(KEYBORD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1)
                            .addComponent(GPR0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IX2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IX3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GPR3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FR2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnIPL, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRbProgram1)
                                .addGap(27, 27, 27)
                                .addComponent(jRbProgram2)
                                .addGap(30, 30, 30)
                                .addComponent(jRbFloatProgram)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PRINTER, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(971, 971, 971))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PRINTER, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRbProgram1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRbProgram2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRbFloatProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(KEYBORD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GPR0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GPR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GPR2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GPR3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FR2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IX1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IX2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IX3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

       
    /**
     * stop the process from running state
     * @param stop runing reason to be displayed
     */
    public void stopRunning(String msg) {
    	
    	this.running = false;
        jMessages.append("Stop running:"+msg+"\n");

    }
    
    
    /**
     * start booting from virtual card(file specified)
     * @param start runing details to be displayed
     */
    public void startRunning(String msg){
    	this.running = true;
      	jMessages.append("Booting from:"+msg+"\n");
    	
    }
    
     /**
     * This action when btnIPL button is clicked starts the Simulator
     * The rest of the function is left for the later phases.
     */
    private void btnIPLActionPerformed(java.awt.event.ActionEvent evt) {    
        
        SetInitialValues();
       
        
        if (this.running) {
          //stop running the bootloader if it's reading file,or stop the processor if it's executing instructions
           getmBootLoader().stopRunning("Turn off by user.");
            
        }
        else {
        	try {                        
			getmBootLoader().readCard(strCardReader);
				
			} catch (Exception e) {
				stopRunning("wrong instruction error:"+e.toString());
			}
        }
    }                                      

    private void InitialMemory(Integer DATA, Integer ADDR) {
        
        String sDATA, sADDR;
        sDATA = Long.toBinaryString(DATA);
        sDATA = String.format("%0" + (20-sDATA.length())+ "d", 0) + sDATA;
        sADDR = Long.toBinaryString(ADDR);
        sADDR = String.format("%0" + (20-sADDR.length())+ "d", 0) + sADDR;
        MEMORY.setDirect(sDATA, sADDR);
    
    
    }
    
    private void SetInitialValues(){
    
         /**
          * Initial values for the program-1 for base-addressing and some constant values 
          * such as ASCII of Carriage Return (13), 1, 10
         */
        if(PROGRAM==1) {
            InitialMemory(20,150);
            InitialMemory(20,249);
            InitialMemory(100,151);
            InitialMemory(528,201);
            InitialMemory(545,202);
            InitialMemory(535,203);
            InitialMemory(13,155);
            InitialMemory(1,153);
            InitialMemory(10,154);
            InitialMemory(10,144);
            InitialMemory(160,158);
            InitialMemory(0,200);
            InitialMemory(126,145);
            InitialMemory(160,143);
            InitialMemory(111111,11);

            IX3.set(Long.toBinaryString(511));
            IX1.set(Long.toBinaryString(0));
            IX2.set(Long.toBinaryString(160)); 
            
             // Send Cursor to the Printer (_?)
            PRINTER.Clear();
            PRINTER.GetData("1011111", false);
            PRINTER.GetData("111111", false);
        }
        else if (PROGRAM==2) {
                 /**
         * Initial values for the program-2 for base-addressing  the vector addresses
         */
      
        InitialMemory(0,90);
        InitialMemory(140,99);
        InitialMemory(150,100);
        InitialMemory(160,101);
        InitialMemory(170,102);
        InitialMemory(180,103);
        InitialMemory(190,104);
        InitialMemory(200,105);
        InitialMemory(210,106);
        InitialMemory(220,107);
        InitialMemory(230,108);
        InitialMemory(240,109);
        InitialMemory(13,110); // CR=13
        InitialMemory(25, 115);
                
        IX3.set(Long.toBinaryString(511));
        IX1.set(Long.toBinaryString(100));
        IX2.set(Long.toBinaryString(160));
        
        // Send Cursor to the Printer (_?)
        PRINTER.Clear();
        PRINTER.GetData("1011111", false);
        PRINTER.GetData("111111", false);
        
       
        }
        
        else if (PROGRAM==3) {
            PRINTER.Clear();
            IX3.set(Long.toBinaryString(511));
            InitialMemory(3,100);
        }
   
    }
    
    /**
     * This action when btnSingleStep button is clicked runs the Instructions which 
     * is set by switches (radio button
     * After switch values are read, Instruction Register is set with this Instructions to be executed
     * Then, Instructions is decoded, and related function is called.
     */    
    private void btnSingleStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSingleStepActionPerformed
        
               
        runType = 5; // single Instructions mode
        if (!PipelinedSingleInstruction.isStarted) {
            PipelinedSingleInstruction = new PipelinedInstruction(1,this);
            strSwitches = GetSwitchValues();
            PipelinedSingleInstruction.start();

            }
        else {
            if (!PipelinedSingleInstruction.isRunning) {
                PipelinedSingleInstruction.resume();
                PipelinedSingleInstruction.isRunning = true;
            }
            else {
                jMessages.append("Error: Cannot continue!\nThread is running already");
            }
        }
        
     
    }//GEN-LAST:event_btnSingleStepActionPerformed

    private void btnClrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClrActionPerformed
      jMessages.setText("");
    }//GEN-LAST:event_btnClrActionPerformed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        
          KEYBORD.jButtonActionPerformed(evt.getKeyChar());
    }//GEN-LAST:event_formKeyTyped

    private void jRbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRbActionPerformed
     
        if (jRbProgram1.isSelected()) {
            PROGRAM=1;
            runType = 1;  
            strCardReader= "program1.txt";
            numericPrint = true;
            
       }
        else if (jRbProgram2.isSelected()) {
            PROGRAM=2;
            runType = 1;  
            strCardReader= "program2.txt";
            numericPrint = false;
            
       }
         else if (jRbFloatProgram.isSelected()) {
            PROGRAM=3;
            runType = 4;  
            strCardReader= "program3.txt";
            numericPrint = true;
            
       }
        
    }//GEN-LAST:event_jRbActionPerformed


      
    /**
     * This function gets the the input by reading radio button values
     * @return all values in a binary string
     */
    public String GetSwitchValues() {
        
        strSwitches = "";
        
        strSwitches += jRadioButton1.isSelected() ? "1" : "0";
        strSwitches += jRadioButton2.isSelected() ? "1" : "0";
        strSwitches += jRadioButton3.isSelected() ? "1" : "0";
        strSwitches += jRadioButton4.isSelected() ? "1" : "0";
        strSwitches += jRadioButton5.isSelected() ? "1" : "0";
        strSwitches += jRadioButton6.isSelected() ? "1" : "0";
        strSwitches += jRadioButton7.isSelected() ? "1" : "0";
        strSwitches += jRadioButton8.isSelected() ? "1" : "0";
        strSwitches += jRadioButton9.isSelected() ? "1" : "0";
        strSwitches += jRadioButton10.isSelected() ? "1" : "0";
        strSwitches += jRadioButton11.isSelected() ? "1" : "0";
        strSwitches += jRadioButton12.isSelected() ? "1" : "0";
        strSwitches += jRadioButton13.isSelected() ? "1" : "0";
        strSwitches += jRadioButton14.isSelected() ? "1" : "0";
        strSwitches += jRadioButton15.isSelected() ? "1" : "0";
        strSwitches += jRadioButton16.isSelected() ? "1" : "0";
        strSwitches += jRadioButton17.isSelected() ? "1" : "0";
        strSwitches += jRadioButton18.isSelected() ? "1" : "0";
        strSwitches += jRadioButton19.isSelected() ? "1" : "0";
        strSwitches += jRadioButton20.isSelected() ? "1" : "0";
        
        return strSwitches;
    }
    
    public final void Key(char c) {
        
        KEYBORD.jButtonActionPerformed(c);
    
    }
     
 
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
       /* Create and display the form */
     //   SwingUtilities.invokeLater(new Runnable() {
               
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final ControlPanel ControlPanel = new ControlPanel();
               

                ControlPanel.setVisible(true);
                              
               
            }
        });
        
    }
    
  
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private simulator.Register FR1;
    private simulator.Register FR2;
    private simulator.Register GPR0;
    private simulator.Register GPR1;
    private simulator.Register GPR2;
    private simulator.Register GPR3;
    private simulator.Register IX1;
    private simulator.Register IX2;
    private simulator.Register IX3;
    private simulator.Input KEYBORD;
    private simulator.Register MAR;
    private simulator.Output PRINTER;
    private javax.swing.JButton btnClr;
    private javax.swing.JToggleButton btnIPL;
    public static javax.swing.JButton btnSingleStep;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JTextArea jMessages;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton17;
    private javax.swing.JRadioButton jRadioButton18;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton20;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JRadioButton jRbFloatProgram;
    private javax.swing.JRadioButton jRbProgram1;
    private javax.swing.JRadioButton jRbProgram2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel label;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    public static javax.swing.JLabel lblCC;
    public static javax.swing.JLabel lblCC2;
    private javax.swing.JLabel lblOpcode;
    public static javax.swing.JLabel lblPC;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the lblOpcode
     */
    public javax.swing.JLabel getLblOpcode() {
        return lblOpcode;
    }

    /**
     * @param lblOpcode the lblOpcode to set
     */
    public void setLblOpcode(javax.swing.JLabel lblOpcode) {
        this.lblOpcode = lblOpcode;
    }

    /**
     * @return the mBootLoader
     */
    public RomLoader getmBootLoader() {
        return mBootLoader;
    }

    /**
     * @param mBootLoader the mBootLoader to set
     */
    public void setmBootLoader(RomLoader mBootLoader) {
        this.mBootLoader = mBootLoader;
    }

    /**
     * @return the GPR0
     */
    public simulator.Register getGPR0() {
        return GPR0;
    }

    /**
     * @param GPR0 the GPR0 to set
     */
    public void setGPR0(simulator.Register GPR0) {
        this.GPR0 = GPR0;
    }

    /**
     * @return the GPR1
     */
    public simulator.Register getGPR1() {
        return GPR1;
    }

    /**
     * @param GPR1 the GPR1 to set
     */
    public void setGPR1(simulator.Register GPR1) {
        this.GPR1 = GPR1;
    }

    /**
     * @return the GPR2
     */
    public simulator.Register getGPR2() {
        return GPR2;
    }

    /**
     * @param GPR2 the GPR2 to set
     */
    public void setGPR2(simulator.Register GPR2) {
        this.GPR2 = GPR2;
    }

    /**
     * @return the GPR3
     */
    public simulator.Register getGPR3() {
        return GPR3;
    }

    /**
     * @param GPR3 the GPR3 to set
     */
    public void setGPR3(simulator.Register GPR3) {
        this.GPR3 = GPR3;
    }

    /**
     * @return the IX1
     */
    public simulator.Register getIX1() {
        return IX1;
    }

    /**
     * @param IX1 the IX1 to set
     */
    public void setIX1(simulator.Register IX1) {
        this.IX1 = IX1;
    }

    /**
     * @return the IX2
     */
    public simulator.Register getIX2() {
        return IX2;
    }

    /**
     * @param IX2 the IX2 to set
     */
    public void setIX2(simulator.Register IX2) {
        this.IX2 = IX2;
    }

    /**
     * @return the IX3
     */
    public simulator.Register getIX3() {
        return IX3;
    }

    /**
     * @param IX3 the IX3 to set
     */
    public void setIX3(simulator.Register IX3) {
        this.IX3 = IX3;
    }

    /**
     * @return the MAR
     */
    public simulator.Register getMAR() {
        return MAR;
    }

    /**
     * @param MAR the MAR to set
     */
    public void setMAR(simulator.Register MAR) {
        this.MAR = MAR;
    }

    /**
     * @return the MBR
     */
    public simulator.Register getMBR() {
        return MBR;
    }

    /**
     * @param MBR the MBR to set
     */
    public void setMBR(simulator.Register MBR) {
        this.MBR = MBR;
    }
    
     /**
     * @return the KEYBORD
     */
    public simulator.Input getKEYBORD() {
        return KEYBORD;
    }

    /**
     * @param KEYBORD the KEYBORD to set
     */
    public void setKEYBORD(simulator.Input KEYBORD) {
        this.KEYBORD = KEYBORD;
    }
    
    /**
     * @return the PRINTER
     */
    public simulator.Output getPRINTER() {
        return PRINTER;
    }

    /**
     * @param PRINTER the PRINTER to set
     */
    public void setPRINTER(simulator.Output PRINTER) {
        this.PRINTER = PRINTER;
    }
    
     public void setPipelineColor(int n,Color color){
        if (n==1){
        jTextField1.setBackground(color);
        } else if (n==2){
        jTextField2.setBackground(color);
        } else if (n==3){
        jTextField3.setBackground(color);}
        else if (n==4){
        jTextField4.setBackground(color);}
        else if (n==5){
        jTextField5.setBackground(color);}
        else if (n==6){
        jTextField6.setBackground(color);}
        else if (n==7){
        jTextField7.setBackground(color);}
    else if (n==8){
        jTextField8.setBackground(color);}
        else if (n==9){
        jTextField9.setBackground(color);}
    }
     
      public void setPC(int num,String pcAddr){
        if (num==1){
        jLabel1.setText(pcAddr);}
        else if (num==3){
            jLabel2.setText(pcAddr);}
            else 
            jLabel3.setText(pcAddr);
       
    }
}
