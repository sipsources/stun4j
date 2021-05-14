/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TransactionID
/*     */ {
/*  28 */   private byte[] transactionID = new byte[16];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static Random random = new Random(System.currentTimeMillis());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private int hashCode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static TransactionID createTransactionID() {
/*  56 */     TransactionID tid = new TransactionID();
/*     */     
/*  58 */     long left = System.currentTimeMillis();
/*  59 */     long right = random.nextLong();
/*     */     
/*  61 */     for (int i = 0; i < 8; i++) {
/*     */       
/*  63 */       tid.transactionID[i] = (byte)(int)(left >> i * 8 & 0xFFL);
/*  64 */       tid.transactionID[i + 8] = (byte)(int)(right >> i * 8 & 0xFFL);
/*     */     } 
/*     */ 
/*     */     
/*  68 */     tid.hashCode = tid.transactionID[3] << 24 & 0xFF000000 | tid.transactionID[2] << 16 & 0xFF0000 | tid.transactionID[1] << 8 & 0xFF00 | tid.transactionID[0] & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     return tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static TransactionID createTransactionID(byte[] transactionID) {
/*  83 */     TransactionID tid = new TransactionID();
/*     */     
/*  85 */     System.arraycopy(transactionID, 0, tid.transactionID, 0, 16);
/*     */ 
/*     */     
/*  88 */     tid.hashCode = tid.transactionID[3] << 24 & 0xFF000000 | tid.transactionID[2] << 16 & 0xFF0000 | tid.transactionID[1] << 8 & 0xFF00 | tid.transactionID[0] & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     return tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTransactionID() {
/* 104 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 115 */     if (obj == null || !(obj instanceof TransactionID))
/*     */     {
/* 117 */       return false;
/*     */     }
/* 119 */     if (this == obj) {
/* 120 */       return true;
/*     */     }
/* 122 */     byte[] targetBytes = ((TransactionID)obj).transactionID;
/*     */     
/* 124 */     return Arrays.equals(this.transactionID, targetBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(byte[] targetID) {
/* 134 */     return Arrays.equals(this.transactionID, targetID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     StringBuffer idStr = new StringBuffer();
/*     */     
/* 156 */     for (int i = 0; i < this.transactionID.length; i++) {
/*     */       
/* 158 */       idStr.append("0x");
/* 159 */       if ((this.transactionID[i] & 0xFF) <= 15)
/*     */       {
/* 161 */         idStr.append("0");
/*     */       }
/*     */       
/* 164 */       idStr.append(Integer.toHexString(this.transactionID[i] & 0xFF).toUpperCase());
/*     */       
/* 166 */       if (i < this.transactionID.length) {
/* 167 */         idStr.append(" ");
/*     */       }
/*     */     } 
/* 170 */     return idStr.toString();
/*     */   }
/*     */ }

