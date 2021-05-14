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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChangeRequestAttribute
/*     */   extends Attribute
/*     */ {
/*     */   private boolean changeIpFlag = false;
/*     */   private boolean changePortFlag = false;
/*     */   public static final char DATA_LENGTH = '\004';
/*     */   public static final String NAME = "CHANGE-REQUEST";
/*     */   
/*     */   ChangeRequestAttribute() {
/*  68 */     super('\003');
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
/*  79 */     return "CHANGE-REQUEST";
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
/*     */   public boolean equals(Object obj) {
/*  93 */     if (!(obj instanceof ChangeRequestAttribute) || obj == null)
/*     */     {
/*  95 */       return false;
/*     */     }
/*  97 */     if (obj == this) {
/*  98 */       return true;
/*     */     }
/* 100 */     ChangeRequestAttribute att = (ChangeRequestAttribute)obj;
/* 101 */     if (att.getAttributeType() != getAttributeType() || att.getDataLength() != getDataLength() || att.getChangeIpFlag() != getChangeIpFlag() || att.getChangePortFlag() != getChangePortFlag())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       return false;
/*     */     }
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getDataLength() {
/* 118 */     return '\004';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/* 127 */     byte[] binValue = new byte[8];
/*     */ 
/*     */     
/* 130 */     binValue[0] = (byte)(getAttributeType() >> 8);
/* 131 */     binValue[1] = (byte)(getAttributeType() & 0xFF);
/*     */     
/* 133 */     binValue[2] = (byte)(getDataLength() >> 8);
/* 134 */     binValue[3] = (byte)(getDataLength() & 0xFF);
/*     */     
/* 136 */     binValue[4] = 0;
/* 137 */     binValue[5] = 0;
/* 138 */     binValue[6] = 0;
/* 139 */     binValue[7] = (byte)((getChangeIpFlag() ? 4 : 0) + (getChangePortFlag() ? 2 : 0));
/*     */     
/* 141 */     return binValue;
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
/*     */   public void setChangeIpFlag(boolean changeIP) {
/* 155 */     this.changeIpFlag = changeIP;
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
/*     */   public boolean getChangeIpFlag() {
/* 167 */     return this.changeIpFlag;
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
/*     */   public void setChangePortFlag(boolean changePort) {
/* 179 */     this.changePortFlag = changePort;
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
/*     */   public boolean getChangePortFlag() {
/* 191 */     return this.changePortFlag;
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
/* 208 */     offset = (char)(offset + 3);
/* 209 */     setChangeIpFlag(((attributeValue[offset] & 0x4) > 0));
/* 210 */     setChangePortFlag(((attributeValue[offset] & 0x2) > 0));
/*     */   }
/*     */ }

