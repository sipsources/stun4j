/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnknownAttributesAttribute
/*     */   extends Attribute
/*     */ {
/*  44 */   private ArrayList unknownAttributes = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   UnknownAttributesAttribute() {
/*  50 */     super('\n');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  61 */     return NAME;
/*     */   }
/*     */   
/*  64 */   public static String NAME = "UNKNOWN-ATTRIBUTES";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getDataLength() {
/*  75 */     char len = (char)this.unknownAttributes.size();
/*     */     
/*  77 */     if (len % 2 != 0) {
/*  78 */       len = (char)(len + 1);
/*     */     }
/*  80 */     return (char)(len * 2);
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
/*     */   public void addAttributeID(char attributeID) {
/*  92 */     if (!contains(attributeID)) {
/*  93 */       this.unknownAttributes.add(new Character(attributeID));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(char attributeID) {
/* 103 */     return this.unknownAttributes.contains(new Character(attributeID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getAttributes() {
/* 114 */     return this.unknownAttributes.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeCount() {
/* 124 */     return this.unknownAttributes.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getAttribute(int index) {
/* 134 */     return ((Character)this.unknownAttributes.get(index)).charValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/* 143 */     byte[] binValue = new byte[getDataLength() + 4];
/* 144 */     int offset = 0;
/*     */ 
/*     */     
/* 147 */     binValue[offset++] = (byte)(getAttributeType() >> 8);
/* 148 */     binValue[offset++] = (byte)(getAttributeType() & 0xFF);
/*     */ 
/*     */     
/* 151 */     binValue[offset++] = (byte)(getDataLength() >> 8);
/* 152 */     binValue[offset++] = (byte)(getDataLength() & 0xFF);
/*     */ 
/*     */     
/* 155 */     Iterator attributes = getAttributes();
/* 156 */     while (attributes.hasNext()) {
/* 157 */       char att = ((Character)attributes.next()).charValue();
/* 158 */       binValue[offset++] = (byte)(att >> 8);
/* 159 */       binValue[offset++] = (byte)(att & 0xFF);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     if (offset < binValue.length) {
/*     */       
/* 167 */       char att = getAttribute(0);
/* 168 */       binValue[offset++] = (byte)(att >> 8);
/* 169 */       binValue[offset++] = (byte)(att & 0xFF);
/*     */     } 
/*     */ 
/*     */     
/* 173 */     return binValue;
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
/*     */   public boolean equals(Object obj) {
/* 185 */     if (!(obj instanceof UnknownAttributesAttribute) || obj == null)
/*     */     {
/* 187 */       return false;
/*     */     }
/* 189 */     if (obj == this) {
/* 190 */       return true;
/*     */     }
/* 192 */     UnknownAttributesAttribute att = (UnknownAttributesAttribute)obj;
/* 193 */     if (att.getAttributeType() != getAttributeType() || att.getDataLength() != getDataLength() || !this.unknownAttributes.equals(att.unknownAttributes))
/*     */     {
/*     */ 
/*     */       
/* 197 */       return false;
/*     */     }
/* 199 */     return true;
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
/*     */   void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws StunException {
/* 218 */     if (length % 2 != 0) {
/* 219 */       throw new StunException("Attribute IDs are 2 bytes long and the passed binary array has an odd length value.");
/*     */     }
/* 221 */     char originalOffset = offset;
/* 222 */     for (int i = offset; i < originalOffset + length; i += 2) {
/*     */       
/* 224 */       offset = (char)(offset + 1); offset = (char)(offset + 1); char attributeID = (char)(attributeValue[offset] << 8 | attributeValue[offset]);
/*     */       
/* 226 */       addAttributeID(attributeID);
/*     */     } 
/*     */   }
/*     */ }

