/*     */ package net.java.stun4j;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public class TransactionSupportTests
/*     */   extends TestCase
/*     */ {
/*  18 */   StunStack stunStack = null;
/*     */   
/*  20 */   StunAddress clientAddress = new StunAddress("127.0.0.1", 5216);
/*  21 */   StunAddress serverAddress = new StunAddress("127.0.0.2", 5255);
/*     */   
/*  23 */   NetAccessPointDescriptor clientAccessPoint = null;
/*  24 */   NetAccessPointDescriptor serverAccessPoint = null;
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
/*     */     
/*  42 */     this.stunStack.installNetAccessPoint(this.clientAccessPoint);
/*  43 */     this.stunStack.installNetAccessPoint(this.serverAccessPoint);
/*     */     
/*  45 */     this.bindingRequest = MessageFactory.createBindingRequest();
/*  46 */     this.bindingResponse = MessageFactory.createBindingResponse(this.clientAddress, this.clientAddress, this.serverAddress);
/*     */ 
/*     */     
/*  49 */     this.requestCollector = new PlainRequestCollector();
/*  50 */     this.responseCollector = new PlainResponseCollector();
/*     */     
/*  52 */     System.setProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS", "false");
/*     */     
/*  54 */     System.setProperty("net.java.stun4j.KEEP_CLIENT_TRANS_AFTER_A_RESPONSE", "false");
/*     */     
/*  56 */     System.setProperty("net.java.stun4j.MAX_RETRANSMISSIONS", "");
/*     */     
/*  58 */     System.setProperty("net.java.stun4j.MAX_WAIT_INTERVAL", "");
/*     */     
/*  60 */     System.setProperty("net.java.stun4j.ORIGINAL_WAIT_INTERVAL", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  68 */     this.clientAccessPoint = null;
/*  69 */     this.serverAccessPoint = null;
/*  70 */     this.requestCollector = null;
/*  71 */     this.responseCollector = null;
/*     */     
/*  73 */     System.setProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS", "false");
/*     */     
/*  75 */     System.setProperty("net.java.stun4j.KEEP_CLIENT_TRANS_AFTER_A_RESPONSE", "false");
/*     */     
/*  77 */     System.setProperty("net.java.stun4j.MAX_RETRANSMISSIONS", "");
/*     */     
/*  79 */     System.setProperty("net.java.stun4j.MAX_WAIT_INTERVAL", "");
/*     */     
/*  81 */     System.setProperty("net.java.stun4j.ORIGINAL_WAIT_INTERVAL", "");
/*     */ 
/*     */     
/*  84 */     StunStack.shutDown();
/*     */     
/*  86 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testClientRetransmissions() throws Exception {
/*  97 */     System.setProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS", "true");
/*     */     
/*  99 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 102 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     Thread.currentThread(); Thread.sleep(12000L);
/*     */ 
/*     */     
/* 110 */     assertTrue("No retransmissions of the request have been received", (this.requestCollector.receivedRequests.size() > 1));
/*     */     
/* 112 */     assertTrue("The binding request has not been retransmitted enough!", (this.requestCollector.receivedRequests.size() >= 9));
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
/*     */   public void testServerRetransmissionHiding() throws Exception {
/* 125 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 128 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     Thread.currentThread(); Thread.sleep(12000L);
/*     */ 
/*     */     
/* 136 */     assertTrue("Retransmissions of a binding request were propagated to the server", (this.requestCollector.receivedRequests.size() <= 1));
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
/*     */   public void testServerResponseRetransmissions() throws Exception {
/* 150 */     System.setProperty("net.java.stun4j.KEEP_CLIENT_TRANS_AFTER_A_RESPONSE", "true");
/*     */     
/* 152 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 155 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     Thread.currentThread(); Thread.sleep(500L);
/*     */     
/* 163 */     StunMessageEvent evt = this.requestCollector.receivedRequests.get(0);
/*     */     
/* 165 */     byte[] tid = evt.getMessage().getTransactionID();
/* 166 */     this.stunStack.getProvider().sendResponse(tid, this.bindingResponse, this.serverAccessPoint, this.clientAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     Thread.currentThread(); Thread.sleep(12000L);
/*     */ 
/*     */     
/* 175 */     assertTrue("There were no retransmissions of a binding response", (this.responseCollector.receivedResponses.size() < 5));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testUniqueIDs() throws Exception {
/* 186 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 189 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     Thread.currentThread(); Thread.sleep(500L);
/*     */     
/* 196 */     StunMessageEvent evt1 = this.requestCollector.receivedRequests.get(0);
/*     */ 
/*     */ 
/*     */     
/* 200 */     byte[] tid = evt1.getMessage().getTransactionID();
/* 201 */     this.stunStack.getProvider().sendResponse(tid, this.bindingResponse, this.serverAccessPoint, this.clientAddress);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     Thread.currentThread(); Thread.sleep(12000L);
/*     */     
/* 214 */     StunMessageEvent evt2 = this.requestCollector.receivedRequests.get(0);
/*     */ 
/*     */     
/* 217 */     assertTrue("Consecutive requests were assigned the same transaction id", Arrays.equals(evt1.getMessage().getTransactionID(), evt2.getMessage().getTransactionID()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testClientTransactionMaxRetransmisssionsConfigurationParameter() throws Exception {
/* 227 */     System.setProperty("net.java.stun4j.MAX_RETRANSMISSIONS", "2");
/*     */ 
/*     */     
/* 230 */     System.setProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS", "true");
/*     */     
/* 232 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 235 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     Thread.currentThread(); Thread.sleep(1600L);
/*     */ 
/*     */     
/* 243 */     assertTrue("No retransmissions of the request have been received", (this.requestCollector.receivedRequests.size() > 1));
/*     */     
/* 245 */     assertTrue("The MAX_RETRANSMISSIONS param was not taken into account!", (this.requestCollector.receivedRequests.size() == 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testMinWaitIntervalConfigurationParameter() throws Exception {
/* 254 */     System.setProperty("net.java.stun4j.ORIGINAL_WAIT_INTERVAL", "1000");
/*     */ 
/*     */     
/* 257 */     System.setProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS", "true");
/*     */     
/* 259 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 262 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     Thread.currentThread(); Thread.sleep(500L);
/*     */ 
/*     */     
/* 271 */     assertTrue("A retransmissions of the request was sent too early", (this.requestCollector.receivedRequests.size() < 2));
/*     */ 
/*     */ 
/*     */     
/* 275 */     Thread.currentThread(); Thread.sleep(600L);
/*     */ 
/*     */     
/* 278 */     assertTrue("A retransmissions of the request was not sent", (this.requestCollector.receivedRequests.size() == 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testMaxWaitIntervalConfigurationParameter() throws Exception {
/* 286 */     System.setProperty("net.java.stun4j.MAX_WAIT_INTERVAL", "100");
/*     */ 
/*     */     
/* 289 */     System.setProperty("net.java.stun4j.PROPAGATE_RECEIVED_RETRANSMISSIONS", "true");
/*     */     
/* 291 */     this.stunStack.getProvider().addRequestListener(this.serverAccessPoint, this.requestCollector);
/*     */ 
/*     */     
/* 294 */     this.stunStack.getProvider().sendRequest(this.bindingRequest, this.serverAddress, this.clientAccessPoint, this.responseCollector);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     Thread.currentThread(); Thread.sleep(1100L);
/*     */ 
/*     */     
/* 302 */     assertTrue("Not all retransmissions were made for the expected period of time", (this.requestCollector.receivedRequests.size() == 9));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     Thread.currentThread(); Thread.sleep(1600L);
/*     */ 
/*     */     
/* 310 */     assertTrue("A retransmissions of the request was sent, while not supposed to", (this.requestCollector.receivedRequests.size() == 9));
/*     */   }
/*     */   
/*     */   private class PlainRequestCollector
/*     */     implements RequestListener
/*     */   {
/* 316 */     public Vector receivedRequests = new Vector(); private final TransactionSupportTests this$0;
/*     */     
/*     */     public void requestReceived(StunMessageEvent evt) {
/* 319 */       this.receivedRequests.add(evt);
/*     */     }
/*     */     
/*     */     private PlainRequestCollector() {} }
/*     */   
/*     */   private class PlainResponseCollector implements ResponseCollector {
/* 325 */     public Vector receivedResponses = new Vector();
/*     */     private final TransactionSupportTests this$0;
/*     */     
/*     */     public void processResponse(StunMessageEvent responseEvt) {
/* 329 */       this.receivedResponses.add(responseEvt);
/*     */     }
/*     */ 
/*     */     
/*     */     public void processTimeout() {
/* 334 */       this.receivedResponses.add(new String("timeout"));
/*     */     }
/*     */     
/*     */     private PlainResponseCollector() {}
/*     */   }
/*     */ }
