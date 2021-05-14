/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.message.Message;
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
/*     */ class MessageProcessor
/*     */   implements Runnable
/*     */ {
/*  26 */   private static final Logger logger = Logger.getLogger(MessageProcessor.class.getName());
/*     */ 
/*     */   
/*  29 */   private MessageQueue messageQueue = null;
/*  30 */   private MessageEventHandler messageHandler = null;
/*  31 */   private ErrorHandler errorHandler = null;
/*     */   
/*     */   private boolean isRunning = false;
/*  34 */   private Thread runningThread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageProcessor(MessageQueue queue, MessageEventHandler messageHandler, ErrorHandler errorHandler) {
/*  40 */     this.messageQueue = queue;
/*  41 */     this.messageHandler = messageHandler;
/*  42 */     this.errorHandler = errorHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  54 */       while (this.isRunning)
/*     */       {
/*  56 */         RawMessage rawMessage = null;
/*     */         
/*     */         try {
/*  59 */           rawMessage = this.messageQueue.remove();
/*     */         }
/*  61 */         catch (InterruptedException ex) {
/*     */           
/*  63 */           if (isRunning()) {
/*  64 */             logger.log(Level.WARNING, "A net access point has gone useless:", ex);
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  72 */         if (!isRunning()) {
/*     */           return;
/*     */         }
/*     */         
/*  76 */         if (rawMessage == null) {
/*     */           continue;
/*     */         }
/*  79 */         Message stunMessage = null;
/*     */         
/*     */         try {
/*  82 */           stunMessage = Message.decode(rawMessage.getBytes(), false, (char)rawMessage.getMessageLength());
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*  87 */         catch (StunException ex) {
/*     */           
/*  89 */           this.errorHandler.handleError("Failed to decode a stun mesage!", (Throwable)ex);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*  94 */         StunMessageEvent stunMessageEvent = new StunMessageEvent(rawMessage.getNetAccessPoint(), stunMessage, new StunAddress(rawMessage.getRemoteAddress().getAddress(), rawMessage.getRemoteAddress().getPort()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 100 */         this.messageHandler.handleMessageEvent(stunMessageEvent);
/*     */       }
/*     */     
/* 103 */     } catch (Throwable err) {
/*     */ 
/*     */       
/* 106 */       this.errorHandler.handleFatalError(this, "Unexpected Error!", err);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void start() {
/* 115 */     this.isRunning = true;
/*     */     
/* 117 */     this.runningThread = new Thread(this);
/* 118 */     this.runningThread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stop() {
/* 127 */     this.isRunning = false;
/* 128 */     this.runningThread.interrupt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isRunning() {
/* 139 */     return this.isRunning;
/*     */   }
/*     */ }
