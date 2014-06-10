/**
 * This class simulates Cache Design of the Computer
 * Memory class instantiates a sub-level cache as L1
 * This structure also enables the any cache-objects to have sub-level cache (L1, L2)
 * Cache size is determined by the upper-level memory
 * Each block contains 4 words
 * DIRECT MAPPED placement/replacement, WRITE-BACK 
 * and WRITE-ALLOCATE on a write-miss are used in our design
 * 
 * Functions:
 * Besides the basic Cache functions such as Read/Write, Fetch/WriteBack,
 * we have some auxiliary functions to manage the block data such as TakeDataBitsFromtheBlock, GetWordFromBlock.
 * 
 */

package simulator;

import javax.swing.JTextArea;

public class Cache {
    
    private int Size;
    private int[][] CACHE_DATA;
    private Register WriteBuffer;
    
  
    public Cache(int l_size) {
        
        this.Size = l_size;
    
       /** BLOCK CONTENT **
        * First 1 Bit Dirty Bit, 1  Valid Bit, 3 bits Index, 2 bits offset, 13 bit TAG, 4*20 word
        * 1+1+3+2+13+80=100 bits 
        * 0: VALID BIT:
        * 1: DIRTY BIT:     CHECK IF THE BLOCK UPDATED
        * 2-4 INDEX BITS:   NOT USED SINCE WE DO NOT USE SET 
        * 5-6 OFFSET BITS:  SELECT THE WORD ON THE BLOCK
        * 7-19 TAG:         ADDRESS OF THE FIRST WORD ON THE BLOCK
        * 20-39 WORD 1      CONTENT OF THE FIRST WORD
        * 40-59 WORD 2      CONTENT OF THE SECOND WORD
        * 60-79 WORD 3      CONTENT OF THE THIRD WORD
        * 80-99 WORD 4      CONTENT OF THE FOURTH WORD
        */
        CACHE_DATA = new int[this.Size][100];
       
       //This register is used to save data temporarily on Read-Miss 
       WriteBuffer = new Register("WB", 100, false);
          
    }
   
   /**
    * @param Addr Address of the data asked by processor
    * @return Content of the this address either Memory or Cache
    * This is the READ operation of the CACHE
    * If Address asked  is on the Cache, find on the cache, and simply return it (Read Hit)
    * If not on the Cache (Read Miss), bring it from the Memory by using DIRECT MAPPED (Fetch)
    * If there is UPDATE on the block to be swapped (check DIRTY-BIT), 
    * save the changes on the Memory (WRITE-BACK)
    */ 
   public String Read(int Addr) {
        
        String WORD="";
        Integer OffSet, TAG;
        
        boolean OntheCache = false;
        
        OffSet = Addr%4; 
        TAG = Addr - OffSet; // block's TAG: we have first block's physical address only
        String sOffSet = ""; //00,01,10,11
        sOffSet = (OffSet<=1) ? "0" + String.format("%s", OffSet) : Long.toBinaryString(OffSet);
        int block=0;
        
        // check if it is in the CACHE block by block
        for(int i=0; i<=15; i++) {
            
            if (GetBlockTag(i) == TAG){ 
                /** READ HIT !.. **/
                //Set offSet bits to select the word asked
                CACHE_DATA[i][5] = Integer.parseInt(sOffSet.substring(0,1)); // first bit of offset 01 -> 1
                CACHE_DATA[i][6] = Integer.parseInt(sOffSet.substring(1,2)); // second bit of offset 01 -> 0
               //System.out.println("Read Hit!");
               // ControlPanel.jMessages.append("Read Hit!\n");
                
                
                OntheCache = true;
                block = i; // found in the ith block
                break;
            }
        }
        
        if (!OntheCache) {
            
            /** READ MISS !..
             * Fetch words from Memory
             * Since Direct Mapping is used, after read-miss data will be exchanged between memory and cache 
             * new block is placed in  cache index  which is modulo 16 of the asked address 
             * for example: asked address is 2046: 2046-(2046mod4)=2044
             * 2044 mod 16 = 12th block on the cache will be swapped
             * 4 words will be fetched since one block has 4 words
             * fetch from 2044, 2045, 2046, 2047; 2046-(2046mod4)=2044
             * Before fetching, current data on the cache must be checked if it is updated 
             * Since we use Write-Back method, if dirty Bit is set, data must be written to memory
             * But we will give the priority to read, therefore first we will save the data on
             * write buffer, after read-miss finishes, write-back will be done
             */
            //System.out.println("Read Miss!");
           // ControlPanel.jMessages.append("Read Miss!\n");
            
            block = TAG%16; // This block will be exchanged
            if  (CACHE_DATA[block][1] == 1) { //if it is dirty, write back to memory
                WriteBuffer.set(TakeDataBitsFromtheBlock(block,0,99)); //data saved on the buffer
                WriteBack(block); 
            }
            Fetch(TAG); 
           
            //Select the Word asked by setting offset bits
            CACHE_DATA[block][5] = Integer.parseInt(sOffSet.substring(0,1)); // second bit of offset 01 -> 0
            CACHE_DATA[block][6] = Integer.parseInt(sOffSet.substring(1,2)); // first bit of offset 01 -> 1
                 
       }
        
        WORD = GetWordFromBlock(block);
                
        return WORD;
    }
   
    /**
     * @param TAG Address of the Data to be inserted
     * This method brings 4 words from the Memory to the Cache
     * together with the data asked
     */   
    public void Fetch(int TAG) {
        
       String DATA="", sMsg="";          
       int block;
       String sTag = "";
          
        // Choose the new block to be inserted
        block = TAG%16; 
        
        SetDataBitsIntheBlock("0000000", block, 0, 6); //valid-dirty-index-offset bits
        sTag = Long.toBinaryString(TAG); //TAG (physical address) of the first word in the cache
        sTag = String.format("%0" + (13- sTag.length()) + "d", 0) + sTag;
        SetDataBitsIntheBlock(sTag, block, 7, 19); //Tag bits

       int indexStart=20, indexEnd=39; // first word in the CACHE
            
       for (int i=1;i<=4;i++) {
           DATA = ControlPanel.MEMORY.getDirect(Long.toBinaryString(TAG+i-1));
           SetDataBitsIntheBlock(DATA,block, i*indexStart, indexEnd+((i-1)*indexStart));
           sMsg = sMsg + String.format("%s",TAG+i-1) + "-";
           
       }
      // ControlPanel.jMessages.append("CACHE(" + block + ") --> " + sMsg.substring(0, sMsg.length()-1) + "\n");
             
    }
    
    /**
     * 
     * @param WORD the data to be updated
     * @param Addr the address of the data on either Memory or Cache
     * This is the WRITE operation of the CACHE
     * If Address updated  is on the Cache, find on the cache, and simply update it (Write Hit)
     * Set the DIRTY BIT, UPDATE the Memory during the WRITE-BACK operation which occurs on read miss
     * If not on the Cache (Write Miss), bring it from the Memory by using WRITE-ALLOCATE (fetch)
     */ 
     public void Write(String WORD, int Addr) {
        
        int TAG=0;
        String sAddr = "";
        Integer OffSet;
        boolean OntheCache = false;
        
        OffSet = Addr%4; 
        TAG = Addr - OffSet; // block's physical address
        String sOffSet = ""; //00,01,10,11
        sOffSet = (OffSet<=1) ? "0" + String.format("%s", OffSet) : Long.toBinaryString(OffSet);
        int block=0;
        
        // check if it is in the CACHE block by block
        for(int i=0; i<=15; i++) {
            if (GetBlockTag(i) == TAG){ 
            /** WRITE HIT !..
              * we are using Write Back method
              * if data on the cache, we will not update the Memory right away
              * instead we set dirty bit and update it 
              * while we are swapping cache blocks from the memory
              */
                CACHE_DATA[i][1] = 1; // set Dirty bit, block updated
                //Set offSet bits to select the word asked
                CACHE_DATA[i][5] = Integer.parseInt(sOffSet.substring(0,1)); // first bit of offset 01 -> 1
                CACHE_DATA[i][6] = Integer.parseInt(sOffSet.substring(1,2)); // second bit of offset 01 -> 0
                UpdateWordInBlock(i,WORD);
              //  ControlPanel.jMessages.append("Write Hit!\n");
                OntheCache = true;
                break;
            }
        }
        
        if (!OntheCache) {
            /** WRITE MISS !..
              * Data is not on the cache so write to memory
              * Since we are using  write allocate method,
              * Fetch words from Memory
              */ 
          //System.out.println("Write Miss!");
          //  ControlPanel.jMessages.append("Write Miss!\n");
            sAddr = Long.toBinaryString(Addr);
            sAddr = String.format("%0" + (20-sAddr.length())+ "d", 0) + sAddr;
            ControlPanel.MEMORY.setDirect(WORD, sAddr);
            
            Fetch(TAG); 
           
        }
  }
    /**
     * 
     * @param block The block on the cache to be written to Memory
     * This method writes 4 words from Cache to Memory  
     * This happens during the read miss if block is dirty
     * Data should be read from Write-Buffer
     * For the simplicity, we are reading it from the cache before exchanging
     */
    public void WriteBack(int block) {
             
        int TAG;
        String ADDR="";
        String DATA="";
        
        TAG = Integer.parseInt(TakeDataBitsFromtheBlock(block, 7, 19),2);
        int indexStart=20, indexEnd=39; // first word in the CACHE
                
        for (int i=1;i<=4;i++) {
           DATA = TakeDataBitsFromtheBlock(block, i*indexStart, indexEnd+((i-1)*indexStart));
           ADDR = Long.toBinaryString(TAG + i -1);
           ADDR = String.format("%0" + (20-ADDR.length())+ "d", 0) + ADDR;
           ControlPanel.MEMORY.setDirect(DATA, ADDR);
        }
  }
    
  
    // ****** AUXILIARY FUNCTIONS TO MANAGE THE BLOCK DATA ******************
     
    private int GetBlockTag(int block) {
        
        String s="";
   
        s = TakeDataBitsFromtheBlock(block, 7, 19);
               
        return Integer.parseInt(s, 2);
   
    }
    
    private String GetWordFromBlock(int block) {
        String sData="";
        int indexStart=0, indexEnd=0;
        
        int OffSet = (1 * CACHE_DATA[block][6]) + (2 * CACHE_DATA[block][5]); //select data from the block
       //20-39 WORD 1
       //40-59 WORD 2
       //60-79 WORD 3
       //80-99 WORD 4
        switch(OffSet) {
            case 0:
                indexStart = 20;
                indexEnd = 39;
                break;
            case 1:
                indexStart = 40;
                indexEnd = 59;
                break;
            case 2:
                indexStart = 60;
                indexEnd = 79;
                break;
            case 3:
                indexStart = 80;
                indexEnd = 99;
                break;
         }
        
    
        return TakeDataBitsFromtheBlock(block, indexStart, indexEnd);
    
    }
    
    private void UpdateWordInBlock(int block, String DATA) {
       
        int indexStart=0, indexEnd=0;
        
        int OffSet = (1 * CACHE_DATA[block][6]) + (2 * CACHE_DATA[block][5]); //select data from the block
       //20-39 WORD 1
       //40-59 WORD 2
       //60-79 WORD 3
       //80-99 WORD 4
        switch(OffSet) {
            case 0:
                indexStart = 20;
                indexEnd = 39;
                break;
            case 1:
                indexStart = 40;
                indexEnd = 59;
                break;
            case 2:
                indexStart = 60;
                indexEnd = 79;
                break;
            case 3:
                indexStart = 80;
                indexEnd = 99;
                break;
         }
        
        SetDataBitsIntheBlock(DATA, block, indexStart, indexEnd);
   
    }
    
      
    private void SetDataBitsIntheBlock(String mData, int block, int indexStart, int indexEnd) {
        
        int j=0;
        for(int i=indexStart; i<=indexEnd; i++) {
             CACHE_DATA[block][i] = Integer.parseInt(mData.substring(j, j+1));
             j++;
        }
    }
    
    private String TakeDataBitsFromtheBlock(int block, int indexStart, int indexEnd) {
        
        String sData = "";
        
        for(int i=indexStart; i<=indexEnd; i++)
             sData += CACHE_DATA[block][i];
        
        return sData;
    }
  
}
