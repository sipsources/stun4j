/*    */ package net.java.stun4j.attribute;
/*    */ 
/*    */ import net.java.stun4j.StunAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XorMappedAddressAttribute
/*    */   extends AddressAttribute
/*    */ {
/*    */   public static final String NAME = "XOR-MAPPED-ADDRESS";
/*    */   
/*    */   XorMappedAddressAttribute() {
/* 56 */     super('耠');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static StunAddress applyXor(StunAddress address, byte[] transactionID) {
/* 69 */     byte[] addressBytes = address.getAddressBytes();
/* 70 */     char port = address.getPort();
/*    */     
/* 72 */     char portModifier = (char)(transactionID[1] << 8 & 0xFF00 | transactionID[0] & 0xFF);
/*    */ 
/*    */     
/* 75 */     port = (char)(port ^ portModifier);
/*    */     
/* 77 */     for (int i = 0; i < addressBytes.length; i++) {
/* 78 */       addressBytes[i] = (byte)(addressBytes[i] ^ transactionID[i]);
/*    */     }
/* 80 */     StunAddress xoredAdd = new StunAddress(addressBytes, port);
/*    */     
/* 82 */     return xoredAdd;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StunAddress applyXor(byte[] transactionID) {
/* 94 */     return applyXor(getAddress(), transactionID);
/*    */   }
/*    */ }

