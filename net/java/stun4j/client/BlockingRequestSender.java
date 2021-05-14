/*     */ package net.java.stun4j.client;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.ResponseCollector;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.message.Request;
/*     */ import net.java.stun4j.stack.StunProvider;
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
/*     */ class BlockingRequestSender
/*     */   implements ResponseCollector
/*     */ {
/*  39 */   private static final Logger logger = Logger.getLogger(BlockingRequestSender.class.getName());
/*     */ 
/*     */   
/*  42 */   private StunProvider stunProvider = null;
/*  43 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */   
/*  45 */   StunMessageEvent responseEvent = null;
/*     */   
/*     */   private boolean ended = false;
/*  48 */   private Object sendLock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   BlockingRequestSender(StunProvider stunProvider, NetAccessPointDescriptor apDescriptor) {
/*  53 */     this.stunProvider = stunProvider;
/*  54 */     this.apDescriptor = apDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void processResponse(StunMessageEvent evt) {
/*  64 */     synchronized (this.sendLock) {
/*  65 */       this.responseEvent = evt;
/*  66 */       this.ended = true;
/*  67 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void processTimeout() {
/*  77 */     synchronized (this.sendLock) {
/*  78 */       this.ended = true;
/*  79 */       notifyAll();
/*     */     } 
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
/*     */   public synchronized StunMessageEvent sendRequestAndWaitForResponse(Request request, StunAddress serverAddress) throws StunException {
/*  98 */     synchronized (this.sendLock) {
/*  99 */       this.stunProvider.sendRequest(request, serverAddress, this.apDescriptor, this);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     this.ended = false;
/* 104 */     while (!this.ended) {
/*     */       
/*     */       try {
/* 107 */         wait();
/*     */       }
/* 109 */       catch (InterruptedException ex) {
/*     */         
/* 111 */         logger.log(Level.WARNING, "Interrupted", ex);
/*     */       } 
/*     */     } 
/* 114 */     StunMessageEvent res = this.responseEvent;
/* 115 */     this.responseEvent = null;
/*     */     
/* 117 */     return res;
/*     */   }
/*     */ }


