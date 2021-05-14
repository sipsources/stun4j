/*    */ package net.java.stun4j;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ import net.java.stun4j.message.Message;
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
/*    */ public class StunMessageEvent
/*    */   extends EventObject
/*    */ {
/* 34 */   private Message message = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private StunAddress remoteAddress = null;
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
/*    */   public StunMessageEvent(NetAccessPointDescriptor source, Message message, StunAddress remoteAddress) {
/* 51 */     super(source);
/* 52 */     this.message = message;
/* 53 */     this.remoteAddress = remoteAddress;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NetAccessPointDescriptor getSourceAccessPoint() {
/* 63 */     return (NetAccessPointDescriptor)getSource();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Message getMessage() {
/* 72 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StunAddress getRemoteAddress() {
/* 81 */     return this.remoteAddress;
/*    */   }
/*    */ }


