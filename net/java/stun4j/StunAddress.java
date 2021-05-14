/*     */ package net.java.stun4j;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.UnknownHostException;
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
/*     */ public class StunAddress
/*     */ {
/*  24 */   InetSocketAddress socketAddress = null;
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
/*     */   public StunAddress(String hostname, int port) {
/*  42 */     this.socketAddress = new InetSocketAddress(hostname, port);
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
/*     */   public StunAddress(byte[] ipAddress, int port) {
/*     */     try {
/*  66 */       this.socketAddress = new InetSocketAddress(InetAddress.getByAddress(ipAddress), port);
/*     */     
/*     */     }
/*  69 */     catch (UnknownHostException ex) {
/*     */ 
/*     */       
/*  72 */       this.socketAddress = new InetSocketAddress((ipAddress[0] & 0xFF) + "." + (ipAddress[1] & 0xFF) + "." + (ipAddress[2] & 0xFF) + "." + (ipAddress[3] & 0xFF) + ".", port);
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
/*     */   public StunAddress(InetAddress address, int port) {
/*  98 */     this.socketAddress = new InetSocketAddress(address, port);
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
/*     */   public StunAddress(int port) {
/* 116 */     this.socketAddress = new InetSocketAddress(port);
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
/*     */   public byte[] getAddressBytes() {
/* 128 */     return (this.socketAddress == null) ? null : this.socketAddress.getAddress().getAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getPort() {
/* 139 */     return (this.socketAddress == null) ? Character.MIN_VALUE : (char)this.socketAddress.getPort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetSocketAddress getSocketAddress() {
/* 150 */     return this.socketAddress;
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
/*     */   public String toString() {
/* 163 */     return this.socketAddress.toString();
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
/*     */   public boolean equals(Object obj) {
/* 182 */     if (!(obj instanceof StunAddress)) {
/* 183 */       return false;
/*     */     }
/* 185 */     StunAddress target = (StunAddress)obj;
/* 186 */     if (target.socketAddress == null && this.socketAddress == null)
/*     */     {
/* 188 */       return true;
/*     */     }
/* 190 */     return this.socketAddress.equals(target.getSocketAddress());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostName() {
/* 199 */     return this.socketAddress.getHostName();
/*     */   }
/*     */ }

