/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Logger;
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.MsgFixture;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.ResponseCollector;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.message.MessageFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShallowStackTest
/*     */   extends TestCase
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(ShallowStackTest.class.getName());
/*     */ 
/*     */   
/*  39 */   private StunProvider stunProvider = null;
/*  40 */   private StunStack stunStack = null;
/*  41 */   private MsgFixture msgFixture = null;
/*     */   
/*  43 */   private StunAddress stun4jAddressOfDummyImpl = null;
/*  44 */   private InetSocketAddress socketAddressOfStun4jStack = null;
/*     */   
/*  46 */   private DatagramCollector dgramCollector = new DatagramCollector();
/*     */   
/*  48 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */   
/*  50 */   private DatagramSocket dummyImplSocket = null;
/*  51 */   private DatagramPacket bindingRequestPacket = new DatagramPacket(new byte[4096], 4096);
/*     */   
/*     */   public ShallowStackTest(String name) {
/*  54 */     super(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUp() throws Exception {
/*  60 */     super.setUp();
/*  61 */     this.msgFixture = new MsgFixture();
/*  62 */     this.msgFixture.setUp();
/*     */     
/*  64 */     this.stun4jAddressOfDummyImpl = new StunAddress(InetAddress.getByName("127.0.0.1"), 6000);
/*     */     
/*  66 */     this.socketAddressOfStun4jStack = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 5000);
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.stunStack = StunStack.getInstance();
/*  71 */     this.stunStack.start();
/*     */     
/*  73 */     this.stunProvider = this.stunStack.getProvider();
/*     */ 
/*     */     
/*  76 */     this.apDescriptor = new NetAccessPointDescriptor(new StunAddress(InetAddress.getByName("127.0.0.1"), 5000));
/*     */ 
/*     */     
/*  79 */     this.stunStack.installNetAccessPoint(this.apDescriptor);
/*     */ 
/*     */     
/*  82 */     this.dummyImplSocket = new DatagramSocket(6000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  89 */     this.msgFixture.tearDown();
/*     */     
/*  91 */     StunStack.shutDown();
/*  92 */     this.dummyImplSocket.close();
/*     */     
/*  94 */     this.msgFixture = null;
/*  95 */     super.tearDown();
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
/*     */   public void testSendRequest() throws Exception {
/* 108 */     Request bindingRequest = MessageFactory.createBindingRequest();
/*     */     
/* 110 */     this.dgramCollector.startListening(this.dummyImplSocket);
/*     */     
/* 112 */     this.stunProvider.sendRequest(bindingRequest, this.stun4jAddressOfDummyImpl, this.apDescriptor, new SimpleResponseCollector());
/*     */ 
/*     */ 
/*     */     
/* 116 */     try { Thread.sleep(500L); } catch (InterruptedException ex) {}
/*     */     
/* 118 */     DatagramPacket receivedPacket = this.dgramCollector.collectPacket();
/*     */     
/* 120 */     assertTrue("The stack did not properly send a Binding Request", (receivedPacket.getLength() > 0));
/*     */ 
/*     */     
/* 123 */     Request receivedRequest = (Request)Request.decode(receivedPacket.getData(), false, (char)receivedPacket.getLength());
/*     */ 
/*     */ 
/*     */     
/* 127 */     assertEquals("The received request did not match the one that was sent.", bindingRequest, receivedRequest);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     this.dgramCollector.startListening(this.dummyImplSocket);
/*     */     
/* 135 */     try { Thread.sleep(1000L); } catch (InterruptedException ex) {}
/*     */     
/* 137 */     receivedPacket = this.dgramCollector.collectPacket();
/*     */     
/* 139 */     assertTrue("The stack did not retransmit a Binding Request", (receivedPacket.getLength() > 0));
/*     */ 
/*     */     
/* 142 */     receivedRequest = (Request)Request.decode(receivedPacket.getData(), false, (char)receivedPacket.getLength());
/*     */ 
/*     */ 
/*     */     
/* 146 */     assertEquals("The retransmitted request did not match the original.", bindingRequest, receivedRequest);
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
/*     */   public void testReceiveRequest() throws Exception {
/* 163 */     SimpleRequestCollector requestCollector = new SimpleRequestCollector();
/* 164 */     this.stunProvider.addRequestListener(requestCollector);
/*     */     
/* 166 */     this.dummyImplSocket.send(new DatagramPacket(this.msgFixture.bindingRequest, this.msgFixture.bindingRequest.length, this.socketAddressOfStun4jStack));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     try { Thread.sleep(500L); } catch (InterruptedException ex) {}
/*     */     
/* 174 */     Request collectedRequest = requestCollector.collectedRequest;
/*     */     
/* 176 */     assertNotNull("No request has been received", collectedRequest);
/*     */     
/* 178 */     byte[] expectedReturn = this.msgFixture.bindingRequest;
/* 179 */     byte[] actualReturn = collectedRequest.encode();
/* 180 */     assertTrue("Received request was not the same as the one that was sent", Arrays.equals(expectedReturn, actualReturn));
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
/*     */   public void testSendResponse() throws Exception {
/* 196 */     SimpleRequestCollector requestCollector = new SimpleRequestCollector();
/* 197 */     this.stunProvider.addRequestListener(requestCollector);
/*     */     
/* 199 */     this.dummyImplSocket.send(new DatagramPacket(this.msgFixture.bindingRequest, this.msgFixture.bindingRequest.length, this.socketAddressOfStun4jStack));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     try { Thread.sleep(500L); } catch (InterruptedException ex) {}
/*     */     
/* 207 */     Request collectedRequest = requestCollector.collectedRequest;
/*     */     
/* 209 */     byte[] expectedReturn = this.msgFixture.bindingRequest;
/* 210 */     byte[] actualReturn = collectedRequest.encode();
/* 211 */     assertTrue("Received request was not the same as the one that was sent", Arrays.equals(expectedReturn, actualReturn));
/*     */ 
/*     */ 
/*     */     
/* 215 */     Response bindingResponse = MessageFactory.createBindingResponse(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904), new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_2, 3478), new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_3, 3479));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     this.dgramCollector.startListening(this.dummyImplSocket);
/*     */     
/* 223 */     this.stunProvider.sendResponse(collectedRequest.getTransactionID(), bindingResponse, this.apDescriptor, this.stun4jAddressOfDummyImpl);
/*     */ 
/*     */ 
/*     */     
/* 227 */     try { Thread.sleep(500L); } catch (InterruptedException ex) {}
/*     */     
/* 229 */     DatagramPacket receivedPacket = this.dgramCollector.collectPacket();
/*     */     
/* 231 */     assertTrue("The stack did not properly send a Binding Request", (receivedPacket.getLength() > 0));
/*     */ 
/*     */     
/* 234 */     Response receivedResponse = (Response)Response.decode(receivedPacket.getData(), false, (char)receivedPacket.getLength());
/*     */ 
/*     */ 
/*     */     
/* 238 */     assertEquals("The received request did not match the one that was sent.", bindingResponse, receivedResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testReceiveResponse() throws Exception {
/* 247 */     SimpleResponseCollector collector = new SimpleResponseCollector();
/*     */     
/* 249 */     Request bindingRequest = MessageFactory.createBindingRequest();
/*     */     
/* 251 */     this.stunProvider.sendRequest(bindingRequest, this.stun4jAddressOfDummyImpl, this.apDescriptor, collector);
/*     */ 
/*     */ 
/*     */     
/* 255 */     try { Thread.sleep(500L); } catch (InterruptedException ex) {}
/*     */ 
/*     */     
/* 258 */     byte[] response = new byte[this.msgFixture.bindingResponse.length];
/* 259 */     System.arraycopy(this.msgFixture.bindingResponse, 0, response, 0, response.length);
/*     */ 
/*     */ 
/*     */     
/* 263 */     System.arraycopy(bindingRequest.getTransactionID(), 0, response, 4, 16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     this.dummyImplSocket.send(new DatagramPacket(response, response.length, this.socketAddressOfStun4jStack));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     try { Thread.sleep(500L); } catch (InterruptedException ex) {}
/*     */     
/* 278 */     Response collectedResponse = collector.collectedResponse;
/*     */     
/* 280 */     byte[] expectedReturn = response;
/* 281 */     byte[] actualReturn = collectedResponse.encode();
/* 282 */     assertTrue("Received request was not the same as the one that was sent", Arrays.equals(expectedReturn, actualReturn));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class SimpleResponseCollector
/*     */     implements ResponseCollector
/*     */   {
/* 290 */     Response collectedResponse = null; private final ShallowStackTest this$0;
/*     */     
/*     */     public void processResponse(StunMessageEvent evt) {
/* 293 */       this.collectedResponse = (Response)evt.getMessage();
/* 294 */       ShallowStackTest.logger.info("Received response.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void processTimeout() {
/* 299 */       ShallowStackTest.logger.info("Timeout");
/*     */     }
/*     */   }
/*     */   
/*     */   public class SimpleRequestCollector
/*     */     implements RequestListener
/*     */   {
/* 306 */     Request collectedRequest = null; private final ShallowStackTest this$0;
/*     */     
/*     */     public void requestReceived(StunMessageEvent evt) {
/* 309 */       this.collectedRequest = (Request)evt.getMessage();
/* 310 */       ShallowStackTest.logger.info("Received request.");
/*     */     }
/*     */   }
/*     */ }
