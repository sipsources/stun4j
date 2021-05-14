/*    */ package net.java.stun4j.message;
/*    */ 
/*    */ import net.java.stun4j.StunException;
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
/*    */ public class Response
/*    */   extends Message
/*    */ {
/*    */   public void setMessageType(char responseType) throws StunException {
/* 40 */     if (!isResponseType(responseType)) {
/* 41 */       throw new StunException(2, responseType + " - is not a valid response type.");
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 46 */     super.setMessageType(responseType);
/*    */   }
/*    */ }

