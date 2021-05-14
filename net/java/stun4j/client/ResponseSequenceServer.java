/*     */ package net.java.stun4j.client;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.message.Response;
/*     */ import net.java.stun4j.stack.RequestListener;
/*     */ import net.java.stun4j.stack.StunProvider;
/*     */ import net.java.stun4j.stack.StunStack;
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
/*     */ public class ResponseSequenceServer
/*     */   implements RequestListener
/*     */ {
/*  29 */   private static final Logger logger = Logger.getLogger(ResponseSequenceServer.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private Vector messageSequence = new Vector();
/*     */   
/*  36 */   private StunStack stunStack = null;
/*  37 */   private StunProvider stunProvider = null;
/*     */   
/*  39 */   private StunAddress serverAddress = null;
/*  40 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */ 
/*     */   
/*     */   public ResponseSequenceServer(StunAddress bindAddress) {
/*  44 */     this.serverAddress = bindAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() throws StunException {
/*  54 */     this.stunStack = StunStack.getInstance();
/*  55 */     this.stunProvider = this.stunStack.getProvider();
/*     */     
/*  57 */     this.apDescriptor = new NetAccessPointDescriptor(this.serverAddress);
/*     */     
/*  59 */     this.stunStack.installNetAccessPoint(this.apDescriptor);
/*  60 */     this.stunProvider.addRequestListener(this.apDescriptor, this);
/*  61 */     this.stunStack.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutDown() {
/*  70 */     this.messageSequence.removeAllElements();
/*     */     
/*  72 */     StunStack.shutDown();
/*     */     
/*  74 */     this.stunStack = null;
/*  75 */     this.stunProvider = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMessage(Response response) {
/*  85 */     if (response == null) {
/*     */ 
/*     */       
/*  88 */       this.messageSequence.add(new Boolean(false));
/*     */     } else {
/*     */       
/*  91 */       this.messageSequence.add(response);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestReceived(StunMessageEvent evt) {
/* 102 */     if (this.messageSequence.isEmpty())
/*     */       return; 
/* 104 */     Object obj = this.messageSequence.remove(0);
/*     */     
/* 106 */     if (!(obj instanceof Response)) {
/*     */       return;
/*     */     }
/* 109 */     Response res = (Response)obj;
/*     */ 
/*     */     
/*     */     try {
/* 113 */       this.stunProvider.sendResponse(evt.getMessage().getTransactionID(), res, this.apDescriptor, evt.getRemoteAddress());
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 118 */     catch (Exception ex) {
/*     */       
/* 120 */       logger.log(Level.WARNING, "failed to send a response", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     return (this.serverAddress == null) ? "null" : this.serverAddress.toString();
/*     */   }
/*     */ }

