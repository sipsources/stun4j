/*     */ package net.java.stun4j.client;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.StunMessageEvent;
/*     */ import net.java.stun4j.attribute.ChangeRequestAttribute;
/*     */ import net.java.stun4j.attribute.ChangedAddressAttribute;
/*     */ import net.java.stun4j.attribute.MappedAddressAttribute;
/*     */ import net.java.stun4j.message.MessageFactory;
/*     */ import net.java.stun4j.message.Request;
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
/*     */ 
/*     */ 
/*     */ public class NetworkConfigurationDiscoveryProcess
/*     */ {
/*  86 */   private static final Logger logger = Logger.getLogger(NetworkConfigurationDiscoveryProcess.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean started = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   private StunStack stunStack = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   private StunProvider stunProvider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private NetAccessPointDescriptor apDescriptor = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private StunAddress serverAddress = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private BlockingRequestSender requestSender = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetworkConfigurationDiscoveryProcess(StunAddress localAddress, StunAddress serverAddress) {
/* 129 */     this.apDescriptor = new NetAccessPointDescriptor(localAddress);
/* 130 */     this.serverAddress = serverAddress;
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
/*     */   public NetworkConfigurationDiscoveryProcess(NetAccessPointDescriptor apDescriptor, StunAddress serverAddress) {
/* 142 */     this.apDescriptor = apDescriptor;
/* 143 */     this.serverAddress = serverAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutDown() {
/* 153 */     StunStack.shutDown();
/* 154 */     this.stunStack = null;
/* 155 */     this.stunProvider = null;
/* 156 */     this.apDescriptor = null;
/* 157 */     this.requestSender = null;
/*     */     
/* 159 */     this.started = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() throws StunException {
/* 170 */     this.stunStack = StunStack.getInstance();
/* 171 */     this.stunStack.start();
/*     */     
/* 173 */     this.stunStack.installNetAccessPoint(this.apDescriptor);
/*     */     
/* 175 */     this.stunProvider = this.stunStack.getProvider();
/*     */     
/* 177 */     this.requestSender = new BlockingRequestSender(this.stunProvider, this.apDescriptor);
/*     */     
/* 179 */     this.started = true;
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
/*     */   public StunDiscoveryReport determineAddress() throws StunException {
/* 193 */     checkStarted();
/* 194 */     StunDiscoveryReport report = new StunDiscoveryReport();
/* 195 */     StunMessageEvent evt = doTestI(this.serverAddress);
/*     */     
/* 197 */     if (evt == null) {
/*     */ 
/*     */       
/* 200 */       report.setNatType("UDP Blocking Firewall");
/* 201 */       return report;
/*     */     } 
/*     */ 
/*     */     
/* 205 */     StunAddress mappedAddress = ((MappedAddressAttribute)evt.getMessage().getAttribute('\001')).getAddress();
/*     */ 
/*     */     
/* 208 */     logger.info("mapped address is=" + mappedAddress + ", name=" + mappedAddress.getHostName());
/*     */ 
/*     */     
/* 211 */     StunAddress backupServerAddress = ((ChangedAddressAttribute)evt.getMessage().getAttribute('\005')).getAddress();
/*     */ 
/*     */     
/* 214 */     logger.info("backup server address is=" + backupServerAddress + ", name=" + backupServerAddress.getHostName());
/*     */ 
/*     */     
/* 217 */     report.setPublicAddress(mappedAddress);
/* 218 */     if (mappedAddress.equals(this.apDescriptor.getAddress())) {
/*     */       
/* 220 */       evt = doTestII(this.serverAddress);
/* 221 */       if (evt == null) {
/*     */ 
/*     */         
/* 224 */         report.setNatType("Symmetric UDP Firewall");
/* 225 */         return report;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 230 */       report.setNatType("Open Internet Configuration");
/* 231 */       return report;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     evt = doTestII(this.serverAddress);
/* 238 */     if (evt == null) {
/*     */       
/* 240 */       evt = doTestI(backupServerAddress);
/* 241 */       if (evt == null) {
/*     */         
/* 243 */         logger.info("Failed to receive a response from backup stun server!");
/* 244 */         return report;
/*     */       } 
/* 246 */       StunAddress mappedAddress2 = ((MappedAddressAttribute)evt.getMessage().getAttribute('\001')).getAddress();
/*     */ 
/*     */       
/* 249 */       if (mappedAddress.equals(mappedAddress2)) {
/*     */         
/* 251 */         evt = doTestIII(this.serverAddress);
/* 252 */         if (evt == null) {
/*     */ 
/*     */           
/* 255 */           report.setNatType("Port Restricted Cone NAT");
/* 256 */           return report;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 261 */         report.setNatType("Restricted Cone NAT");
/* 262 */         return report;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       report.setNatType("Symmetric NAT");
/* 270 */       return report;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     report.setNatType("Full Cone NAT");
/* 277 */     return report;
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
/*     */   private StunMessageEvent doTestI(StunAddress serverAddress) throws StunException {
/* 295 */     Request request = MessageFactory.createBindingRequest();
/*     */     
/* 297 */     ChangeRequestAttribute changeRequest = (ChangeRequestAttribute)request.getAttribute('\003');
/* 298 */     changeRequest.setChangeIpFlag(false);
/* 299 */     changeRequest.setChangePortFlag(false);
/* 300 */     StunMessageEvent evt = this.requestSender.sendRequestAndWaitForResponse(request, serverAddress);
/*     */     
/* 302 */     if (evt != null) {
/* 303 */       logger.info("TEST I res=" + evt.getRemoteAddress().toString() + " - " + evt.getRemoteAddress().getHostName());
/*     */     } else {
/*     */       
/* 306 */       logger.info("NO RESPONSE received to TEST I.");
/* 307 */     }  return evt;
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
/*     */   private StunMessageEvent doTestII(StunAddress serverAddress) throws StunException {
/* 321 */     Request request = MessageFactory.createBindingRequest();
/*     */     
/* 323 */     ChangeRequestAttribute changeRequest = (ChangeRequestAttribute)request.getAttribute('\003');
/* 324 */     changeRequest.setChangeIpFlag(true);
/* 325 */     changeRequest.setChangePortFlag(true);
/*     */     
/* 327 */     StunMessageEvent evt = this.requestSender.sendRequestAndWaitForResponse(request, serverAddress);
/*     */     
/* 329 */     if (evt != null) {
/* 330 */       logger.info("Test II res=" + evt.getRemoteAddress().toString() + " - " + evt.getRemoteAddress().getHostName());
/*     */     } else {
/*     */       
/* 333 */       logger.info("NO RESPONSE received to Test II.");
/*     */     } 
/* 335 */     return evt;
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
/*     */   private StunMessageEvent doTestIII(StunAddress serverAddress) throws StunException {
/* 349 */     Request request = MessageFactory.createBindingRequest();
/*     */     
/* 351 */     ChangeRequestAttribute changeRequest = (ChangeRequestAttribute)request.getAttribute('\003');
/* 352 */     changeRequest.setChangeIpFlag(false);
/* 353 */     changeRequest.setChangePortFlag(true);
/*     */     
/* 355 */     StunMessageEvent evt = this.requestSender.sendRequestAndWaitForResponse(request, serverAddress);
/*     */     
/* 357 */     if (evt != null) {
/* 358 */       logger.info("Test III res=" + evt.getRemoteAddress().toString() + " - " + evt.getRemoteAddress().getHostName());
/*     */     } else {
/*     */       
/* 361 */       logger.info("NO RESPONSE received to Test III.");
/*     */     } 
/* 363 */     return evt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkStarted() throws StunException {
/* 374 */     if (!this.started) {
/* 375 */       throw new StunException(1, "The Discoverer must be started before launching the discovery process!");
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
/*     */   public static void main(String[] args) throws Exception {
/* 391 */     StunAddress localAddr = null;
/* 392 */     StunAddress serverAddr = null;
/* 393 */     if (args.length == 4) {
/*     */       
/* 395 */       localAddr = new StunAddress(args[2], Integer.valueOf(args[3]).intValue());
/* 396 */       serverAddr = new StunAddress(args[0], Integer.valueOf(args[1]).intValue());
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 401 */       localAddr = new StunAddress(InetAddress.getLocalHost(), 5678);
/* 402 */       serverAddr = new StunAddress("stun01bak.sipphone.com.", 3479);
/*     */     } 
/* 404 */     NetworkConfigurationDiscoveryProcess addressDiscovery = new NetworkConfigurationDiscoveryProcess(localAddr, serverAddr);
/*     */ 
/*     */     
/* 407 */     addressDiscovery.start();
/* 408 */     StunDiscoveryReport report = addressDiscovery.determineAddress();
/* 409 */     System.out.println(report);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 415 */     System.exit(0);
/*     */   }
/*     */ }

