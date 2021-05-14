/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.ResponseCollector;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.message.Message;
/*     */ import net.java.stun4j.message.Request;
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
/*     */ public class StunProvider
/*     */   implements MessageEventHandler
/*     */ {
/*  30 */   private static final Logger logger = Logger.getLogger(StunProvider.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private Hashtable clientTransactions = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private Hashtable serverTransactions = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private StunStack stunStack = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private EventDispatcher eventDispatcher = new EventDispatcher();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StunProvider(StunStack stunStack) {
/*  63 */     this.stunStack = stunStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendRequest(Request request, StunAddress sendTo, NetAccessPointDescriptor sendThrough, ResponseCollector collector) throws StunException {
/*  88 */     this.stunStack.checkStarted();
/*     */     
/*  90 */     StunClientTransaction clientTransaction = new StunClientTransaction(this, request, sendTo, sendThrough, collector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     this.clientTransactions.put(clientTransaction.getTransactionID(), clientTransaction);
/*     */     
/*  99 */     clientTransaction.sendRequest();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendResponse(byte[] transactionID, Response response, NetAccessPointDescriptor sendThrough, StunAddress sendTo) throws StunException {
/* 126 */     this.stunStack.checkStarted();
/*     */     
/* 128 */     TransactionID tid = TransactionID.createTransactionID(transactionID);
/* 129 */     StunServerTransaction sTran = (StunServerTransaction)this.serverTransactions.get(tid);
/*     */ 
/*     */     
/* 132 */     if (sTran == null || sTran.isReransmitting()) {
/* 133 */       throw new StunException(3, "The transaction specified in the response object does not exist or has already transmitted a response.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     sTran.sendResponse(response, sendThrough, sendTo);
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
/*     */   public void addRequestListener(RequestListener requestListener) {
/* 152 */     this.eventDispatcher.addRequestListener(requestListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRequestListener(RequestListener listener) {
/* 163 */     this.eventDispatcher.removeRequestListener(listener);
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
/*     */   public synchronized void addRequestListener(NetAccessPointDescriptor apDescriptor, RequestListener listener) {
/* 179 */     this.eventDispatcher.addRequestListener(apDescriptor, listener);
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
/*     */   NetAccessManager getNetAccessManager() {
/* 192 */     return this.stunStack.getNetAccessManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void removeClientTransaction(StunClientTransaction tran) {
/* 202 */     this.clientTransactions.remove(tran.getTransactionID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void removeServerTransaction(StunServerTransaction tran) {
/* 213 */     this.serverTransactions.remove(tran.getTransactionID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMessageEvent(StunMessageEvent event) {
/* 222 */     Message msg = event.getMessage();
/*     */     
/* 224 */     if (logger.isLoggable(Level.FINEST)) {
/* 225 */       logger.finest("Received a message on NetAP" + event.getSourceAccessPoint() + " of type:" + msg.getMessageType());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     if (msg instanceof Request) {
/*     */       
/* 233 */       TransactionID serverTid = TransactionID.createTransactionID(msg.getTransactionID());
/*     */ 
/*     */       
/* 236 */       StunServerTransaction sTran = (StunServerTransaction)this.serverTransactions.get(serverTid);
/*     */       
/* 238 */       if (sTran != null) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 243 */           sTran.retransmitResponse();
/* 244 */           logger.finest("Response retransmitted");
/*     */         }
/* 246 */         catch (StunException ex) {
/*     */ 
/*     */           
/* 249 */           logger.log(Level.WARNING, "Failed to retransmit a stun response", (Throwable)ex);
/*     */         } 
/*     */ 
/*     */         
/* 253 */         String propagate = System.getProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS");
/*     */         
/* 255 */         if (propagate == null || !propagate.trim().equalsIgnoreCase("true")) {
/*     */           return;
/*     */         }
/*     */       } else {
/*     */         
/* 260 */         sTran = new StunServerTransaction(this, serverTid);
/*     */ 
/*     */         
/* 263 */         this.serverTransactions.put(serverTid, sTran);
/* 264 */         sTran.start();
/*     */       } 
/* 266 */       this.eventDispatcher.fireMessageEvent(event);
/*     */     
/*     */     }
/* 269 */     else if (msg instanceof Response) {
/*     */       
/* 271 */       TransactionID tid = TransactionID.createTransactionID(msg.getTransactionID());
/*     */ 
/*     */       
/* 274 */       StunClientTransaction tran = (StunClientTransaction)this.clientTransactions.remove(tid);
/*     */ 
/*     */       
/* 277 */       if (tran != null) {
/*     */         
/* 279 */         tran.handleResponse(event);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 284 */         logger.fine("Dropped response - no matching client tran found.");
/* 285 */         logger.fine("response tid was - " + tid.toString());
/* 286 */         logger.fine("all tids in stock were" + this.clientTransactions.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void shutDown() {
/* 297 */     this.eventDispatcher.removeAllListeners();
/*     */     
/* 299 */     Enumeration tids = this.clientTransactions.keys();
/* 300 */     while (tids.hasMoreElements()) {
/* 301 */       TransactionID item = tids.nextElement();
/* 302 */       StunClientTransaction tran = (StunClientTransaction)this.clientTransactions.remove(item);
/*     */       
/* 304 */       if (tran != null) {
/* 305 */         tran.cancel();
/*     */       }
/*     */     } 
/*     */     
/* 309 */     tids = this.serverTransactions.keys();
/* 310 */     while (tids.hasMoreElements()) {
/* 311 */       TransactionID item = tids.nextElement();
/* 312 */       StunServerTransaction tran = (StunServerTransaction)this.clientTransactions.remove(item);
/*     */       
/* 314 */       if (tran != null)
/* 315 */         tran.expire(); 
/*     */     } 
/*     */   }
/*     */ }

