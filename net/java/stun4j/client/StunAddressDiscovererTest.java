/*     */ package net.java.stun4j.client;
/*     */ 
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.message.MessageFactory;
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
/*     */ public class StunAddressDiscovererTest
/*     */   extends TestCase
/*     */ {
/*  25 */   private NetworkConfigurationDiscoveryProcess stunAddressDiscoverer = null;
/*  26 */   private StunAddress discovererAddress = new StunAddress("127.0.0.1", 15555);
/*     */   
/*  28 */   private ResponseSequenceServer responseServer = null;
/*  29 */   private StunAddress responseServerAddress = new StunAddress("127.0.0.1", 19999);
/*     */ 
/*     */   
/*  32 */   private StunAddress mappedClientAddress = new StunAddress("212.56.4.10", 15612);
/*  33 */   private StunAddress mappedClientAddressPort2 = new StunAddress("212.56.4.10", 15611);
/*     */ 
/*     */ 
/*     */   
/*     */   public StunAddressDiscovererTest(String name) throws StunException {
/*  38 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  42 */     super.setUp();
/*  43 */     this.responseServer = new ResponseSequenceServer(this.responseServerAddress);
/*  44 */     this.stunAddressDiscoverer = new NetworkConfigurationDiscoveryProcess(this.discovererAddress, this.responseServerAddress);
/*     */     
/*  46 */     this.stunAddressDiscoverer.start();
/*  47 */     this.responseServer.start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  52 */     this.responseServer.shutDown();
/*  53 */     this.stunAddressDiscoverer.shutDown();
/*  54 */     this.stunAddressDiscoverer = null;
/*     */     
/*  56 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testRecognizeBlockedUDP() throws StunException {
/*  66 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/*  68 */     expectedReturn.setNatType("UDP Blocking Firewall");
/*  69 */     expectedReturn.setPublicAddress(null);
/*     */     
/*  71 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*  72 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeSymmetricNat() throws StunException {
/*  86 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/*  88 */     Response testIResponse2 = null;
/*  89 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddressPort2, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/*  92 */     this.responseServer.addMessage(testIResponse1);
/*  93 */     this.responseServer.addMessage(testIResponse2);
/*  94 */     this.responseServer.addMessage(testIResponse3);
/*     */ 
/*     */     
/*  97 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/*  99 */     expectedReturn.setNatType("Symmetric NAT");
/* 100 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 102 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/* 103 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizePortRestrictedCone() throws StunException {
/* 116 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 118 */     Response testIResponse2 = null;
/* 119 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 121 */     Response testIResponse4 = null;
/*     */     
/* 123 */     this.responseServer.addMessage(testIResponse1);
/* 124 */     this.responseServer.addMessage(testIResponse2);
/* 125 */     this.responseServer.addMessage(testIResponse3);
/* 126 */     this.responseServer.addMessage(testIResponse4);
/*     */ 
/*     */     
/* 129 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 131 */     expectedReturn.setNatType("Port Restricted Cone NAT");
/* 132 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 134 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/* 135 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeRestrictedCone() throws StunException {
/* 148 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 150 */     Response testIResponse2 = null;
/* 151 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 153 */     Response testIResponse4 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/* 156 */     this.responseServer.addMessage(testIResponse1);
/* 157 */     this.responseServer.addMessage(testIResponse2);
/* 158 */     this.responseServer.addMessage(testIResponse3);
/* 159 */     this.responseServer.addMessage(testIResponse4);
/*     */     
/* 161 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 163 */     expectedReturn.setNatType("Restricted Cone NAT");
/* 164 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 166 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 168 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeFullCone() throws StunException {
/* 182 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 184 */     Response testIResponse2 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/* 187 */     this.responseServer.addMessage(testIResponse1);
/* 188 */     this.responseServer.addMessage(testIResponse2);
/*     */     
/* 190 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 192 */     expectedReturn.setNatType("Full Cone NAT");
/* 193 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 195 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 197 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeUdpSymmetricFirewall() throws StunException {
/* 211 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.discovererAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 213 */     Response testIResponse2 = null;
/*     */     
/* 215 */     this.responseServer.addMessage(testIResponse1);
/* 216 */     this.responseServer.addMessage(testIResponse2);
/*     */     
/* 218 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 220 */     expectedReturn.setNatType("Symmetric UDP Firewall");
/* 221 */     expectedReturn.setPublicAddress(this.discovererAddress);
/*     */     
/* 223 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 225 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeOpenInternet() throws StunException {
/* 239 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.discovererAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 241 */     Response testIResponse2 = MessageFactory.createBindingResponse(this.discovererAddress, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/* 244 */     this.responseServer.addMessage(testIResponse1);
/* 245 */     this.responseServer.addMessage(testIResponse2);
/*     */     
/* 247 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 249 */     expectedReturn.setNatType("Open Internet Configuration");
/* 250 */     expectedReturn.setPublicAddress(this.discovererAddress);
/*     */     
/* 252 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 254 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
/*     */   }
/*     */ }

