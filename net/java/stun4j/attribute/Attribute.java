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
/*     */ public abstract class Attribute
/*     */ {
/*     */   public static final char MAPPED_ADDRESS = '\001';
/*     */   public static final char RESPONSE_ADDRESS = '\002';
/*     */   public static final char CHANGE_REQUEST = '\003';
/*     */   public static final char SOURCE_ADDRESS = '\004';
/*     */   public static final char CHANGED_ADDRESS = '\005';
/*     */   public static final char USERNAME = '\006';
/*     */   public static final char PASSWORD = '\007';
/*     */   public static final char MESSAGE_INTEGRITY = '\b';
/*     */   public static final char ERROR_CODE = '\t';
/*     */   public static final char UNKNOWN_ATTRIBUTES = '\n';
/*     */   public static final char REFLECTED_FROM = '\013';
/*     */   public static final char XOR_MAPPED_ADDRESS = '耠';
/*     */   public static final char XOR_ONLY = '!';
/*     */   public static final char SERVER = '耢';
/*     */   public static final char UNKNOWN_OPTIONAL_ATTRIBUTE = '耀';
/*  64 */   protected char attributeType = Character.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final char HEADER_LENGTH = '\004';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Attribute(char attributeType) {
/*  78 */     setAttributeType(attributeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract char getDataLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getAttributeType() {
/* 101 */     return this.attributeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAttributeType(char type) {
/* 110 */     this.attributeType = type;
/*     */   }
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract byte[] encode();
/*     */   
/*     */   abstract void decodeAttributeBody(byte[] paramArrayOfbyte, char paramChar1, char paramChar2) throws StunException;
/*     */ }


