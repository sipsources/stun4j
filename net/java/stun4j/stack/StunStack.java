/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.net.DatagramSocket;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunException;
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
/*     */ public class StunStack
/*     */ {
/*  29 */   private static StunStack stackInstance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private NetAccessManager netAccessManager = new NetAccessManager();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFAULT_THREAD_POOL_SIZE = 5;
/*     */ 
/*     */   
/*  41 */   private StunProvider stunProvider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized StunStack getInstance() {
/*  51 */     if (stackInstance == null)
/*  52 */       stackInstance = new StunStack(); 
/*  53 */     return stackInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  62 */     this.netAccessManager.start();
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
/*     */   public static void shutDown() {
/*  78 */     if (stackInstance == null)
/*     */       return; 
/*  80 */     stackInstance.stunProvider.shutDown();
/*  81 */     stackInstance.netAccessManager.shutDown();
/*     */     
/*  83 */     stackInstance.netAccessManager = null;
/*  84 */     stackInstance.stunProvider = null;
/*  85 */     stackInstance = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThreadPoolSize(int threadPoolSize) throws StunException {
/*  91 */     this.netAccessManager.setThreadPoolSize(threadPoolSize);
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
/*     */   public void installNetAccessPoint(NetAccessPointDescriptor apDescriptor) throws StunException {
/* 106 */     checkStarted();
/*     */     
/* 108 */     this.netAccessManager.installNetAccessPoint(apDescriptor);
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
/*     */   public NetAccessPointDescriptor installNetAccessPoint(DatagramSocket sock) throws StunException {
/* 124 */     checkStarted();
/*     */     
/* 126 */     return this.netAccessManager.installNetAccessPoint(sock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNetAccessPoint(NetAccessPointDescriptor apDescriptor) throws StunException {
/* 137 */     checkStarted();
/*     */     
/* 139 */     this.netAccessManager.removeNetAccessPoint(apDescriptor);
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
/*     */   public StunProvider getProvider() {
/* 152 */     return this.stunProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StunStack() {
/* 161 */     this.stunProvider = new StunProvider(this);
/*     */     
/* 163 */     this.netAccessManager.setEventHandler(getProvider());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkStarted() throws StunException {
/* 173 */     if (!this.netAccessManager.isRunning()) {
/* 174 */       throw new StunException(1, "The stack needs to be started, for the requested method to work.");
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
/*     */   NetAccessManager getNetAccessManager() {
/* 186 */     return this.netAccessManager;
/*     */   }
/*     */ }

