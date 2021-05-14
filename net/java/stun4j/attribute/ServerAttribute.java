/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.java.stun4j.StunException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerAttribute
/*     */   extends Attribute
/*     */ {
/*  27 */   private byte[] server = null;
/*     */ 
/*     */   
/*     */   protected ServerAttribute() {
/*  31 */     super('耢');
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
/*     */   void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws StunException {
/*  48 */     this.server = new byte[length];
/*  49 */     System.arraycopy(attributeValue, offset, this.server, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/*  59 */     char type = getAttributeType();
/*  60 */     byte[] binValue = new byte[4 + getDataLength()];
/*     */ 
/*     */     
/*  63 */     binValue[0] = (byte)(type >> 8);
/*  64 */     binValue[1] = (byte)(type & 0xFF);
/*     */ 
/*     */     
/*  67 */     binValue[2] = (byte)(getDataLength() >> 8);
/*  68 */     binValue[3] = (byte)(getDataLength() & 0xFF);
/*     */ 
/*     */     
/*  71 */     System.arraycopy(this.server, 0, binValue, 4, getDataLength());
/*     */     
/*  73 */     return binValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getDataLength() {
/*  83 */     return (char)this.server.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  93 */     return "SERVER";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getServer() {
/* 103 */     if (this.server == null) {
/* 104 */       return null;
/*     */     }
/* 106 */     byte[] copy = new byte[this.server.length];
/* 107 */     System.arraycopy(this.server, 0, copy, 0, this.server.length);
/* 108 */     return this.server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServer(byte[] server) {
/* 119 */     if (server == null) {
/* 120 */       this.server = null;
/*     */     }
/* 122 */     this.server = new byte[server.length];
/* 123 */     System.arraycopy(server, 0, this.server, 0, server.length);
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
/* 136 */     if (!(obj instanceof ServerAttribute) || obj == null)
/*     */     {
/* 138 */       return false;
/*     */     }
/* 140 */     if (obj == this) {
/* 141 */       return true;
/*     */     }
/* 143 */     ServerAttribute att = (ServerAttribute)obj;
/* 144 */     if (att.getAttributeType() != getAttributeType() || att.getDataLength() != getDataLength() || !Arrays.equals(att.server, this.server))
/*     */     {
/*     */       
/* 147 */       return false;
/*     */     }
/* 149 */     return true;
/*     */   }
/*     */ }

x