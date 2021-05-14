/*     */ package net.java.stun4j.client;
/*     */ 
/*     */ import java.net.DatagramSocket;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.attribute.MappedAddressAttribute;
/*     */ import net.java.stun4j.message.MessageFactory;
/*     */ import net.java.stun4j.message.Response;
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
/*     */ public class SimpleAddressDetector
/*     */ {
/*     */   private boolean started = false;
/*  38 */   private StunStack stunStack = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private StunProvider stunProvider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private StunAddress serverAddress = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private BlockingRequestSender requestSender = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleAddressDetector(StunAddress serverAddress) {
/*  63 */     this.serverAddress = serverAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutDown() {
/*  73 */     StunStack.shutDown();
/*  74 */     this.stunStack = null;
/*  75 */     this.stunProvider = null;
/*  76 */     this.requestSender = null;
/*     */     
/*  78 */     this.started = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() throws StunException {
/*  88 */     this.stunStack = StunStack.getInstance();
/*  89 */     this.stunStack.start();
/*     */     
/*  91 */     this.stunProvider = this.stunStack.getProvider();
/*     */ 
/*     */ 
/*     */     
/*  95 */     this.started = true;
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
/*     */   public StunAddress getMappingFor(StunAddress address) throws StunException {
/* 111 */     NetAccessPointDescriptor apDesc = new NetAccessPointDescriptor(address);
/*     */     
/* 113 */     this.stunStack.installNetAccessPoint(apDesc);
/*     */     
/* 115 */     this.requestSender = new BlockingRequestSender(this.stunProvider, apDesc);
/* 116 */     StunMessageEvent evt = null;
/*     */     
/*     */     try {
/* 119 */       evt = this.requestSender.sendRequestAndWaitForResponse(MessageFactory.createBindingRequest(), this.serverAddress);
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 125 */       this.stunStack.removeNetAccessPoint(apDesc);
/*     */     } 
/*     */     
/* 128 */     if (evt != null) {
/*     */       
/* 130 */       Response res = (Response)evt.getMessage();
/* 131 */       MappedAddressAttribute maAtt = (MappedAddressAttribute)res.getAttribute('\001');
/*     */ 
/*     */       
/* 134 */       if (maAtt != null) {
/* 135 */         return maAtt.getAddress();
/*     */       }
/*     */     } 
/* 138 */     return null;
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
/*     */   public StunAddress getMappingFor(DatagramSocket socket) throws StunException {
/* 152 */     NetAccessPointDescriptor apDesc = this.stunStack.installNetAccessPoint(socket);
/*     */     
/* 154 */     this.requestSender = new BlockingRequestSender(this.stunProvider, apDesc);
/* 155 */     StunMessageEvent evt = null;
/*     */     
/*     */     try {
/* 158 */       evt = this.requestSender.sendRequestAndWaitForResponse(MessageFactory.createBindingRequest(), this.serverAddress);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 163 */       this.stunStack.removeNetAccessPoint(apDesc);
/*     */     } 
/*     */     
/* 166 */     if (evt != null) {
/*     */       
/* 168 */       Response res = (Response)evt.getMessage();
/* 169 */       MappedAddressAttribute maAtt = (MappedAddressAttribute)res.getAttribute('\001');
/*     */ 
/*     */       
/* 172 */       if (maAtt != null) {
/* 173 */         return maAtt.getAddress();
/*     */       }
/*     */     } 
/* 176 */     return null;
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
/*     */   public StunAddress getMappingFor(int port) throws StunException {
/* 191 */     return getMappingFor(new StunAddress(port));
/*     */   }
/*     */ }

