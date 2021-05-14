/*     */ package net.java.stun4j.attribute;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XorOnlyAttribute
/*     */   extends Attribute
/*     */ {
/*     */   protected XorOnlyAttribute() {
/*  31 */     super('!');
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
/*     */   void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws StunException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/*  57 */     char type = getAttributeType();
/*  58 */     byte[] binValue = new byte[4 + getDataLength()];
/*     */ 
/*     */     
/*  61 */     binValue[0] = (byte)(type >> 8);
/*  62 */     binValue[1] = (byte)(type & 0xFF);
/*     */ 
/*     */     
/*  65 */     binValue[2] = (byte)(getDataLength() >> 8);
/*  66 */     binValue[3] = (byte)(getDataLength() & 0xFF);
/*     */     
/*  68 */     return binValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getDataLength() {
/*  79 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  89 */     return "XOR-ONLY";
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
/* 102 */     if (!(obj instanceof XorOnlyAttribute) || obj == null)
/*     */     {
/* 104 */       return false;
/*     */     }
/* 106 */     if (obj == this) {
/* 107 */       return true;
/*     */     }
/* 109 */     XorOnlyAttribute att = (XorOnlyAttribute)obj;
/* 110 */     if (att.getAttributeType() != getAttributeType() || att.getDataLength() != getDataLength())
/*     */     {
/* 112 */       return false;
/*     */     }
/* 114 */     return true;
/*     */   }
/*     */ }

