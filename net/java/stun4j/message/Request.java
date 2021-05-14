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
/*    */ public class Request
/*    */   extends Message
/*    */ {
/*    */   public void setMessageType(char requestType) throws StunException {
/* 40 */     if (!isRequestType(requestType)) {
/* 41 */       throw new StunException(2, requestType + " - is not a valid request type.");
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 46 */     super.setMessageType(requestType);
/*    */   }
/*    */ }

