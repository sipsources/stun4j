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
/*     */ 
/*     */ 
/*     */ public class OptionalAttribute
/*     */   extends Attribute
/*     */ {
/*  29 */   byte[] attributeValue = null;
/*     */ 
/*     */   
/*     */   protected OptionalAttribute(char attributeType) {
/*  33 */     super(attributeType);
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
/*     */   void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws StunException {
/*  49 */     this.attributeValue = new byte[length];
/*  50 */     System.arraycopy(attributeValue, offset, this.attributeValue, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/*  60 */     char type = getAttributeType();
/*     */     
/*  62 */     byte[] binValue = new byte[4 + this.attributeValue.length];
/*     */ 
/*     */     
/*  65 */     binValue[0] = (byte)(type >> 8);
/*  66 */     binValue[1] = (byte)(type & 0xFF);
/*     */     
/*  68 */     binValue[2] = (byte)(getDataLength() >> 8);
/*  69 */     binValue[3] = (byte)(getDataLength() & 0xFF);
/*     */     
/*  71 */     System.arraycopy(this.attributeValue, 0, binValue, 4, this.attributeValue.length);
/*     */ 
/*     */     
/*  74 */     return binValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getDataLength() {
/*  84 */     return (char)this.attributeValue.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  94 */     return "Unknown Attribute";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBody() {
/* 104 */     return this.attributeValue;
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
/*     */   public void setBody(byte[] body, int offset, int length) {
/* 116 */     this.attributeValue = new byte[length];
/* 117 */     System.arraycopy(body, offset, this.attributeValue, 0, length);
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
/* 130 */     if (obj == null || !(obj instanceof OptionalAttribute))
/*     */     {
/* 132 */       return false;
/*     */     }
/* 134 */     return (obj == this || Arrays.equals(((OptionalAttribute)obj).attributeValue, this.attributeValue));
/*     */   }
/*     */ }

