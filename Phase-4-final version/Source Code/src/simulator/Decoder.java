/**
 * This class converts the assembly code into binary code for each instruction.
 */

package simulator;

public class Decoder {
    
    /**
     * format the code into specific bits
     * @param code
     * @param bit
     * @return 
     */
    public static String formatCode(String code, int bit){
        if(code.length() < bit){
            code = String.format("%0" + (bit-code.length()) + "d", 0) + code;
        }
        return code;
    }
    
    /**
     * change a string to binary code
     * @param line
     * @return 
     */
    public static String toBinary(String line){
            String binaryCode = new String();   // result
            String operator = new String();     // operator
            String rest = new String();         // substring following the operator
            String temp[];                      // storing 2 parts: operator + following part
            String restTemp[];                  // storing substrings of the following part
            
            line = line.toLowerCase();          // change every char into small letter
            line = line.trim();                 // delete " " before and after the string
            temp = line.split(" ");             // divide the string into 2 parts: oprater + following part
            operator = temp[0];
            rest = line.substring(operator.length(), line.length());    
            rest = rest.replace(" ", "");       // delete " "
            restTemp = rest.split(",");         // get each element
            
            // LDR, STR, LDA, JZ, JNE, JCC, SOB, JGE, AMR, SMR
            if(operator.equals("ldr") || operator.equals("str") || operator.equals("lda")
                    || operator.equals("jz") || operator.equals("jne") || operator.equals("jcc")
                    || operator.equals("sob") || operator.equals("jge") || operator.equals("amr")
                    || operator.equals("smr")){
                
                
                String R = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String IX = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 2);
                String addr = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[2])), 8);
                
                if (operator.equals("ldr")){
                    operator = Integer.toBinaryString(1);
                }
                else if(operator.equals("str")){
                    operator = Integer.toBinaryString(2);
                }
                else if(operator.equals("lda")){
                    operator = Integer.toBinaryString(3);
                }
                else if(operator.equals("jz")){
                    operator = Integer.toBinaryString(10);
                }
                else if(operator.equals("jne")){
                    operator = Integer.toBinaryString(11);
                }
                else if(operator.equals("jcc")){
                    operator = Integer.toBinaryString(12);
                }
                else if(operator.equals("sob")){
                    operator = Integer.toBinaryString(16);
                }
                else if(operator.equals("jge")){
                    operator = Integer.toBinaryString(17);
                }
                else if(operator.equals("amr")){
                    operator = Integer.toBinaryString(4);
                }
                else if(operator.equals("smr")){
                    operator = Integer.toBinaryString(5);
                }
                operator = formatCode(operator, 6);
                    
                if(restTemp.length == 4){
                    binaryCode = operator                                               // opcode
                            + IX                                                        // IX
                            + R                                                         // R (CC in JCC)
                            + Integer.toBinaryString(Integer.valueOf(restTemp[3]))      // I
                            + "0"                                                       // T
                            + addr;                                                     // addr
                }
                else if(restTemp.length == 3){
                    binaryCode = operator                                               // opcode
                            + IX                                                        // IX
                            + R                                                         // R
                            + "00"                                                      // I, T
                            + addr;                                                     // addr
                }
                else {
                    return "false";
                }
                
            }
            
            // LDX, STX, JMP, JSR 
            if(operator.equals("ldx") || operator.equals("stx") || operator.equals("jmp")
                    || operator.equals("jsr")){
                
                String IX = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String addr = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 8);
                
                if (operator.equals("ldx")){
                    operator = Integer.toBinaryString(41);
                }
                else if(operator.equals("stx")){
                    operator = Integer.toBinaryString(42);
                }
                else if(operator.equals("jmp")){
                    operator = Integer.toBinaryString(13);
                }
                else if(operator.equals("jsr")){
                    operator = Integer.toBinaryString(14);
                }
                operator = formatCode(operator, 6);
                    
                if(restTemp.length == 3){
                    binaryCode = operator                                               // opcode
                            + IX                                                        // IX
                            + "00"                                                      // R
                            + Integer.toBinaryString(Integer.valueOf(restTemp[2]))      // I
                            + "0"                                                       // T
                            + addr;                                                     // addr
                }
                else if(restTemp.length == 2){
                    binaryCode = operator                                               // opcode
                            + IX                                                        // IX
                            + "00"                                                      // R
                            + "00"                                                      // I, T
                            + addr;                                                     // addr
                }
                else {
                    return "false";
                }
                
            }
            
            // RFS
            else if (operator.equals("rfs")){
                String immed = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 8);
                binaryCode = formatCode(Integer.toBinaryString(15), 6)                  // opcode
                        + "000000"                                                      // IX, R, I, T (ignored)
                        + immed;                                                        // immediate
                
            }
            
            // AIR, SIR
            else if (operator.equals("air") || (operator.equals("sir"))){
                String R = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String immed = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 8);
                if(operator.equals("air")){
                    operator = Integer.toBinaryString(6);
                }
                else if(operator.equals("sir")){
                    operator = Integer.toBinaryString(7);
                }
                operator = formatCode(operator, 6);
                binaryCode = operator                                                   // opcode
                        +  "00" 
                        + R                                                            // IX
                        + "00"                                                        // R, I, T (ignored)
                        + immed;                                                        // immediate
            }
            
              else if (operator.equals("aix") || (operator.equals("six"))){
                String IX = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String immed = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 8);
                 if(operator.equals("aix")){
                    operator = Integer.toBinaryString(8);
                }
                else if(operator.equals("six")){
                    operator = Integer.toBinaryString(9);
                }
                             
                operator = formatCode(operator, 6);
                binaryCode = operator                                                   // opcode
                        + IX                                                                               // IX
                        + "0000"                                                        // R, I, T (ignored)
                        + immed;                                                        // immediate
            }
            
            // MLT, DVD, TRR, AND, ORR
            else if(operator.equals("mlt") || operator.equals("dvd") || operator.equals("trr")
                    || operator.equals("and") || operator.equals("orr")){
                
                String Rx = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String Ry = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 2);
                if(operator.equals("mlt")){
                    operator = Integer.toBinaryString(20);
                }
                else if(operator.equals("dvd")){
                    operator = Integer.toBinaryString(21);
                }
                else if(operator.equals("trr")){
                    operator = Integer.toBinaryString(22);
                }
                else if(operator.equals("and")){
                    operator = Integer.toBinaryString(23);
                }
                else if(operator.equals("orr")){
                    operator = Integer.toBinaryString(24);
                }
                operator = formatCode(operator, 6);
                
                binaryCode = operator                                               // opcode
                        + Rx                                                        // Rx
                        + Ry                                                        // Ry
                        + "0000000000";                                             // L/R, A/L, ...(ignored)
            }
            
            // NOT
            else if (operator.equals("not")) {
                String Rx = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                binaryCode = formatCode(Integer.toBinaryString(25), 6)              // opcode
                        + Rx                                                        // Rx
                        + "000000000000";                                           // Ry, L/R, A/L, ...(ignored)
                
            }
           

            // SRC, RRC
            else if(operator.equals("src") || operator.equals("rrc")){
                
                String R = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String count = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 5);
                if (operator.equals("src")){
                    operator = Integer.toBinaryString(31);
                }
                else if(operator.equals("rrc")) {
                    operator = Integer.toBinaryString(32);      
                }
                operator = formatCode(operator, 6);
                        
                binaryCode = operator                                                   // opcode
                        + "00"                                                          // IX (ignored)
                        + R                                                             // R
                        + Integer.toBinaryString(Integer.valueOf(restTemp[3]))          // A/L
                        + Integer.toBinaryString(Integer.valueOf(restTemp[2]))          // L/R
                        + "000"
                        + count;                                                        // count
                
            }
            
            // IN, OUT CHK
            else if(operator.equals("in") || operator.equals("out") || operator.equals("chk")){
                
                String R = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 2);
                String devid = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[1])), 4);
                if(operator.equals("in")){
                    operator = Integer.toBinaryString(61);
                }
                else if(operator.equals("out")){
                    operator = Integer.toBinaryString(62);
                }
                else if(operator.equals("chk")){
                    operator = Integer.toBinaryString(63);
                }
                operator = formatCode(operator, 6);
                        
                binaryCode = operator                                                   // opcode
                        + "00"                                                          // IX (ignored)
                        + R                                                             // R
                        + "000000"
                        + devid;                                                        // DevID
            }
            
            // HLT
            else if(operator.equals("hlt")){
                binaryCode = "00000000000000000000";
            }
            
            // TRAP
            else if(operator.equals("trap")){
                String code = formatCode(Integer.toBinaryString(Integer.valueOf(restTemp[0])), 8);
                binaryCode = formatCode(Integer.toBinaryString(30), 6)                  // opcode
                        + "000000"                                                      
                        + code;                                                         // trap code
            }
                
            return binaryCode;
        }
    
}
