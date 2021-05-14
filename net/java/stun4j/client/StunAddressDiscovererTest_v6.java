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
/*     */ public class StunAddressDiscovererTest_v6
/*     */   extends TestCase
/*     */ {
/*  25 */   private NetworkConfigurationDiscoveryProcess stunAddressDiscoverer = null;
/*  26 */   private StunAddress discovererAddress = new StunAddress("::1", 16555);
/*     */   
/*  28 */   private ResponseSequenceServer responseServer = null;
/*  29 */   private StunAddress responseServerAddress = new StunAddress("::1", 20999);
/*     */   
/*  31 */   private StunAddress mappedClientAddress = new StunAddress("2001:660:4701:1001:ff::1", 16612);
/*  32 */   private StunAddress mappedClientAddressPort2 = new StunAddress("2001:660:4701:1001:ff::1", 16611);
/*     */ 
/*     */ 
/*     */   
/*     */   public StunAddressDiscovererTest_v6(String name) throws StunException {
/*  37 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  41 */     super.setUp();
/*  42 */     this.responseServer = new ResponseSequenceServer(this.responseServerAddress);
/*  43 */     this.stunAddressDiscoverer = new NetworkConfigurationDiscoveryProcess(this.discovererAddress, this.responseServerAddress);
/*     */     
/*  45 */     this.stunAddressDiscoverer.start();
/*  46 */     this.responseServer.start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  51 */     this.responseServer.shutDown();
/*  52 */     this.stunAddressDiscoverer.shutDown();
/*  53 */     this.stunAddressDiscoverer = null;
/*     */     
/*  55 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testRecognizeBlockedUDP() throws StunException {
/*  65 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/*  67 */     expectedReturn.setNatType("UDP Blocking Firewall");
/*  68 */     expectedReturn.setPublicAddress(null);
/*     */     
/*  70 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*  71 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*  85 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/*  87 */     Response testIResponse2 = null;
/*  88 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddressPort2, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/*  91 */     this.responseServer.addMessage(testIResponse1);
/*  92 */     this.responseServer.addMessage(testIResponse2);
/*  93 */     this.responseServer.addMessage(testIResponse3);
/*     */ 
/*     */     
/*  96 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/*  98 */     expectedReturn.setNatType("Symmetric NAT");
/*  99 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 101 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/* 102 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/* 115 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 117 */     Response testIResponse2 = null;
/* 118 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 120 */     Response testIResponse4 = null;
/*     */     
/* 122 */     this.responseServer.addMessage(testIResponse1);
/* 123 */     this.responseServer.addMessage(testIResponse2);
/* 124 */     this.responseServer.addMessage(testIResponse3);
/* 125 */     this.responseServer.addMessage(testIResponse4);
/*     */ 
/*     */     
/* 128 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 130 */     expectedReturn.setNatType("Port Restricted Cone NAT");
/* 131 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 133 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/* 134 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/* 147 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 149 */     Response testIResponse2 = null;
/* 150 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 152 */     Response testIResponse4 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/* 155 */     this.responseServer.addMessage(testIResponse1);
/* 156 */     this.responseServer.addMessage(testIResponse2);
/* 157 */     this.responseServer.addMessage(testIResponse3);
/* 158 */     this.responseServer.addMessage(testIResponse4);
/*     */     
/* 160 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 162 */     expectedReturn.setNatType("Restricted Cone NAT");
/* 163 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 165 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 167 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/* 181 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 183 */     Response testIResponse2 = MessageFactory.createBindingResponse(this.mappedClientAddress, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/* 186 */     this.responseServer.addMessage(testIResponse1);
/* 187 */     this.responseServer.addMessage(testIResponse2);
/*     */     
/* 189 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 191 */     expectedReturn.setNatType("Full Cone NAT");
/* 192 */     expectedReturn.setPublicAddress(this.mappedClientAddress);
/*     */     
/* 194 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 196 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/* 210 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.discovererAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 212 */     Response testIResponse2 = null;
/*     */     
/* 214 */     this.responseServer.addMessage(testIResponse1);
/* 215 */     this.responseServer.addMessage(testIResponse2);
/*     */     
/* 217 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 219 */     expectedReturn.setNatType("Symmetric UDP Firewall");
/* 220 */     expectedReturn.setPublicAddress(this.discovererAddress);
/*     */     
/* 222 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 224 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/* 238 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.discovererAddress, this.responseServerAddress, this.responseServerAddress);
/*     */     
/* 240 */     Response testIResponse2 = MessageFactory.createBindingResponse(this.discovererAddress, this.responseServerAddress, this.responseServerAddress);
/*     */ 
/*     */     
/* 243 */     this.responseServer.addMessage(testIResponse1);
/* 244 */     this.responseServer.addMessage(testIResponse2);
/*     */     
/* 246 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 248 */     expectedReturn.setNatType("Open Internet Configuration");
/* 249 */     expectedReturn.setPublicAddress(this.discovererAddress);
/*     */     
/* 251 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer.determineAddress();
/*     */     
/* 253 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
/*     */   }
/*     */ }

