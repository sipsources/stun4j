/*     */ package net.java.stun4j.client;
/*     */ 
/*     */ import net.java.stun4j.StunAddress;
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
/*     */ public class StunDiscoveryReport
/*     */ {
/*     */   public static final String UNKNOWN = "Unknown Network Configuration";
/*     */   public static final String OPEN_INTERNET = "Open Internet Configuration";
/*     */   public static final String UDP_BLOCKING_FIREWALL = "UDP Blocking Firewall";
/*     */   public static final String SYMMETRIC_UDP_FIREWALL = "Symmetric UDP Firewall";
/*     */   public static final String FULL_CONE_NAT = "Full Cone NAT";
/*     */   public static final String SYMMETRIC_NAT = "Symmetric NAT";
/*     */   public static final String RESTRICTED_CONE_NAT = "Restricted Cone NAT";
/*     */   public static final String PORT_RESTRICTED_CONE_NAT = "Port Restricted Cone NAT";
/*  65 */   private String natType = "Unknown Network Configuration";
/*     */ 
/*     */   
/*  68 */   private StunAddress publicAddress = null;
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
/*     */   public String getNatType() {
/*  84 */     return this.natType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNatType(String natType) {
/*  93 */     this.natType = natType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StunAddress getPublicAddress() {
/* 102 */     return this.publicAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPublicAddress(StunAddress stunAddress) {
/* 111 */     this.publicAddress = stunAddress;
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
/*     */   public boolean equals(Object obj) {
/* 124 */     if (obj == null || !(obj instanceof StunDiscoveryReport))
/*     */     {
/* 126 */       return false;
/*     */     }
/* 128 */     if (obj == this) {
/* 129 */       return true;
/*     */     }
/* 131 */     StunDiscoveryReport target = (StunDiscoveryReport)obj;
/*     */     
/* 133 */     return (target.getNatType() == getNatType() && ((getPublicAddress() == null && target.getPublicAddress() == null) || target.getPublicAddress().equals(getPublicAddress())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return "The detected network configuration is: " + getNatType() + "\n" + "Your mapped public address is: " + getPublicAddress();
/*     */   }
/*     */ }


