/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NetAccessManager
/*     */   implements ErrorHandler
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(NetAccessManager.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private Hashtable netAccessPoints = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private MessageQueue messageQueue = new MessageQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private Vector messageProcessors = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private MessageEventHandler messageEventHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRunning = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private int initialThreadPoolSize = 5;
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
/*     */   synchronized void start() {
/*  85 */     if (this.isRunning) {
/*     */       return;
/*     */     }
/*  88 */     this.isRunning = true;
/*  89 */     initThreadPool();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void shutDown() {
/*  97 */     if (!this.isRunning) {
/*     */       return;
/*     */     }
/*     */     
/* 101 */     logger.info("removing " + this.netAccessPoints.size() + " access points.");
/* 102 */     Enumeration keys = this.netAccessPoints.keys();
/* 103 */     while (keys.hasMoreElements()) {
/*     */       
/* 105 */       NetAccessPointDescriptor apd = keys.nextElement();
/*     */       
/* 107 */       removeNetAccessPoint(apd);
/* 108 */       logger.info(".");
/*     */     } 
/* 110 */     logger.info("removed all access points");
/*     */ 
/*     */     
/* 113 */     while (!this.messageProcessors.isEmpty()) {
/*     */       
/* 115 */       MessageProcessor mp = this.messageProcessors.remove(0);
/* 116 */       mp.stop();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     this.isRunning = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isRunning() {
/* 135 */     return this.isRunning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setEventHandler(MessageEventHandler evtHandler) {
/* 145 */     this.messageEventHandler = evtHandler;
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
/*     */   public void handleError(String message, Throwable error) {
/* 159 */     logger.log(Level.WARNING, "The following error occurred", error);
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
/*     */   public void handleFatalError(Runnable callingThread, String message, Throwable error) {
/* 172 */     if (callingThread instanceof NetAccessPoint) {
/*     */       
/* 174 */       NetAccessPoint ap = (NetAccessPoint)callingThread;
/*     */ 
/*     */       
/* 177 */       removeNetAccessPoint(ap.getDescriptor());
/*     */ 
/*     */       
/*     */       try {
/* 181 */         logger.log(Level.WARNING, "An access point has unexpectedly stopped. AP:" + ap.toString(), error);
/*     */         
/* 183 */         installNetAccessPoint(ap.getDescriptor());
/*     */       }
/* 185 */       catch (StunException ex) {
/*     */ 
/*     */         
/* 188 */         removeNetAccessPoint(ap.getDescriptor());
/* 189 */         logger.log(Level.WARNING, "Failed to relaunch accesspoint:" + ap, (Throwable)ex);
/*     */       }
/*     */     
/*     */     }
/* 193 */     else if (callingThread instanceof MessageProcessor) {
/*     */       
/* 195 */       MessageProcessor mp = (MessageProcessor)callingThread;
/* 196 */       logger.log(Level.WARNING, "A message processor has unexpectedly stopped. AP:" + mp.toString(), error);
/*     */ 
/*     */ 
/*     */       
/* 200 */       mp.stop();
/* 201 */       this.messageProcessors.remove(mp);
/*     */       
/* 203 */       mp = new MessageProcessor(this.messageQueue, this.messageEventHandler, this);
/* 204 */       mp.start();
/* 205 */       logger.fine("A message processor has been relaunched because of an error.");
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
/*     */   void installNetAccessPoint(NetAccessPointDescriptor apDescriptor) throws StunException {
/* 221 */     if (this.netAccessPoints.containsKey(apDescriptor)) {
/*     */       return;
/*     */     }
/* 224 */     NetAccessPoint ap = new NetAccessPoint(apDescriptor, this.messageQueue, this);
/* 225 */     this.netAccessPoints.put(apDescriptor, ap);
/*     */ 
/*     */     
/*     */     try {
/* 229 */       ap.start();
/*     */     }
/* 231 */     catch (IOException ex) {
/*     */       
/* 233 */       logger.log(Level.WARNING, "The NAPD(" + ap + ") failed to bind ", ex);
/* 234 */       throw new StunException(4, "An IOException occurred while starting access point: " + apDescriptor.toString(), ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NetAccessPointDescriptor installNetAccessPoint(DatagramSocket socket) throws StunException {
/* 257 */     StunAddress address = new StunAddress(socket.getLocalAddress(), socket.getLocalPort());
/* 258 */     NetAccessPointDescriptor apDescriptor = new NetAccessPointDescriptor(address);
/*     */     
/* 260 */     if (this.netAccessPoints.containsKey(apDescriptor)) {
/* 261 */       return apDescriptor;
/*     */     }
/* 263 */     NetAccessPoint ap = new NetAccessPoint(apDescriptor, this.messageQueue, this);
/*     */ 
/*     */     
/* 266 */     ap.useExternalSocket(socket);
/* 267 */     this.netAccessPoints.put(apDescriptor, ap);
/*     */ 
/*     */     
/*     */     try {
/* 271 */       ap.start();
/*     */     }
/* 273 */     catch (IOException ex) {
/*     */       
/* 275 */       throw new StunException(4, "An IOException occurred while starting the access point", ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     return apDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeNetAccessPoint(NetAccessPointDescriptor apDescriptor) {
/* 291 */     NetAccessPoint ap = (NetAccessPoint)this.netAccessPoints.remove(apDescriptor);
/*     */     
/* 293 */     if (ap != null) {
/* 294 */       ap.stop();
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
/*     */   void setThreadPoolSize(int threadPoolSize) throws StunException {
/* 310 */     if (threadPoolSize < 1) {
/* 311 */       throw new StunException(2, threadPoolSize + " is not a legal thread pool size value.");
/*     */     }
/*     */ 
/*     */     
/* 315 */     if (!this.isRunning) {
/*     */       
/* 317 */       this.initialThreadPoolSize = threadPoolSize;
/*     */       
/*     */       return;
/*     */     } 
/* 321 */     if (this.messageProcessors.size() < threadPoolSize) {
/*     */ 
/*     */       
/* 324 */       fillUpThreadPool(threadPoolSize);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 329 */       shrinkThreadPool(threadPoolSize);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initThreadPool() {
/* 339 */     fillUpThreadPool(this.initialThreadPoolSize);
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
/*     */   private void fillUpThreadPool(int newSize) {
/* 351 */     this.messageProcessors.ensureCapacity(newSize);
/*     */     
/* 353 */     for (int i = this.messageProcessors.size(); i < newSize; i++) {
/*     */       
/* 355 */       MessageProcessor mp = new MessageProcessor(this.messageQueue, this.messageEventHandler, this);
/*     */ 
/*     */       
/* 358 */       this.messageProcessors.add(mp);
/*     */       
/* 360 */       mp.start();
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
/*     */   private void shrinkThreadPool(int newSize) {
/* 372 */     while (this.messageProcessors.size() > newSize) {
/*     */       
/* 374 */       MessageProcessor mp = this.messageProcessors.remove(0);
/* 375 */       mp.stop();
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
/*     */   
/*     */   void sendMessage(Message stunMessage, NetAccessPointDescriptor apDescriptor, StunAddress address) throws StunException {
/* 395 */     byte[] bytes = stunMessage.encode();
/* 396 */     NetAccessPoint ap = (NetAccessPoint)this.netAccessPoints.get(apDescriptor);
/*     */     
/* 398 */     if (ap == null) {
/* 399 */       throw new StunException(2, "The specified access point had not been installed.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 405 */       ap.sendMessage(bytes, address);
/*     */     }
/* 407 */     catch (Exception ex) {
/*     */       
/* 409 */       throw new StunException(4, "An Exception occurred while sending message bytes through a network socket!", ex);
/*     */     } 
/*     */   }
/*     */ }

