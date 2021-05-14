/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.SocketException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunAddress;
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
/*     */ class NetAccessPoint
/*     */   implements Runnable
/*     */ {
/*  30 */   private static final Logger logger = Logger.getLogger(NetAccessPoint.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_DATAGRAM_SIZE = 8192;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private MessageQueue messageQueue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DatagramSocket sock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isUsingExternalSocket = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRunning;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private ErrorHandler errorHandler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private Object socketLock = new Object();
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
/*     */   NetAccessPoint(NetAccessPointDescriptor apDescriptor, MessageQueue messageQueue, ErrorHandler errorHandler) {
/*  94 */     this.apDescriptor = apDescriptor;
/*  95 */     this.messageQueue = messageQueue;
/*  96 */     this.errorHandler = errorHandler;
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
/*     */   void start() throws IOException {
/* 108 */     synchronized (this.socketLock) {
/*     */ 
/*     */ 
/*     */       
/* 112 */       if (this.sock == null) {
/*     */         
/* 114 */         this.sock = new DatagramSocket(getDescriptor().getAddress().getSocketAddress());
/*     */         
/* 116 */         this.isUsingExternalSocket = false;
/* 117 */         logger.info("Bound a socket on ap: " + toString());
/*     */       } 
/*     */       
/* 120 */       this.sock.setReceiveBufferSize(8192);
/* 121 */       this.isRunning = true;
/* 122 */       Thread thread = new Thread(this);
/* 123 */       thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NetAccessPointDescriptor getDescriptor() {
/* 134 */     return this.apDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 142 */     while (this.isRunning) {
/*     */ 
/*     */       
/*     */       try {
/* 146 */         int bufsize = this.sock.getReceiveBufferSize();
/* 147 */         byte[] message = new byte[bufsize];
/* 148 */         DatagramPacket packet = new DatagramPacket(message, bufsize);
/*     */         
/* 150 */         this.sock.receive(packet);
/*     */         
/* 152 */         RawMessage rawMessage = new RawMessage(message, packet.getLength(), packet.getAddress(), packet.getPort(), getDescriptor());
/*     */ 
/*     */ 
/*     */         
/* 156 */         this.messageQueue.add(rawMessage);
/*     */       }
/* 158 */       catch (SocketException ex) {
/*     */         
/* 160 */         if (this.isRunning)
/*     */         {
/* 162 */           logger.log(Level.WARNING, "A net access point has gone useless:", ex);
/*     */ 
/*     */           
/* 165 */           stop();
/*     */           
/* 167 */           this.errorHandler.handleFatalError(this, "A socket exception was thrown while trying to receive a message.", ex);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 179 */       catch (IOException ex) {
/*     */         
/* 181 */         logger.log(Level.WARNING, "A net access point has gone useless:", ex);
/*     */ 
/*     */         
/* 184 */         this.errorHandler.handleError(ex.getMessage(), ex);
/*     */       
/*     */       }
/* 187 */       catch (Throwable ex) {
/*     */         
/* 189 */         logger.log(Level.WARNING, "A net access point has gone useless:", ex);
/*     */ 
/*     */         
/* 192 */         stop();
/* 193 */         this.errorHandler.handleFatalError(this, "Unknown error occurred while listening for messages!", ex);
/*     */       } 
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
/*     */ 
/*     */   
/*     */   synchronized void stop() {
/* 219 */     this.isRunning = false;
/*     */ 
/*     */     
/* 222 */     if (this.sock != null && !this.isUsingExternalSocket)
/*     */     {
/*     */       
/* 225 */       synchronized (this.socketLock) {
/*     */         
/* 227 */         this.sock.close();
/*     */         
/* 229 */         logger.info("Closed socket on ap " + toString());
/* 230 */         this.sock = null;
/* 231 */         String hardSocketClose = System.getProperty("net.java.stun4j.stack.HARD_SOCK_CLOSE");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 236 */         if (hardSocketClose == null || hardSocketClose.equalsIgnoreCase("true")) {
/*     */ 
/*     */ 
/*     */           
/* 240 */           int waitForSockClose = 200;
/*     */           
/*     */           try {
/* 243 */             String waitForSockCloseStr = System.getProperty("net.java.stun4j.stack.WAIT_FOR_SOCK_CLOSE");
/*     */ 
/*     */             
/* 246 */             if (waitForSockCloseStr != null && waitForSockCloseStr.length() > 0)
/*     */             {
/*     */               
/* 249 */               waitForSockClose = Integer.parseInt(System.getProperty(waitForSockCloseStr));
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 254 */           catch (Throwable t) {
/*     */             
/* 256 */             logger.log(Level.WARNING, "Failed to parse wait_for_sock_close prop", t);
/*     */ 
/*     */ 
/*     */             
/* 260 */             if (waitForSockClose < 0)
/*     */             {
/* 262 */               waitForSockClose = 200;
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 269 */             wait(waitForSockClose);
/*     */           }
/* 271 */           catch (InterruptedException t) {
/*     */             
/* 273 */             logger.warning("Interrupted waiting for sock close.");
/*     */           } 
/* 275 */           System.gc();
/*     */         } 
/*     */       } 
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
/*     */   void sendMessage(byte[] message, StunAddress address) throws IOException {
/* 290 */     DatagramPacket datagramPacket = new DatagramPacket(message, 0, message.length, address.getSocketAddress());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     synchronized (this.socketLock) {
/* 296 */       this.sock.send(datagramPacket);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 306 */     return "net.java.stun4j.stack.AccessPoint@" + this.apDescriptor.getAddress() + " status: " + (this.isRunning ? "not" : "") + " running";
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
/*     */   void useExternalSocket(DatagramSocket socket) {
/* 320 */     this.sock = socket;
/* 321 */     this.isUsingExternalSocket = true;
/*     */   }
/*     */   
/*     */   NetAccessPoint() {}
/*     */ }
