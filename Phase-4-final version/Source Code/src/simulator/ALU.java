/**
 * This class simulates the ALU component
 * It has 3 Register class instances.
 * OP1 and OP2 represent the operant registers inside the ALU  
 * When these operant registers are set and and an operator function is call
 * the result is saved in RES register
*/
package simulator;

public class ALU {
    
    public Register OP1, OP2, RES, RES1, RESlong;

    public ALU() {

        OP1 = new Register("OP1", 20, false);
        OP2 = new Register("OP2", 20, false);
        RES = new Register("RES", 20, false);
        RES1 = new Register("RES1", 20, false);
        RESlong = new Register("LongResult", 40, false);

    }
    
    /**
     * This function simulates ADDER circuit inside the ALU
     * Values are read from operand registers
     * Result is loaded to RES register
     * 
     */
     public void add() {
        int op1Int = convertStringtoSignedValue(OP1.get());
        int op2Int = convertStringtoSignedValue(OP2.get());
        int subResult = op1Int+op2Int;
        if (subResult>=0){
        RES.set( Long.toBinaryString(subResult));}
        else {
            subResult=-subResult;       
            RES.set(Long.toBinaryString(subResult));
            String temp = RES.get();
            temp = "1"+temp.substring(1,20);
            RES.set(temp);
      
        }
          
    }
    
    /**
     * This method does the same thing as add(). But return the result as integer.
     * The purpose is to check overflow. 
     * For example, check the AMR() method in ControlPanel.
     *
     * @return 
     */
    public int addInt(){
        int op1Int = convertStringtoSignedValue(OP1.get());
        int op2Int = convertStringtoSignedValue(OP2.get());
        return op1Int+op2Int;
    }
    
 
     /**
     * This function simulates SUBTRACTOR circuit inside the ALU
     * Values are read from operand registers
     * Result is loaded to RES register
     */
    public void sub() {
        
        int op1Int = convertStringtoSignedValue(OP1.get());
        int op2Int = convertStringtoSignedValue(OP2.get());
        int subResult = op1Int-op2Int;
        if (subResult<0){
         RES.set( convertSignedValuetoString(subResult));
         } else
         RES.set( Long.toBinaryString(subResult));   
         
    }
    
     /**
     * This method multiply op1 by op2. And result was stored in RESlong which
     * is a 40 bits register.
     * The value in OP1 and OP2 are considered as signed value.
     */
 public void multiply() {
        String signBite = "0";
        if (OP1.get().substring(0,1).equals(OP2.get().substring(0, 1))){
           signBite = "0";
        }
        else 
           signBite = "1";
        long OPOne = Integer.parseInt(OP1.get().substring(1, 20), 2);
        long OPTwo = Integer.parseInt(OP2.get().substring(1, 20), 2);
        long multiResult = OPOne*OPTwo;
//        RESlong.set(Long.toBinaryString(multiResult));
//        RESlong.set(signBite+RESlong.get().substring(1, 40));
//        String teststring = RESlong.get();
        RES.set( Long.toBinaryString(multiResult));
    }

    public void divide() {
        RES.set(Long.toBinaryString(Integer.parseInt(OP2.get(), 2) / Integer.parseInt(OP1.get(), 2)));
        RES1.set(Long.toBinaryString(Integer.parseInt(OP2.get(), 2) % Integer.parseInt(OP1.get(), 2)));
    }

    public void and() {
        String result = "";
        for (int i = 0; i < 20; i++) {
            if (OP1.get().substring(i, i + 1).equals("1") && OP2.get().substring(i, i + 1).equals("1")) {
                result = result + "1";
            } else {
                result = result + "0";
            }
        }
        RES.set(result);
    }

    public void orr() {
        String result = "";
        for (int i = 0; i < 20; i++) {
            if (OP1.get().substring(i, i + 1).equals("0") && OP2.get().substring(i, i + 1).equals("0")) {
                result = result + "0";
            } else {
                result = result + "1";
            }
        }
        RES.set(result);
    }

    public void not() {
        String result = "";
        for (int i = 0; i < 20; i++) {
            if (OP1.get().substring(i, i + 1).equals("0")) {
                result = result + "1";
            } else {
                result = result + "0";

            }
        }
        RES.set(result);
    }
    
    /**
     * This method shift the data in OP1 n bits right arithmetically. The sign bit 
     * will be remained.
     * @param n 
     */
    public void arithShiftRight(int n){
        int shift = n%19;
    String sign = OP1.get().substring(0,1);
    int beforeShift = Integer.parseInt(OP1.get().substring(1, 20), 2);
    int afterShift = (beforeShift >>> shift);
    String shiftResult = Long.toBinaryString(afterShift);
    RES.set(shiftResult);
    RES.setSignBit(sign);
    }
    
    /**
     * This method shift the data in OP1 n bits left arithmetically. The sign bit 
     * will be remained.
     * @param n 
     */
    public void arithShiftLeft(int n){
        int shift = n%19;
    String sign = OP1.get().substring(0,1);
    int beforeShift = Integer.parseInt(OP1.get().substring(1, 20), 2);
    int afterShift = (beforeShift << shift);
    String shiftResult = Long.toBinaryString(afterShift);
    RES.set(shiftResult);
    RES.setSignBit(sign);
    }
    
    /**
     * This method shift the data in OP1 n bits right logically. The sign bit 
     * will be remained.
     * @param n 
     */
    public void logicalShiftRight(int n){
    int shift = n%20;
    int beforeShift = Integer.parseInt(OP1.get(), 2);
    int afterShift = (beforeShift >> shift);
    String shiftResult = Long.toBinaryString(afterShift);
    RES.set(shiftResult);
    }
    
    /**
     * This method shift the data in OP1 n bits left logically. The sign bit 
     * will be remained.
     * @param n 
     */
    public void logicalShiftLeft(int n){
        int shift = n%20;
    int beforeShift = Integer.parseInt(OP1.get(), 2);
    int afterShift = (beforeShift << shift);
    String shiftResult = Long.toBinaryString(afterShift);
    RES.set(shiftResult);
    }
    
    public void logicalRotateLeft(int n){
        int shift = n%20;
        String buffer = OP1.get();
        for (int i=0;i<shift;i++){
            buffer= buffer.substring(1, 20)+buffer.substring(0,1);
        }
        RES.set(buffer);
    }
    
    public void logicalRotateRight(int n){
        int shift = n%20;
    String buffer = OP1.get();
        for (int i=0;i<shift;i++){
            buffer= buffer.substring(19)+buffer.substring(0,19);
        }
        RES.set(buffer);
    }
    
    /**
     * This method convert string to signed decimal integer. The param value has to
     * be 20bits string.
     * @param value
     * @return 
     */
    public int convertStringtoSignedValue(String value){
    int a;
    if (value.substring(0, 1).equals("1")){
     a = -Integer.parseInt(value.substring(1,20), 2);
    } 
    else
    a =Integer.parseInt(value.substring(1,20), 2);
        
    return a;
    }
    
    /**
     * this method convert negative integer to 20bits binary code;the param n has to be 
     * negative value. Register RES1 is used in this method.
     * @param n
     * @return 
     */
    public String convertSignedValuetoString(int n){
    
    if (n >= 0){
    String s = Long.toBinaryString(n);
    RES1.set(s);
    } else {
    //int a = -n;
    String s = Long.toBinaryString(-n);
    RES1.set(s);
    RES1.set( "1" + RES1.get().substring(1, 20));
    }
    
    return RES1.get();
    }
    
    /**
     * This method is used to check the over flow status.
     * The first parameter "input" is the value you want to check.
     * The second parameter "bits" indicate the length of the register or memory where you want to store that data.
     * If overflow is confirmed, this method will return "1" and set CC(1) to 1. Otherwise it will return "0".
     * @return 
     */
    public int checkOverflow(int input,int bits){
        Double max = Math.pow(2, bits-1);
        Double min = -Math.pow(2, bits-1);
        if (input>=min && input<=max){
           return 0;
        } else 
                 
           return 1;
        
    }

}
