/***
 * This class is designed to show pipeline implementation of our CSCI 6461 Computer. 
 * All instruction is run under the categories; instruction fetch, instruction decode and execute. 
 * By using timer and thread, it allows consecutive instructions to work in concurrently. 
 * In order to examine the internal step of or the each instructions, 
 * this class extends the thread. By setting the working process from the ControlPanel class
 * internal process of the instructions can be seen step by step while program is working
 */

package simulator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class PipelinedInstruction extends Thread {
    
    /* Contants used to decide the position of pause */
    static final int BEGIN  = 0;    // pause at the beginning
    static final int MIDDLE = 1;    // pause in the process
    static final int END    = 2;    // pause at the end
    
    /* this indicator shows whether running is started */
    boolean isStarted = false;
    
    /* this indicator shows whether this Instructions is done */
    boolean isFinished = false;
    
    /* this indicator shows the status of this instructionThread (running/pause) */
    boolean isRunning = false;
    
    /* this indicator shows whether this instructionThread is sleep */
    boolean isSleep = false;
    
    /* number of instructions left */
    private int instructionNum = 0;
    
    /* parameters from controlPanel */
    private File XF, RF, I, T;
    private Memory MEMORY;
    private ALU ALU;
    private Register IR = new Register("IR", 20, false);;
    private Register OPCODE = new Register("OPCODE", 6, false);
    private Register ADDR = new Register("ADDR", 8, false);
    private Register CC = new Register("CC", 4, false);
    private Register PC = new Register("PC", 13, false);
    private Register COUNT = new Register("COUNT", 5, false);
    private Register DEVID = new Register("DEVID", 4, false);
    private Register MAR, MBR, IX1, IX2, IX3, GPR0, GPR1, GPR2, GPR3;
    private String strSwitches;
    private Input KEYBORD = new Input();
    private Output PRINTER = new Output();
    private Register FR0, FR1;
    private int pipenum;
    private String pfileName = "";
 
    private ControlPanel controlPanel = null;
    
    /* constructor */
    public PipelinedInstruction(int num, ControlPanel controlPanel){
        this.controlPanel = controlPanel;
        this.pipenum = num;
    }
    
    private void executeOneInst(){
        
        if (controlPanel.runType != 5) {
            
            controlPanel.setPipelineColor(pipenum+1, Color.RED);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PipelinedInstruction.class.getName()).log(Level.SEVERE, null, ex);
            }
            controlPanel.setPipelineColor(pipenum+1, Color.GREEN);
        }
                
        isStarted = true;
        isRunning = true;
        isFinished = false;

        instructionNum++;
        String sMsg = String.format("Instruction-%s is in FETCH Stage\n", instructionNum);
        controlPanel.jMessages.append(sMsg);
        IF();
        
        sMsg = String.format("Instruction-%s is in DECODE Stage\n", instructionNum);
        controlPanel.jMessages.append(sMsg);
        ID();
        
        sMsg = String.format("Instruction-%s is in EXECUTION Stage\n", instructionNum);
        controlPanel.jMessages.append(sMsg);
        EXE();
       
        pause(END);
        isFinished = true;
        isRunning = false;
        
       
   
    }
    
    /* Override run() in java.lang.Thread */
    @Override
    public void run() {
        String sAddr;
        
        /* set the value of parameters in instructionThread */
        setXF(controlPanel.XF);
        setRF(controlPanel.RF);
        setI(controlPanel.I);
        setT(controlPanel.T);
        setMEMORY(controlPanel.MEMORY);
        setALU(controlPanel.ALU);
        setIR(controlPanel.IR);
        setOPCODE(controlPanel.OPCODE);
        setADDR(controlPanel.ADDR);
        setCC(controlPanel.CC);
        setPC(controlPanel.PC);
        setMAR(controlPanel.getMAR());
        setMBR(controlPanel.getMBR());
        setIX1(controlPanel.getIX1());
        setIX2(controlPanel.getIX2());
        setIX3(controlPanel.getIX3());
        setGPR0(controlPanel.getGPR0());
        setGPR1(controlPanel.getGPR1());
        setGPR2(controlPanel.getGPR2());       
        setGPR3(controlPanel.getGPR3());
        setStrSwitches(controlPanel.strSwitches);
        setKEYBORD(controlPanel.getKEYBORD());
        setPRINTER(controlPanel.getPRINTER());
        
        sAddr = Long.toBinaryString(512);
        sAddr = String.format("%0" + (20-sAddr.length())+ "d", 0) + sAddr;
        PC.set(sAddr);
        
        if (controlPanel.runType != 5){ // run a program in a file
            
//            timer1 = new Timer (1, action);
//            timer1.start ();
            
            while(!MEMORY.get(PC.get()).substring(0,6).equals("000000")){
                                  
                 ControlPanel.lblPC.setText(String.format("%s", Integer.parseInt(PC.get(),2)));
                    controlPanel.getMAR().set(PC.get());
                    controlPanel.getMBR().set(controlPanel.MEMORY.get(controlPanel.getMAR().get()));
                    strSwitches=controlPanel.MEMORY.get(controlPanel.getMAR().get());
                    
                    controlPanel.setPipelineColor(pipenum, Color.RED);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PipelinedInstruction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    controlPanel.setPipelineColor(pipenum, Color.GREEN);
                    executeOneInst();
                    
                }
                     
            controlPanel.jMessages.append("Boot Completed!\n");
           
        }
        
        else {
            executeOneInst();
        }
        isStarted = false;
       
    }
    
    private void IF(){        
        IR.set(MEMORY.get(PC.get()));
    }
    
    private void ID(){
        
        String opcodeCondition = new String();
       
        if (controlPanel.runType!=5){
        
        opcodeCondition = IR.get().substring(0, 6);
         
        }
        else {
           
            opcodeCondition = strSwitches.substring(0, 6);
        }
    
         if (opcodeCondition.equals("010100") || opcodeCondition.equals("010101")
                  || opcodeCondition.equals("010110") || opcodeCondition.equals("010111")
                  || opcodeCondition.equals("011000") || opcodeCondition.equals("011001")) {
              DecodeArithRtoR();
          } else if (opcodeCondition.equals("011111")|| opcodeCondition.equals("100000")) {
              DecodeLogicalInstruct();
          } else if (opcodeCondition.equals("111101")|| opcodeCondition.equals("111110") || opcodeCondition.equals("111111")) {
              DecodeIOInstruct();
          }
          else {
              Decode();
          }
         controlPanel.getLblOpcode().setText(OPCODE.get());
    
    }
    
    private void EXE(){
        
        switch (Integer.parseInt(OPCODE.get(),2)) {
              case 0:
                 AppendInstructionAsText("HLT");
                 HLT();
                 break;
              case 1:
                 AppendInstructionAsText("LDR");
                 LDR();
                 break;
              case 2 :
                AppendInstructionAsText("STR");
                STR();
                break;
              case 3:
                AppendInstructionAsText("LDA");
                LDA();
                break;
              case 4:
                AppendInstructionAsText("AMR");
                AMR();
                break;    
              case 5:
                 AppendInstructionAsText("SMR");
                 SMR();                
                 break;  
              case 6:
                  AppendInstructionAsText("AIR");
                  AIR();                                 
                 break;  
              case 7:
                   AppendInstructionAsText("SIR");
                   SIR();
                   break; 
              case 8:
                   AppendInstructionAsText("AIX");
                   AIX();
                   break; 
               case 9:
                   AppendInstructionAsText("SIX");
                   SIX();
                   break; 
              case 10:
                   AppendInstructionAsText("JZ");
                   JZ();
                   
                  break; 
              case 11:
                   AppendInstructionAsText("JNE");
                   JNE();
                  break; 
              case 12:
                   AppendInstructionAsText("JCC");
                   JCC();
                  break; 
              case 13:
                  AppendInstructionAsText("JMP");
                   JMP();
                   break;
              case 14:
                   AppendInstructionAsText("JSR");
                   JSR();
                   break;
              case 15:
                   AppendInstructionAsText("RFS");
                   RFS();
                   break;
              case 16:
                   AppendInstructionAsText("SOB");
                   SOB();
                   break;
              case 17:
                   AppendInstructionAsText("JGE");
                   JGE();
                   break;
               case 20:
                   AppendInstructionAsText("MLT");
                   MLT();
                   
                   break;
              case 21:
                   AppendInstructionAsText("DVD");
                   DVD();
                  break;
              case 22:
                   AppendInstructionAsText("TRR");
                   TRR();
                  break;
              case 23:
                  AppendInstructionAsText("AND");
                   AND();
                  break;
              case 24:
                   AppendInstructionAsText("ORR");
                   ORR();
                  break;
              case 25:
                   AppendInstructionAsText("NOT");
                   NOT();
                  break;
              case 31:
                   AppendInstructionAsText("SRC");
                   SRC();
                  break;
              case 32:
                  AppendInstructionAsText("RRC");
                   RRC();
                  break;
              //--------------------------------------------------------------------------------       
          // Floating Points and Vecter Instruction
              case 33:
                  AppendInstructionAsText("FADD");
                  FADD();
                  break;
              case 34:
                  AppendInstructionAsText("FSUB");
                  FSUB();
                  break;
              case 35:
                  AppendInstructionAsText("VADD");
                  VADD();
                  break;
              case 36:
                  AppendInstructionAsText("VSUB");
                  VSUB();
                  break;
              case 37:
                  AppendInstructionAsText("CNVRT");
                  CNVRT();
                  break;
              case 50:
                  AppendInstructionAsText("LDFR");
                  LDFR();
                  break;
              case 51:
                  AppendInstructionAsText("STFR");
                  STFR();
                  break;
           //--------------------------------------------------------------------------------------------      
              case 41:
                  AppendInstructionAsText("LDX");
                   LDX();
                   break;
              case 42: 
                  AppendInstructionAsText("STX");
                  STX();
                  break;
              case 61: 
                  AppendInstructionAsText("IN");
                  IN();
                  break;
              case 62: 
                 AppendInstructionAsText("OUT");
                 OUT();
                 break;
              case 63: 
                  AppendInstructionAsText("CHK");
                  CHK();
                  break;
              default:
                   break;
          }  
         
         ControlPanel.lblPC.setText(String.format("%s", Integer.parseInt(PC.get(),2)));
         
       if (controlPanel.runType != 5) {
         controlPanel.setPipelineColor(pipenum+2, Color.RED);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PipelinedInstruction.class.getName()).log(Level.SEVERE, null, ex);
            }
                controlPanel.setPipelineColor(pipenum+2, Color.GREEN);
           }
    }
        


    public void pause(int pauseType) {
        switch (controlPanel.runType) {
            case 1: // run without stop
                break;
            
            case 2: // pause between instructions
                if (isRunning) {

                    switch (pauseType) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:

                            isRunning = false;
                            this.suspend();     // Suspend instructionThread
                                        
                            while(!isRunning) {  //  wait until the work in main thread
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                else {
                    getControlPanel().jMessages.append("Error: Cannot pause!\nThread is already stopped\n");
                }
                break;
            
            case 3: // pause between micro steps
                if (isRunning) {

                    switch (pauseType) {
                        case 0:
                            break;
                        case 1:
                            isRunning = false;
                            this.suspend();     // Suspend instructionThread
                            while(!isRunning) {  //  wait until the work in main thread
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        case 2:
                            isRunning = false;
                            this.suspend();     // Suspend instructionThread
                            while(!isRunning) {  //  wait until the work in main thread
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                else {
                    getControlPanel().jMessages.append("Error: Cannot pause!\nThread is already stopped\n");
                }
                break;
            
            case 4: // pause for some time
                if (isRunning) {
                    switch (pauseType) {
                        case 0:
                            isSleep = true;
                            try {
                                Thread.sleep(1200);     // wait for some time at the end of instructions
                            } catch (InterruptedException ex) {
                                Logger.getLogger(PipelinedInstruction.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            isSleep = false;
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                }
                else {
                    getControlPanel().jMessages.append("Error: Cannot pause!\nThread is already stopped\n");
                }
                break;
                
            case 5: // single Instructions mode
                if (isRunning) {

                    switch (pauseType) {
                        case 0:
                            break;
                        case 1:
                            //getControlPanel().jMessages.append("Status: \""+strSwitches+"\" is running!\n Click \"Run Single Step\" to continue!\n");
                            ControlPanel.btnSingleStep.setText("Click again to continue...");
                            isRunning = false;
                            this.suspend();     // Suspend instructionThread
                            while(!isRunning) {  //  wait until the work in main thread
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        case 2:
                            isRunning = false;
                            ControlPanel.btnSingleStep.setText("Run Single Instruction (Step by Step)");
                            this.suspend();     // Suspend instructionThread
                            while(!isRunning) {  //  wait until the work in main thread
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                else {
                    getControlPanel().jMessages.append("Error: Cannot pause!\nThread is already stopped\n");
                }
                break;
                    
            default:
                break;
        }
        
    }
    
    private void AppendInstructionAsText(String sInst) {
        
        String sTxt="";
        
        sTxt += sInst + ", ";
        sTxt += String.format("%s","R" + Integer.parseInt(getRF().get(),2) + ", ") ;
        sTxt += (Integer.parseInt(getXF().get(),2) != 0) ? String.format("%s",Integer.parseInt(getXF().get(),2) + ", ") : "";  
        sTxt += (Integer.parseInt(getI().get(),2) != 0) ? String.format("%s",Integer.parseInt(getI().get(),2) + ", ") : "";
        sTxt += (Integer.parseInt(getT().get(),2) != 0) ? String.format("%s",Integer.parseInt(getT().get(),2) + ", ") : "";    
        sTxt += (Integer.parseInt(getADDR().get(),2) != 0) ? String.format("%s",Integer.parseInt(getADDR().get(),2) + ", ") : ""; 
        getControlPanel().jMessages.append(sTxt.substring(0, sTxt.length()-2)  + "\n");
        
    
    }
    
    /**
     * This function decodes Instructions to be executed and assigns the associated Register
     * according the data provided.
     */ 
    private void Decode(){
   
        getOPCODE().set(getStrSwitches().substring(0,6));;
        getXF().set(getStrSwitches().substring(6,8));
        getRF().set(getStrSwitches().substring(8,10));
        getI().set(getStrSwitches().substring(10,11));
        getT().set(getStrSwitches().substring(11,12));
        getADDR().set(getStrSwitches().substring(12,20));
          
    }
    
    private void DecodeLogicalInstruct(){
   
        getOPCODE().set(getStrSwitches().substring(0,6));;
        getRF().set(getStrSwitches().substring(8,10));
        getI().set(getStrSwitches().substring(10,11));
        getT().set(getStrSwitches().substring(11,12));
        getCOUNT().set(getStrSwitches().substring(15,20));
          
    }
    
    
    private void DecodeArithRtoR() {
        getOPCODE().set(getStrSwitches().substring(0, 6));;
        getXF().set(getStrSwitches().substring(6, 8));
        getRF().set(getStrSwitches().substring(8, 10));
    }
    
     private void DecodeIOInstruct() {
        getOPCODE().set(getStrSwitches().substring(0, 6));;
        getXF().set(getStrSwitches().substring(6, 8));
        getRF().set(getStrSwitches().substring(8, 10));
        DEVID.set(getStrSwitches().substring(16,20));
    }
    
    /**
     * This function calculates the effective address
     * It examines the values of Index Register File and Indirecting bit to set 
     * Memory Address Register with the effective address
     */

    
    public void ComputeEffectiveAddress() {
         
      String memoryAddr;
      
        
        if (getXF().get().equals("00") & getI().get().equals("0")) {
            
            getMAR().set(getADDR().get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()));
            pause(MIDDLE);
           
           
        }
       
        else if ((getXF().get().equals("01")||getXF().get().equals("10")||getXF().get().equals("11")) & getI().get().equals("0") )  {  
            
            getALU().OP1.set(getADDR().get());
            getALU().OP2.set(SelectIndexRegister().get());
            getALU().add(); 
            getMAR().set(getALU().RES.get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()));
            pause(MIDDLE);

        }
        
        else if (getXF().get().equals("00") & getI().get().equals("1")) {
            getMAR().set(getADDR().get());
            pause(MIDDLE);
            memoryAddr = getMAR().get();
            getMBR().set(getMEMORY().get(memoryAddr));
            pause(MIDDLE);
            getMAR().set(getMBR().get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()));
            pause(MIDDLE);
     
        }
        
        else {
            getALU().OP1.set(getADDR().get());
            getALU().OP2.set(SelectIndexRegister().get());
            getALU().add();
            getMAR().set(getMEMORY().get(getALU().RES.get()));
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()));
            pause(MIDDLE);

        }
   
   }
    
    /**
     * This function calculates the effective address for the instructions which do not
     * use the index register 
     */
    public void computeEffectiveAddressForNonIndex(){
       
        if (getI().get().equals("0")) {
            getMAR().set(getADDR().get());
            getMBR().set(getMEMORY().get(getMAR().get()));}
        else
            getMAR().set(getMEMORY().get(getADDR().get()));
            getMBR().set(getMEMORY().get(getMAR().get()));
    }
    
     public void HLT() {
        
       PC.set("0");
    
    }
    
    /**
    * Load Register From Memory
    */    
    public void LDR() {
        
        pcIncrease();
        ComputeEffectiveAddress();
        SelectRegister().set(getMBR().get());
        pause(MIDDLE);
    
    }
    
    /**
     * Store register to memory
     */
    public void STR() {
            pcIncrease();

            ComputeEffectiveAddress();
            getMEMORY().set(SelectRegister().get(), getMAR().get());
   
    }
    
    /**
     * Load register with address
     */
     public void LDA() {
         pcIncrease();
         ComputeEffectiveAddress();
         SelectRegister().set(getMAR().get());
         pause(MIDDLE);
         
    }
    
     /**
      * Subtract memory from register
     */
    public void SMR(){
        pcIncrease();
        ComputeEffectiveAddress(); 
        getALU().OP2.set(getMBR().get());
        getALU().OP1.set(SelectRegister().get());
        getALU().sub();
        SelectRegister().set(getALU().RES.get());
        pause(MIDDLE);
        
    }     
    
    /**
     * Add memory to register
     */
    public void AMR(){
        pcIncrease();

        ComputeEffectiveAddress();
        getALU().OP2.set(getMBR().get());
        getALU().OP1.set(SelectRegister().get());
        getALU().add();
        SelectRegister().set(getALU().RES.get());
        pause(MIDDLE);
         
  }     
    
    /**
     * Add immediate to register
     */
     public void AIR() {
         pcIncrease();
          ComputeEffectiveAddress(); 
          getALU().OP2.set(getADDR().get());
    	  getALU().OP1.set(SelectRegister().get());
    	  getALU().add();
    	  SelectRegister().set (getALU().RES.get());
          pause(MIDDLE);
    
    }
    
   /**
    * Subtract immediate from register
    */
    public void SIR(){
          pcIncrease();
          ComputeEffectiveAddress();
    	  getALU().OP2.set(getADDR().get());
    	  getALU().OP1.set(SelectRegister().get());
    	  getALU().sub();
    	  SelectRegister().set (getALU().RES.get());
          pause(MIDDLE);
    }
    
      public void AIX(){
          pcIncrease();
          ComputeEffectiveAddress();
    	  getALU().OP2.set(getADDR().get());
    	  getALU().OP1.set(SelectIndexRegister().get());
    	  getALU().add();
    	  SelectIndexRegister().set (getALU().RES.get());
          pause(MIDDLE);
    }
      
       public void SIX(){
          pcIncrease();
          ComputeEffectiveAddress();
    	  getALU().OP2.set(getADDR().get());
    	  getALU().OP1.set(SelectIndexRegister().get());
    	  getALU().sub();
    	  SelectIndexRegister().set (getALU().RES.get());
          pause(MIDDLE);
    }
    
    /**
     * Load index register from memory
     */
    public void LDX(){
        pcIncrease();
        computeEffectiveAddressForNonIndex();
        SelectIndexRegister().set(getMBR().get());
        pause(MIDDLE);
    
    }
    
    /**
     * Store index register to memory
     */
    public void STX(){
        pcIncrease();
        computeEffectiveAddressForNonIndex();
        getMEMORY().set(SelectIndexRegister().get(), getMAR().get());
    
    }
    
    /**
     * Jump if zero.
     */
    public void JZ(){
        
        ComputeEffectiveAddress();  
       
        if  (SelectRegister().get().equals(String.format("%0" + 20 + "d", 0) )) {
                        
            if (getI().get().equals("0")) 
                getPC().set(getMAR().get());
            else
                getPC().set(getMEMORY().get(getMAR().get()));
        }
        else {
            pcIncrease();
            
        }
            
      }
   
    
    /**
     * Jump if not equal.
     */
     public void JNE(){

         
        ComputeEffectiveAddress();  
       
        if  (!SelectRegister().get().equals(String.format("%0" + 20 + "d", 0))) {
                        
            if (getI().get().equals("0")) 
                getPC().set(getMAR().get());
            else
                getPC().set(getMEMORY().get(getMAR().get()));
        }
        else {
            pcIncrease();
           
        }
   }
     
     /**
      * Jump if condition true.
      */
     public void JCC(){
         
        ComputeEffectiveAddress();
        
        if  (getCC().get(getRF().Index + 1) == 1) { // if cc(bit-I) = 1
                        
           if (getI().get().equals("0")) 
                getPC().set(getMAR().get());
            else
                getPC().set(getMEMORY().get(getMAR().get()));
        }
        else {
            pcIncrease();
           
        }
      }
     
     /**
      * Unconditional Jump to Address.
      */
     public void JMP(){
         
        ComputeEffectiveAddress();
                   
        if (getI().get().equals("0")) 
            getPC().set(getMAR().get());
        else
            getPC().set(getMEMORY().get(getMAR().get()));
    }
     
     /**
      * Jump and Save Return Address.
      */
     public void JSR(){
        
        ComputeEffectiveAddress();
        pcIncrease();
        getALU().OP2.set(getPC().get());
        getALU().OP1.set("1");
        getALU().add();
        getRF().set("11"); // select Register 3 by seting Register File
        SelectRegister().set(getALU().RES.get()); // R3 <- PC+1
        pause(MIDDLE);
        if (getI().get().equals("1")) 
            getPC().set(getMEMORY().get(getMAR().get())); // PC <- c(EA)
       // R0 should contain pointer to args. Arguments list should end with -17777 ???????
       
    }
     
     /**
      * Return From Subroutine w/ return code as Immed portion (optional).
      * stored in R0's address field
      */
     public void RFS(){
        ComputeEffectiveAddress();
        getRF().set("00"); //select Register 0 by seting Register File
        SelectRegister().set(getADDR().get()); // R0 <- Immed
        pause(MIDDLE);
        getRF().set("11"); //select Register 3 by seting Register File
        getPC().set(SelectRegister().get()); // PC <- c(R3)
               
    }
     
     /**
      * Subtract One And Branch.
      */
     public void SOB(){
        ComputeEffectiveAddress();
        getALU().OP2.set(SelectRegister().get());
        getALU().OP1.set("1");
        getALU().sub();
        if  (SelectRegister().get(20)==0) { // if c(r) > 0
                        
            if (getI().get().equals("0")) 
                getPC().set(getMAR().get());
            else
                getPC().set(getMEMORY().get(getMAR().get()));
        }
        else {
            pcIncrease();
            getALU().OP2.set(getPC().get());
    	    getALU().OP1.set("1");
    	    getALU().add();
            getPC().set (getALU().RES.get());
        }
    
    }
     
     /**
      * Jump Greater than or equal to zero.
      */
    public void JGE(){
        
        ComputeEffectiveAddress();  
       
        if  (SelectRegister().get(20)==0) { // if c(r) > 0
                        
            if (getI().get().equals("0")) 
                getPC().set(getMAR().get());
            else
                getPC().set(getMEMORY().get(getMAR().get()));
        }
        else {
            pcIncrease();
            
        }
            
      }
     
     /**
     * Multiple register by register. Rx contains the high order bits. Rx+1
     * contains the low order bits of the result.
     */
     public void MLT() {
        pcIncrease();
    //    if ((getXF().get().equals("00") || getXF().get().equals("10")) && (getRF().get().equals("00") || getRF().get().equals("10"))) {
            getALU().OP1.set(SelectRegister().get());//OP1 contains Ry
            //String RFcopy = RF.get();
            //String opOne = ALU.OP1.get();
            getRF().set(getXF().get());//RF was set to Rx
            getALU().OP2.set(SelectRegister().get());//OP2 contain Rx
            //String opTwo = ALU.OP2.get();

            getALU().multiply();
            //RF.set(RFcopy);
            
            //String Result = ALU.RESlong.get();
            //SelectRegister().set(getALU().RESlong.get().substring(0, 20));
            SelectRegister().set(getALU().RES.get());
            pause(MIDDLE);
//            SelectRegister().set(getALU().RESlong.get().substring(20, 40));
//            pause(MIDDLE);
            
      //  }
    }

    /**
     * Divide register by register. Rx contains the quotient;Rx+1 contains the
     * remainder.
     * If ry contains 0, set CC(3) to 1 to update DIVZERO flag.
     */
    public void DVD() {
        pcIncrease();
       if ((getXF().get().equals("00") || getXF().get().equals("10")) && (getRF().get().equals("00") || getRF().get().equals("10"))) {
            if (SelectRegister().get().equals("00000000000000000000")||SelectRegister().get().equals("10000000000000000000")){
                //cc(3)set to 1
                getCC().setBit(3, "1");
            }
            else {
                getALU().OP1.set(SelectRegister().get());//OP1 take ry
            //String RFcopy = RF.get();
                getRF().set(getXF().get());
                getALU().OP2.set(SelectRegister().get());//OP2 take rx
                getALU().divide();//Op2/OP1
            //RF.set(RFcopy);
            SelectRegister().set(getALU().RES.get());
            pause(MIDDLE);
            SelectNextRegister().set(getALU().RES1.get());
            pause(MIDDLE);
            }
        }

    }

    /**
     * This method is to test the equality of register and register.
     */
    public void TRR() {
        pcIncrease();
        getALU().OP1.set(SelectRegister().get());
        getRF().set(getXF().get());
        getALU().OP2.set(SelectRegister().get());
        if (getALU().OP1.get().equals(getALU().OP2.get())) {
            //set cc(4) to 1
            getCC().setBit(4, "1");
        } else {
            // set cc(4) to 0
            getCC().setBit(4, "0");
        }
        
      String test = getCC().get();
    }

    /**
     * Logical and of register and register
     */
    public void AND() {
        pcIncrease();
        getALU().OP1.set(SelectRegister().get());
        //String RFcopy = RF.get();

        getRF().set(getXF().get());
        getALU().OP2.set(SelectRegister().get());
        getALU().and();
        //RF.set(RFcopy);
        SelectRegister().set(getALU().RES.get());
        pause(MIDDLE);

    }

    public void ORR() {
        pcIncrease();
        getALU().OP1.set(SelectRegister().get());
        //String RFcopy = RF.get();
        getRF().set(getXF().get());
        getALU().OP2.set(SelectRegister().get());
        getALU().orr();
        //RF.set(RFcopy);
        SelectRegister().set(getALU().RES.get());
        pause(MIDDLE);
    }

    public void NOT() {
        pcIncrease();
        getRF().set(getXF().get());
        getALU().OP1.set(SelectRegister().get());
        String test = getALU().OP1.get();
        getALU().not();
        SelectRegister().set(getALU().RES.get());
        pause(MIDDLE);
    }
    
    public void SRC(){
        pcIncrease();
        int number = Integer.parseInt(getCOUNT().get(), 2);
            getALU().OP1.set(SelectRegister().get());
        if (getI().get().equals("0")&&getT().get().equals("0")){
                getALU().arithShiftRight(number);
            SelectRegister().set(getALU().RES.get());
            pause(MIDDLE);
        }
        else if (getI().get().equals("0")&&getT().get().equals("1"))
        {
                getALU().arithShiftLeft(number);
            SelectRegister().set(getALU().RES.get());
            pause(MIDDLE);
        }
        else if (getI().get().equals("1")&&getT().get().equals("0")){
                getALU().logicalShiftRight(number);
            SelectRegister().set(getALU().RES.get());
            pause(MIDDLE);
        }

        else {
            getALU().logicalShiftLeft(number);
            SelectRegister().set(getALU().RES.get());
            pause(MIDDLE);
        }
    }
    
     public void RRC(){
         pcIncrease();
        int number = Integer.parseInt(getCOUNT().get(), 2);
         getALU().OP1.set(SelectRegister().get());
         if (getI().get().equals("1")&&getT().get().equals("0")){
            getALU().logicalRotateRight(number);
         SelectRegister().set(getALU().RES.get());
         pause(MIDDLE);

         }
         else 
         getALU().logicalRotateLeft(number);
         SelectRegister().set(getALU().RES.get());
         pause(MIDDLE);
    }
     
     public void IN() {
          pcIncrease();
          String DATA="";
          
          switch (Integer.parseInt(DEVID.get(),2)){
              case 0:
                  DATA = KEYBORD.SendData();
                  break;
              case 2:
                  //DATA = CARD READER
                  break;
              case 3:
                  DATA = GPR1.get();
                  break;
                  }
          int numericValue = Integer.parseInt(DATA,2);
          if (48<=numericValue && numericValue<=57) {
              numericValue = Character.getNumericValue(numericValue);
          }
  //        else if (numericValue!=13) 
            //  HLT();
//               if not numeric reject for the program-1
              
          
          SelectRegister().set(Long.toBinaryString(numericValue));
          //pause(MIDDLE);
          
     }
     
     public void OUT() {
          pcIncrease();
          
          switch (Integer.parseInt(DEVID.get(),2)){
              case 1:
                  PRINTER.GetData(SelectRegister().get(), true);
                  
                  break;
              case 2:
                  //DATA = CARD READER
                  break;
              case 3:
                  GPR1.set(SelectRegister().get());
                  break;
               }
                            
         // pause(MIDDLE);
         
     }
     
      public void CHK() {
          
          pcIncrease();
         
          switch (Integer.parseInt(DEVID.get(),2)){
              case 0:
                  SelectRegister().set(KEYBORD.STATUS);
                  break;
              case 1:
                  SelectRegister().set(PRINTER.STATUS);
                  break;
          }
          
      }        
         // pause(MIDDLE);
          
     /**
     * Floating add memory to register
     */
    public void FADD(){
        pcIncrease();

        ComputeEffectiveAddress();
        getALU().OP2.set(getMBR().get());
        getALU().OP1.set(SelectFloatingRegister().get());
        getALU().add();
        SelectFloatingRegister().set(getALU().RES.get());
        pause(MIDDLE);
         
  }     
   
    /**
      * Floating subtract memory from register
     */
    public void FSUB(){
        pcIncrease();
        ComputeEffectiveAddress(); 
        getALU().OP2.set(getMBR().get());
        getALU().OP1.set(SelectFloatingRegister().get());
        getALU().sub();
        SelectFloatingRegister().set(getALU().RES.get());
        pause(MIDDLE);
        
    }     
    
    /**
     * Vector Add
     */
    public void VADD(){
        
        int vectorLength = Integer.valueOf(SelectFloatingRegister().get());
        pcIncrease();
        
        for(int i=0; i<vectorLength; i++){
            EAVector_i(1, i);                       // Calculate effective address of Vector2[i]
            getALU().OP2.set(getMBR().get());       // Store Vector2[i] in OP2
            EAVector_i(0, i);                       // Calculate effective address of Vector1[i]
            getALU().OP1.set(getMBR().get());       // Store Vector1[i] in OP1
            getALU().add();
            getMEMORY().set(getALU().RES.get(), getMAR().get()+i); // V1[i] = V1[i]+ V2[i], result is store in address of V1[i]
        }
         
    }
    
    /**
     * Vector Substract
     */
    public void VSUB(){
        int vectorLength = Integer.valueOf(SelectFloatingRegister().get());
        pcIncrease();
        
        for(int i=0; i<vectorLength; i++){
            EAVector_i(1, i);
            getALU().OP2.set(getMBR().get());
            EAVector_i(0, i);
            getALU().OP1.set(getMBR().get());
            getALU().sub();
            getMEMORY().set(getALU().RES.get(), getMAR().get()+i);
        }
    }
    
    /**
     * Convert to Fixed/FloatingPoint
     */
    public void CNVRT(){
        String memoryAddr;
        pcIncrease();
        
        // Compute address in a special way in this instruction(The x field is used to store the value of F).
        if (getI().get().equals("0")) {
            getMAR().set(getADDR().get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()));
            pause(MIDDLE);
        }
       else{
            getMAR().set(getADDR().get());
            pause(MIDDLE);
            memoryAddr = getMAR().get();
            getMBR().set(getMEMORY().get(memoryAddr));
            pause(MIDDLE);
            getMAR().set(getMBR().get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()));
            pause(MIDDLE);
        }
        
        if(getXF().get().equals("00")) {
            SelectRegister().set(
                                getMBR().get().substring(0, 1)     // sign bit
                                + "000000000000"
                                + getMBR().get().substring(1, 8)    // Exponent of floating point number
                                );
        }
        else if(getXF().get().equals("01")) {
            getFR0().set(getMBR().get().substring(0, 1)     // sign bit
                                        + getMBR().get().substring(13, 20)  // Exponent
                                        + "000000000000"                    // Mantissa
                                        );
        }
        else{
            System.out.println("error!");
            return;
        }
        
    }
    
    /**
     * Load Floating Register From Memory
     */
    public void LDFR(){
        
        pcIncrease();
        ComputeEffectiveAddress();
        SelectFloatingRegister().set(getMBR().get());
    }
    
    /**
     * Store Floating Register To Memory
     */
    public void STFR(){
        pcIncrease();
        ComputeEffectiveAddress();
        getMEMORY().set(SelectFloatingRegister().get(), getMAR().get());
    }
    
    /**
     * Calculate EA of vector0[i]/vector1[i]
     */
    public void EAVector_i(int vector, int i){
        String memoryAddr;
      
        
        if (getXF().get().equals("00") & getI().get().equals("0")) {
            
            getMAR().set(getADDR().get()+vector);
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()+i));
            pause(MIDDLE);
           
           
        }
       
        else if ((getXF().get().equals("01")||getXF().get().equals("10")||getXF().get().equals("11")) & getI().get().equals("0") )  {  
            
            getALU().OP1.set(getADDR().get()+vector);
            getALU().OP2.set(SelectIndexRegister().get());
            getALU().add(); 
            getMAR().set(getALU().RES.get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()+i));
            pause(MIDDLE);

        }
        
        else if (getXF().get().equals("00") & getI().get().equals("1")) {
            getMAR().set(getADDR().get()+vector);
            pause(MIDDLE);
            memoryAddr = getMAR().get();
            getMBR().set(getMEMORY().get(memoryAddr));
            pause(MIDDLE);
            getMAR().set(getMBR().get());
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()+i));
            pause(MIDDLE);
     
        }
        
        else {
            getALU().OP1.set(getADDR().get()+vector);
            getALU().OP2.set(SelectIndexRegister().get());
            getALU().add();
            getMAR().set(getMEMORY().get(getALU().RES.get()));
            pause(MIDDLE);
            getMBR().set(getMEMORY().get(getMAR().get()+i));
            pause(MIDDLE);

        }
        
    }
    
     /**
   * This function select General Purpose Register 
   * according to chosen Register File
   * @return selected register
   */
    private Register SelectRegister() {
        
        Register Rselected;
		
         switch (getRF().Index) {
            case 0:
                Rselected = getGPR0();
                break;
            case 1:
                Rselected = getGPR1();
                break;
            case 2:
                Rselected = getGPR2();
                break;   
             case 3:
                Rselected = getGPR3();
                break;
             default:
                 Rselected = null;
                 break;
         }
         
         return Rselected;
         
 }
    
    private Register SelectNextRegister() {

        Register Rselected;

        switch (getRF().Index) {
            case 0:
                Rselected = getGPR1();
                break;

            case 2:
                Rselected = getGPR3();
                break;

            default:
                Rselected = null;
                break;
        }

        return Rselected;

    }
    /**
   * This function select Index Register 
   * according to chosen Index Register File
   * @return selected index register
   */
     private Register SelectIndexRegister() {
        
        Register Rselected;
		
         switch (getXF().Index) {
            case 1:
                Rselected = getIX1();
                break;
            case 2:
                Rselected = getIX2();
                break;
            case 3:
                Rselected = getIX3();
                break;   
            default:
                 Rselected = null;
                 break;
         }
         
         return Rselected;
         
    }
     
           /**
   * This function select Floating Point Register 
   * according to chosen Register File
   * @return selected register
   */
    private Register SelectFloatingRegister() {
        
        Register FRselected;
		
         switch (getRF().Index) {
            case 0:
                FRselected = getFR0();
                break;
            case 1:
                FRselected = getFR1();
                break;

             default:
                 FRselected = null;
                 break;
         }
         
         return FRselected;
         
 }
    
    
    /**
     * This method increase PC by 1. Used in to updated the PC after execute every Instructions.
     */
    public void pcIncrease(){
        int count = Integer.parseInt(PC.get(), 2);
        if (count < 8191){
            count++;
            PC.set(Long.toBinaryString(count));
        }
        else
            //print something on GUI to remind the overflow.
            PC.set(Long.toBinaryString(count));
        
    }


    /**
     * @return the XF
     */
    public File getXF() {
        return XF;
    }

    /**
     * @param XF the XF to set
     */
    public void setXF(File XF) {
        this.XF = XF;
    }

    /**
     * @return the RF
     */
    public File getRF() {
        return RF;
    }

    /**
     * @param RF the RF to set
     */
    public void setRF(File RF) {
        this.RF = RF;
    }

    /**
     * @return the I
     */
    public File getI() {
        return I;
    }

    /**
     * @param I the I to set
     */
    public void setI(File I) {
        this.I = I;
    }

    /**
     * @return the T
     */
    public File getT() {
        return T;
    }

    /**
     * @param T the T to set
     */
    public void setT(File T) {
        this.T = T;
    }

    /**
     * @return the MEMORY
     */
    public Memory getMEMORY() {
        return MEMORY;
    }

    /**
     * @param MEMORY the MEMORY to set
     */
    public void setMEMORY(Memory MEMORY) {
        this.MEMORY = MEMORY;
    }

    /**
     * @return the ALU
     */
    public ALU getALU() {
        return ALU;
    }

    /**
     * @param ALU the ALU to set
     */
    public void setALU(ALU ALU) {
        this.ALU = ALU;
    }

    /**
     * @return the IR
     */
    public Register getIR() {
        return IR;
    }

    /**
     * @param IR the IR to set
     */
    public void setIR(Register IR) {
        this.IR = IR;
    }

    /**
     * @return the OPCODE
     */
    public Register getOPCODE() {
        return OPCODE;
    }

    /**
     * @param OPCODE the OPCODE to set
     */
    public void setOPCODE(Register OPCODE) {
        this.OPCODE = OPCODE;
    }

    /**
     * @return the ADDR
     */
    public Register getADDR() {
        return ADDR;
    }

    /**
     * @param ADDR the ADDR to set
     */
    public void setADDR(Register ADDR) {
        this.ADDR = ADDR;
    }

    /**
     * @return the CC
     */
    public Register getCC() {
        return CC;
    }

    /**
     * @param CC the CC to set
     */
    public void setCC(Register CC) {
        this.CC = CC;
    }

    /**
     * @return the PC
     */
    public Register getPC() {
        return PC;
    }

    /**
     * @param PC the PC to set
     */
    public void setPC(Register PC) {
        this.PC = PC;
    }

    /**
     * @return the COUNT
     */
    public Register getCOUNT() {
        return COUNT;
    }

    /**
     * @param COUNT the COUNT to set
     */
    public void setCOUNT(Register COUNT) {
        this.COUNT = COUNT;
    }
    
    /**
     * @return the KEYBORD
     */
    public Input getKEYBORD() {
        return KEYBORD;
    }

    /**
     * @param KEYBORD the KEYBORD to set
     */
    public void setKEYBORD(Input KEYBORD) {
        this.KEYBORD = KEYBORD;
    }

    /**
     * @return the MAR
     */
    public Register getMAR() {
        return MAR;
    }
    
     /**
     * @return the PRINTER
     */
    public Output getPRINTER() {
        return PRINTER;
    }

    /**
     * @param PRINTER the PRINTER to set
     */
    public void setPRINTER(Output PRINTER) {
        this.PRINTER = PRINTER;
    }

       /**
     * @param MAR the MAR to set
     */
    public void setMAR(Register MAR) {
        this.MAR = MAR;
    }

    /**
     * @return the MBR
     */
    public Register getMBR() {
        return MBR;
    }

    /**
     * @param MBR the MBR to set
     */
    public void setMBR(Register MBR) {
        this.MBR = MBR;
    }

    /**
     * @return the IX1
     */
    public Register getIX1() {
        return IX1;
    }

    /**
     * @param IX1 the IX1 to set
     */
    public void setIX1(Register IX1) {
        this.IX1 = IX1;
    }

    /**
     * @return the IX2
     */
    public Register getIX2() {
        return IX2;
    }

    /**
     * @param IX2 the IX2 to set
     */
    public void setIX2(Register IX2) {
        this.IX2 = IX2;
    }

    /**
     * @return the IX3
     */
    public Register getIX3() {
        return IX3;
    }

    /**
     * @param IX3 the IX3 to set
     */
    public void setIX3(Register IX3) {
        this.IX3 = IX3;
    }

    /**
     * @return the GPR0
     */
    public Register getGPR0() {
        return GPR0;
    }

    /**
     * @param GPR0 the GPR0 to set
     */
    public void setGPR0(Register GPR0) {
        this.GPR0 = GPR0;
    }

    /**
     * @return the GPR1
     */
    public Register getGPR1() {
        return GPR1;
    }

    /**
     * @param GPR1 the GPR1 to set
     */
    public void setGPR1(Register GPR1) {
        this.GPR1 = GPR1;
    }

    /**
     * @return the GPR2
     */
    public Register getGPR2() {
        return GPR2;
    }

    /**
     * @param GPR2 the GPR2 to set
     */
    public void setGPR2(Register GPR2) {
        this.GPR2 = GPR2;
    }

    /**
     * @return the GPR3
     */
    public Register getGPR3() {
        return GPR3;
    }

    /**
     * @param GPR3 the GPR3 to set
     */
    public void setGPR3(Register GPR3) {
        this.GPR3 = GPR3;
    }

    /**
     * @return the strSwitches
     */
    public String getStrSwitches() {
        return strSwitches;
    }

    /**
     * @param strSwitches the strSwitches to set
     */
    public void setStrSwitches(String strSwitches) {
        this.strSwitches = strSwitches;
    }

     /**
     * @return the controlPanel
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    /**
     * @param controlPanel the controlPanel to set
     */
    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    /**
     * @return the instructionNum
     */
    public int getInstructionNum() {
        return instructionNum;
    }

    /**
     * @param instructionNum the instructionNum to set
     */
    public void setInstructionNum(int instructionNum) {
        this.instructionNum = instructionNum;
    }

    /**
     * @return the pfileName
     */
    public String getPfileName() {
        return pfileName;
    }

    /**
     * @param pfileName the pfileName to set
     */
    public void setPfileName(String pfileName) {
        this.pfileName = pfileName;
    }
    
     /**
     * @return the FR0
     */
    public Register getFR0() {
        return FR0;
    }

    /**
     * @param FR0 the FR0 to set
     */
    public void setFR0(Register FR0) {
        this.FR0 = FR0;
    }

    /**
     * @return the FR1
     */
    public Register getFR1() {
        return FR1;
    }

    /**
     * @param FR1 the FR1 to set
     */
    public void setFR1(Register FR1) {
        this.FR1 = FR1;
    }
}

