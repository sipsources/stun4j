/*     */ package net.java.stun4j;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.message.MessageFactory;
/*     */ import net.java.stun4j.message.Request;
/*     */ import net.java.stun4j.message.Response;
/*     */ import net.java.stun4j.stack.RequestListener;
/*     */ import net.java.stun4j.stack.StunStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageEventDispatchingTest
/*     */   extends TestCase
/*     */ {
/*  16 */   StunStack stunStack = null;
/*     */   
/*  18 */   StunAddress clientAddress = new StunAddress("127.0.0.1", 5216);
/*  19 */   StunAddress serverAddress = new StunAddress("127.0.0.2", 5255);
/*  20 */   StunAddress serverAddress2 = new StunAddress("127.0.0.2", 5259);
/*     */   
/*  22 */   NetAccessPointDescriptor clientAccessPoint = null;
/*  23 */   NetAccessPointDescriptor serverAccessPoint = null;
/*  24 */   NetAccessPointDescriptor serverAccessPoint2 = null;
/*     */   
/*  26 */   Request bindingRequest = null;
/*  27 */   Response bindingResponse = null;
/*     */   
/*  29 */   PlainRequestCollector requestCollector = null;
/*  30 */   PlainResponseCollector responseCollector = null;
/*     */ 
/*     */   
/*     */   protected void setUp() throws Exception {
/*  34 */     super.setUp();
/*     */     
/*  36 */     this.stunStack = StunStack.getInstance();
/*  37 */     this.stunStack.start();
/*     */     
/*  39 */     this.clientAccessPoint = new NetAccessPointDescriptor(this.clientAddress);
/*  40 */     this.serverAccessPoint = new NetAccessPointDescriptor(this.serverAddress);
/*  41 */     this.serverAccessPoint2 = new NetAccessPointDescriptor(this.serverAddress2);
/*     */     
/*  43 */     this.stunStack.installNetAccessPoint(this.clientAccessPoint);
/*  44 */     this.stunStack.installNetAccessPoint(this.serverAccessPoint);
/*  45 */     this.stunStack.installNetAccessPoint(this.serverAccessPoint2);
/*     */     
/*  47 */     this.bindingRequest = MessageFactory.createBindingRequest();
/*  48 */     this.bindingResponse = MessageFactory.createBindingResponse(this.clientAddress, this.clientAddress, this.serverAddress);
/*     */ 
/*     */     
/*  51 */     this.requestCollector = new PlainRequestCollector();
/*  52 */     this.responseCollector = new PlainResponseCollector();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  58 */     this.clientAccessPoint = null;
/*  59 */     this.serverAccessPoint = null;
/*  60 */     this.requestCollector = null;
/*  61 */     this.responseCollector = null;
/*     */     
/*  63 */     StunStack.shutDown();
/*     */     
/*  65 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testClientTransactionTimeouts() throws Exception {
/*  76 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */     
/*  80 */     Thread.currentThread(); Thread.sleep(12000L);
/*     */     
/*  82 */     assertEquals("No timeout was produced upon expiration of a client transaction", this.responseCollector.receivedResponses.size(), 1);
/*     */ 
/*     */ 
/*     */     
/*  86 */     assertEquals("No timeout was produced upon expiration of a client transaction", this.responseCollector.receivedResponses.get(0), "timeout");
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
/*     */   public void testEventDispatchingUponIncomingRequests() throws Exception {
/*  99 */     this.stunStack.getProvider().addRequestListener(this.requestCollector);
/*     */     
/* 101 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     Thread.currentThread(); Thread.sleep(500L);
/*     */ 
/*     */     
/* 109 */     assertTrue("No MessageEvents have been dispatched", (this.requestCollector.receivedRequests.size() == 1));
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
/*     */   public void testSelectiveEventDispatchingUponIncomingRequests() throws Exception {
/* 123 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 126 */     PlainRequestCollector requestCollector2 = new PlainRequestCollector();
/* 127 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint2, requestCollector2);
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress2, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     Thread.currentThread(); Thread.sleep(500L);
/*     */ 
/*     */     
/* 139 */     assertTrue("A MessageEvent was received by a non-interested selective listener", (this.requestCollector.receivedRequests.size() == 0));
/*     */ 
/*     */     
/* 142 */     assertTrue("No MessageEvents have been dispatched for a selective listener", (requestCollector2.receivedRequests.size() == 1));
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
/*     */   public void testServerResponseRetransmissions() throws Exception {
/* 155 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 158 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     Thread.currentThread(); Thread.sleep(500L);
/*     */     
/* 166 */     StunMessageEvent evt = this.requestCollector.receivedRequests.get(0);
/*     */     
/* 168 */     byte[] tid = evt.getMessage().getTransactionID();
/* 169 */     this.stunStack.getProvider().sendResponse(tid, this.bindingResponse, this.serverAccessPoint, this.clientAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     Thread.currentThread(); Thread.sleep(500L);
/*     */ 
/*     */     
/* 178 */     assertTrue("There were no retransmissions of a binding response", (this.responseCollector.receivedResponses.size() == 1));
/*     */   }
/*     */   
/*     */   private class PlainRequestCollector
/*     */     implements RequestListener
/*     */   {
/* 184 */     public Vector receivedRequests = new Vector(); private final MessageEventDispatchingTest this$0;
/*     */     
/*     */     public void requestReceived(StunMessageEvent evt) {
/* 187 */       this.receivedRequests.add(evt);
/*     */     }
/*     */     
/*     */     private PlainRequestCollector() {} }
/*     */   
/*     */   private class PlainResponseCollector implements ResponseCollector {
/* 193 */     public Vector receivedResponses = new Vector();
/*     */     private final MessageEventDispatchingTest this$0;
/*     */     
/*     */     public void processResponse(StunMessageEvent responseEvt) {
/* 197 */       this.receivedResponses.add(responseEvt);
/*     */     }
/*     */ 
/*     */     
/*     */     public void processTimeout() {
/* 202 */       this.receivedResponses.add(new String("timeout"));
/*     */     }
/*     */     
/*     */     private PlainResponseCollector() {}
/*     */   }
/*     */ }

