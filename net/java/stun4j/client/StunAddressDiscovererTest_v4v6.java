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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StunAddressDiscovererTest_v4v6
/*     */   extends TestCase
/*     */ {
/*  30 */   private NetworkConfigurationDiscoveryProcess stunAddressDiscoverer_v6 = null;
/*  31 */   private NetworkConfigurationDiscoveryProcess stunAddressDiscoverer_v4 = null;
/*     */   
/*  33 */   private StunAddress discovererAddress_v4 = new StunAddress("127.0.0.1", 17555);
/*  34 */   private StunAddress discovererAddress_v6 = new StunAddress("::1", 17555);
/*     */   
/*  36 */   private ResponseSequenceServer responseServer_v6 = null;
/*  37 */   private ResponseSequenceServer responseServer_v4 = null;
/*     */   
/*  39 */   private StunAddress responseServerAddress_v6 = new StunAddress("::1", 21999);
/*  40 */   private StunAddress responseServerAddress_v4 = new StunAddress("127.0.0.1", 21999);
/*     */   
/*  42 */   private StunAddress mappedClientAddress_v6 = new StunAddress("2001:660:4701:1001:ff::1", 17612);
/*  43 */   private StunAddress mappedClientAddress_v6_Port2 = new StunAddress("2001:660:4701:1001:ff::1", 17611);
/*     */   
/*  45 */   private StunAddress mappedClientAddress_v4 = new StunAddress("130.79.99.55", 17612);
/*  46 */   private StunAddress mappedClientAddress_v4_Port2 = new StunAddress("130.79.99.55", 17611);
/*     */ 
/*     */ 
/*     */   
/*     */   public StunAddressDiscovererTest_v4v6(String name) throws StunException {
/*  51 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  55 */     super.setUp();
/*  56 */     this.responseServer_v6 = new ResponseSequenceServer(this.responseServerAddress_v6);
/*  57 */     this.responseServer_v4 = new ResponseSequenceServer(this.responseServerAddress_v4);
/*  58 */     this.stunAddressDiscoverer_v6 = new NetworkConfigurationDiscoveryProcess(this.discovererAddress_v6, this.responseServerAddress_v6);
/*  59 */     this.stunAddressDiscoverer_v4 = new NetworkConfigurationDiscoveryProcess(this.discovererAddress_v4, this.responseServerAddress_v4);
/*     */     
/*  61 */     this.stunAddressDiscoverer_v6.start();
/*  62 */     this.stunAddressDiscoverer_v4.start();
/*  63 */     this.responseServer_v6.start();
/*  64 */     this.responseServer_v4.start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  69 */     this.responseServer_v6.shutDown();
/*  70 */     this.responseServer_v4.shutDown();
/*  71 */     this.stunAddressDiscoverer_v6.shutDown();
/*  72 */     this.stunAddressDiscoverer_v6 = null;
/*  73 */     this.stunAddressDiscoverer_v4.shutDown();
/*  74 */     this.stunAddressDiscoverer_v4 = null;
/*     */ 
/*     */     
/*  77 */     Thread.currentThread(); Thread.sleep(1000L);
/*     */     
/*  79 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testRecognizeSymmetricNat_Local_v6_Public_v4() throws StunException {
/*  90 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress_v4, this.responseServerAddress_v6, this.responseServerAddress_v6);
/*     */     
/*  92 */     Response testIResponse2 = null;
/*  93 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddress_v4_Port2, this.responseServerAddress_v6, this.responseServerAddress_v6);
/*     */ 
/*     */     
/*  96 */     this.responseServer_v6.addMessage(testIResponse1);
/*  97 */     this.responseServer_v6.addMessage(testIResponse2);
/*  98 */     this.responseServer_v6.addMessage(testIResponse3);
/*     */ 
/*     */     
/* 101 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 103 */     expectedReturn.setNatType("Symmetric NAT");
/* 104 */     expectedReturn.setPublicAddress(this.mappedClientAddress_v4);
/*     */     
/* 106 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer_v6.determineAddress();
/* 107 */     assertEquals("The StunAddressDiscoverer failed for a v4-v6 sym env.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeSymmetricNat_Local_v4_Public_v6() throws StunException {
/* 120 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress_v6, this.responseServerAddress_v4, this.responseServerAddress_v4);
/*     */     
/* 122 */     Response testIResponse2 = null;
/* 123 */     Response testIResponse3 = MessageFactory.createBindingResponse(this.mappedClientAddress_v6_Port2, this.responseServerAddress_v4, this.responseServerAddress_v4);
/*     */ 
/*     */     
/* 126 */     this.responseServer_v4.addMessage(testIResponse1);
/* 127 */     this.responseServer_v4.addMessage(testIResponse2);
/* 128 */     this.responseServer_v4.addMessage(testIResponse3);
/*     */ 
/*     */     
/* 131 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 133 */     expectedReturn.setNatType("Symmetric NAT");
/* 134 */     expectedReturn.setPublicAddress(this.mappedClientAddress_v6);
/*     */     
/* 136 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer_v4.determineAddress();
/* 137 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeFullCone_Local_v6_Public_v4() throws StunException {
/* 150 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress_v4, this.responseServerAddress_v6, this.responseServerAddress_v6);
/*     */     
/* 152 */     Response testIResponse2 = MessageFactory.createBindingResponse(this.mappedClientAddress_v4, this.responseServerAddress_v6, this.responseServerAddress_v6);
/*     */ 
/*     */     
/* 155 */     this.responseServer_v6.addMessage(testIResponse1);
/* 156 */     this.responseServer_v6.addMessage(testIResponse2);
/*     */     
/* 158 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 160 */     expectedReturn.setNatType("Full Cone NAT");
/* 161 */     expectedReturn.setPublicAddress(this.mappedClientAddress_v4);
/*     */     
/* 163 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer_v6.determineAddress();
/*     */     
/* 165 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
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
/*     */   public void testRecognizeFullCone_Local_v4_Public_v6() throws StunException {
/* 179 */     Response testIResponse1 = MessageFactory.createBindingResponse(this.mappedClientAddress_v6, this.responseServerAddress_v4, this.responseServerAddress_v4);
/*     */     
/* 181 */     Response testIResponse2 = MessageFactory.createBindingResponse(this.mappedClientAddress_v6, this.responseServerAddress_v4, this.responseServerAddress_v4);
/*     */ 
/*     */     
/* 184 */     this.responseServer_v4.addMessage(testIResponse1);
/* 185 */     this.responseServer_v4.addMessage(testIResponse2);
/*     */     
/* 187 */     StunDiscoveryReport expectedReturn = new StunDiscoveryReport();
/*     */     
/* 189 */     expectedReturn.setNatType("Full Cone NAT");
/* 190 */     expectedReturn.setPublicAddress(this.mappedClientAddress_v6);
/*     */     
/* 192 */     StunDiscoveryReport actualReturn = this.stunAddressDiscoverer_v4.determineAddress();
/*     */     
/* 194 */     assertEquals("The StunAddressDiscoverer failed for a no-udp environment.", expectedReturn, actualReturn);
/*     */   }
/*     */ }


