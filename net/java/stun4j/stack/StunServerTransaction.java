/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.message.Message;
/*     */ import net.java.stun4j.message.Response;
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
/*     */ class StunServerTransaction
/*     */   implements Runnable
/*     */ {
/*  47 */   private long transactionLifetime = 16000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private StunProvider providerCallback = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private StunAddress responseDestination = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private Response response = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private TransactionID transactionID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private long expirationDate = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private Thread runningThread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expired = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRetransmitting = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StunServerTransaction(StunProvider providerCallback, TransactionID tranID) {
/* 105 */     this.providerCallback = providerCallback;
/*     */     
/* 107 */     this.transactionID = tranID;
/*     */     
/* 109 */     this.runningThread = new Thread(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 118 */     this.expired = false;
/* 119 */     this.runningThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 128 */     this.runningThread.setName("ServTran");
/*     */     
/* 130 */     schedule(this.transactionLifetime);
/* 131 */     waitNextScheduledDate();
/*     */ 
/*     */     
/* 134 */     expire();
/* 135 */     this.providerCallback.removeServerTransaction(this);
/*     */   }
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
/*     */   void sendResponse(Response response, NetAccessPointDescriptor sendThrough, StunAddress sendTo) throws StunException {
/* 155 */     if (!this.isRetransmitting) {
/* 156 */       this.response = response;
/*     */ 
/*     */       
/* 159 */       response.setTransactionID(this.transactionID.getTransactionID());
/* 160 */       this.apDescriptor = sendThrough;
/* 161 */       this.responseDestination = sendTo;
/*     */     } 
/*     */     
/* 164 */     this.isRetransmitting = true;
/* 165 */     retransmitResponse();
/*     */   }
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
/*     */   void retransmitResponse() throws StunException {
/* 178 */     if (this.expired || !this.isRetransmitting) {
/*     */       return;
/*     */     }
/* 181 */     this.providerCallback.getNetAccessManager().sendMessage((Message)this.response, this.apDescriptor, this.responseDestination);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void waitNextScheduledDate() {
/* 192 */     long current = System.currentTimeMillis();
/* 193 */     while (this.expirationDate - current > 0L) {
/*     */ 
/*     */       
/*     */       try {
/* 197 */         wait(this.expirationDate - current);
/*     */       }
/* 199 */       catch (InterruptedException ex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       if (this.expired)
/*     */         return; 
/* 206 */       current = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void schedule(long timeout) {
/* 216 */     this.expirationDate = System.currentTimeMillis() + timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void expire() {
/* 225 */     this.expired = true;
/* 226 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TransactionID getTransactionID() {
/* 237 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isReransmitting() {
/* 247 */     return this.isRetransmitting;
/*     */   }
/*     */ }

