/*     */ package net.java.stun4j;
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
/*     */ public class NetAccessPointDescriptor
/*     */ {
/*  30 */   protected StunAddress stunAddr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetAccessPointDescriptor(StunAddress address) {
/*  40 */     this.stunAddr = address;
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
/*     */   public final boolean equals(Object obj) {
/*  69 */     if (obj == null || !(obj instanceof NetAccessPointDescriptor))
/*     */     {
/*  71 */       return false;
/*     */     }
/*  73 */     if (obj == this) {
/*  74 */       return true;
/*     */     }
/*  76 */     return this.stunAddr.equals(((NetAccessPointDescriptor)obj).stunAddr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StunAddress getAddress() {
/*  85 */     return this.stunAddr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  95 */     NetAccessPointDescriptor napd = new NetAccessPointDescriptor(this.stunAddr);
/*     */     
/*  97 */     return napd;
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
/*     */   public int hashCode() {
/* 110 */     return this.stunAddr.getSocketAddress().hashCode();
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
/* 121 */     return "StunAddress=" + ((this.stunAddr == null) ? "null" : this.stunAddr.toString());
/*     */   }
/*     */ }
