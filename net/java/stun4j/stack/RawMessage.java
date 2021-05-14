/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RawMessage
/*     */ {
/*  28 */   private byte[] messageBytes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private int messageLength = -1;
/*     */ 
/*     */ 
/*     */   
/*  37 */   private InetSocketAddress remoteAddress = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private NetAccessPointDescriptor receivingAccessPoint = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RawMessage(byte[] messageBytes, int messageLength, InetAddress remoteAddress, int remotePort, NetAccessPointDescriptor netApDescriptor) {
/*  64 */     this.messageBytes = new byte[messageBytes.length];
/*  65 */     this.messageLength = messageLength;
/*  66 */     System.arraycopy(messageBytes, 0, this.messageBytes, 0, messageBytes.length);
/*     */ 
/*     */     
/*  69 */     this.remoteAddress = new InetSocketAddress(remoteAddress, remotePort);
/*  70 */     this.receivingAccessPoint = (NetAccessPointDescriptor)netApDescriptor.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getBytes() {
/*  81 */     return this.messageBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMessageLength() {
/*  91 */     return this.messageLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InetSocketAddress getRemoteAddress() {
/* 101 */     return this.remoteAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NetAccessPointDescriptor getNetAccessPoint() {
/* 110 */     return this.receivingAccessPoint;
/*     */   }
/*     */ }
