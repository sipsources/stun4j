/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.ResponseCollector;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.message.Message;
/*     */ import net.java.stun4j.message.Request;
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
/*     */ class StunClientTransaction
/*     */   implements Runnable
/*     */ {
/*  42 */   private static final Logger logger = Logger.getLogger(StunClientTransaction.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_MAX_RETRANSMISSIONS = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public int maxRetransmissions = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_ORIGINAL_WAIT_INTERVAL = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public int originalWaitInterval = 100;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_MAX_WAIT_INTERVAL = 1600;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public int maxWaitInterval = 1600;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private int retransmissionsCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private int lastWaitInterval = this.originalWaitInterval;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private StunProvider providerCallback = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private Request request = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private StunAddress requestDestination = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private TransactionID transactionID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private ResponseCollector responseCollector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean cancelled = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private long nextRetransmissionDate = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private Thread runningThread = null;
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
/*     */   public StunClientTransaction(StunProvider providerCallback, Request request, StunAddress requestDestination, NetAccessPointDescriptor apDescriptor, ResponseCollector responseCollector) {
/* 157 */     this.providerCallback = providerCallback;
/* 158 */     this.request = request;
/* 159 */     this.apDescriptor = apDescriptor;
/* 160 */     this.responseCollector = responseCollector;
/* 161 */     this.requestDestination = requestDestination;
/*     */     
/* 163 */     initTransactionConfiguration();
/*     */     
/* 165 */     this.transactionID = TransactionID.createTransactionID();
/*     */     
/*     */     try {
/* 168 */       request.setTransactionID(this.transactionID.getTransactionID());
/*     */     }
/* 170 */     catch (StunException ex) {
/*     */ 
/*     */ 
/*     */       
/* 174 */       throw new IllegalArgumentException("The TransactionID class genereated an invalid transaction ID");
/*     */     } 
/*     */ 
/*     */     
/* 178 */     this.runningThread = new Thread(this);
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
/*     */   public void run() {
/* 191 */     this.runningThread.setName("CliTran");
/* 192 */     while (this.retransmissionsCounter++ < this.maxRetransmissions) {
/*     */ 
/*     */       
/* 195 */       waitUntilNextRetransmissionDate();
/*     */ 
/*     */       
/* 198 */       if (this.cancelled) {
/*     */         return;
/*     */       }
/* 201 */       if (this.lastWaitInterval < this.maxWaitInterval) {
/* 202 */         this.lastWaitInterval *= 2;
/*     */       }
/*     */       
/*     */       try {
/* 206 */         this.providerCallback.getNetAccessManager().sendMessage((Message)this.request, this.apDescriptor, this.requestDestination);
/*     */       
/*     */       }
/* 209 */       catch (StunException ex) {
/*     */ 
/*     */ 
/*     */         
/* 213 */         logger.log(Level.WARNING, "A client tran retransmission failed", (Throwable)ex);
/*     */       } 
/*     */ 
/*     */       
/* 217 */       schedule(this.lastWaitInterval);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (this.lastWaitInterval < this.maxWaitInterval) {
/* 223 */       this.lastWaitInterval *= 2;
/*     */     }
/* 225 */     schedule(this.lastWaitInterval);
/* 226 */     waitUntilNextRetransmissionDate();
/*     */     
/* 228 */     this.responseCollector.processTimeout();
/* 229 */     this.providerCallback.removeClientTransaction(this);
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
/*     */   void sendRequest() throws StunException {
/* 245 */     this.providerCallback.getNetAccessManager().sendMessage((Message)this.request, this.apDescriptor, this.requestDestination);
/*     */ 
/*     */ 
/*     */     
/* 249 */     schedule(this.originalWaitInterval);
/* 250 */     this.runningThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Request getRequest() {
/* 259 */     return this.request;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void waitUntilNextRetransmissionDate() {
/* 269 */     long current = System.currentTimeMillis();
/* 270 */     while (this.nextRetransmissionDate - current > 0L) {
/*     */ 
/*     */       
/*     */       try {
/* 274 */         wait(this.nextRetransmissionDate - current);
/*     */       }
/* 276 */       catch (InterruptedException ex) {
/*     */         
/* 278 */         logger.log(Level.FINE, "Interrupted", ex);
/*     */       } 
/*     */ 
/*     */       
/* 282 */       if (this.cancelled)
/*     */         return; 
/* 284 */       current = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void schedule(long timeout) {
/* 294 */     this.nextRetransmissionDate = System.currentTimeMillis() + timeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void cancel() {
/* 303 */     this.cancelled = true;
/* 304 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleResponse(StunMessageEvent evt) {
/* 314 */     String keepTran = System.getProperty("net.java.stun4j.KEEP_CLIENT_TRANS_AFTER_A_RESPONSE");
/*     */ 
/*     */     
/* 317 */     if (keepTran == null || !keepTran.trim().equalsIgnoreCase("true")) {
/* 318 */       cancel();
/*     */     }
/* 320 */     this.responseCollector.processResponse(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TransactionID getTransactionID() {
/* 330 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initTransactionConfiguration() {
/* 340 */     String maxRetransmissionsStr = System.getProperty("net.java.stun4j.MAX_RETRANSMISSIONS");
/*     */ 
/*     */     
/* 343 */     if (maxRetransmissionsStr != null && maxRetransmissionsStr.trim().length() > 0) {
/*     */       
/*     */       try {
/*     */         
/* 347 */         this.maxRetransmissions = Integer.parseInt(maxRetransmissionsStr);
/*     */       }
/* 349 */       catch (NumberFormatException e) {
/*     */         
/* 351 */         logger.log(Level.FINE, "Failed to parse MAX_RETRANSMISSIONS", e);
/* 352 */         this.maxRetransmissions = 8;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 357 */     String originalWaitIntervalStr = System.getProperty("net.java.stun4j.ORIGINAL_WAIT_INTERVAL");
/*     */ 
/*     */     
/* 360 */     if (originalWaitIntervalStr != null && originalWaitIntervalStr.trim().length() > 0) {
/*     */       
/*     */       try {
/*     */         
/* 364 */         this.originalWaitInterval = Integer.parseInt(originalWaitIntervalStr);
/*     */       }
/* 366 */       catch (NumberFormatException e) {
/*     */         
/* 368 */         logger.log(Level.FINE, "Failed to parse ORIGINAL_WAIT_INTERVAL", e);
/* 369 */         this.originalWaitInterval = 100;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 374 */     String maxWaitIntervalStr = System.getProperty("net.java.stun4j.MAX_WAIT_INTERVAL");
/*     */ 
/*     */     
/* 377 */     if (maxWaitIntervalStr != null && maxWaitIntervalStr.trim().length() > 0)
/*     */       
/*     */       try {
/*     */         
/* 381 */         this.maxWaitInterval = Integer.parseInt(maxWaitIntervalStr);
/*     */       }
/* 383 */       catch (NumberFormatException e) {
/*     */         
/* 385 */         logger.log(Level.FINE, "Failed to parse MAX_WAIT_INTERVAL", e);
/* 386 */         this.maxWaitInterval = 1600;
/*     */       }  
/*     */   }
/*     */ }
