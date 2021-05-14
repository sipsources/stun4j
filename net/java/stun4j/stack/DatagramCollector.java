/*    */ package net.java.stun4j.stack;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatagramCollector
/*    */   implements Runnable
/*    */ {
/* 15 */   DatagramPacket receivedPacket = null;
/* 16 */   DatagramSocket sock = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 26 */       this.sock.receive(this.receivedPacket);
/*    */     }
/* 28 */     catch (IOException ex) {
/*    */       
/* 30 */       this.receivedPacket = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startListening(DatagramSocket sock) {
/* 37 */     this.sock = sock;
/* 38 */     this.receivedPacket = new DatagramPacket(new byte[4096], 4096);
/*    */     
/* 40 */     (new Thread(this)).start();
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 45 */       Thread.sleep(200L);
/*    */     }
/* 47 */     catch (InterruptedException ex) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DatagramPacket collectPacket() {
/* 55 */     DatagramPacket returnValue = this.receivedPacket;
/* 56 */     this.receivedPacket = null;
/* 57 */     this.sock = null;
/*    */ 
/*    */     
/* 60 */     return returnValue;
/*    */   }
/*    */ }


