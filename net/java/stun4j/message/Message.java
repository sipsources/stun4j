/*     */ package net.java.stun4j.message;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.attribute.Attribute;
/*     */ import net.java.stun4j.attribute.AttributeDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Message
/*     */ {
/*     */   public static final char BINDING_REQUEST = '\001';
/*     */   public static final char BINDING_RESPONSE = 'ā';
/*     */   public static final char BINDING_ERROR_RESPONSE = 'đ';
/*     */   public static final char SHARED_SECRET_REQUEST = '\002';
/*     */   public static final char SHARED_SECRET_RESPONSE = 'Ă';
/*     */   public static final char SHARED_SECRET_ERROR_RESPONSE = 'Ē';
/*     */   public static final byte HEADER_LENGTH = 20;
/*  49 */   protected char messageType = Character.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   protected byte[] transactionID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte TRANSACTION_ID_LENGTH = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected LinkedHashMap attributes = new LinkedHashMap();
/*     */ 
/*     */   
/*     */   public static final byte N_A = 0;
/*     */ 
/*     */   
/*     */   public static final byte C = 1;
/*     */ 
/*     */   
/*     */   public static final byte O = 2;
/*     */ 
/*     */   
/*     */   public static final byte M = 3;
/*     */ 
/*     */   
/*     */   protected static final byte BINDING_REQUEST_PRESENTITY_INDEX = 0;
/*     */ 
/*     */   
/*     */   protected static final byte BINDING_RESPONSE_PRESENTITY_INDEX = 1;
/*     */ 
/*     */   
/*     */   protected static final byte BINDING_ERROR_RESPONSE_PRESENTITY_INDEX = 2;
/*     */ 
/*     */   
/*     */   protected static final byte SHARED_SECRET_REQUEST_PRESENTITY_INDEX = 3;
/*     */ 
/*     */   
/*     */   protected static final byte SHARED_SECRET_RESPONSE_PRESENTITY_INDEX = 4;
/*     */ 
/*     */   
/*     */   protected static final byte SHARED_SECRET_ERROR_RESPONSE_PRESENTITY_INDEX = 5;
/*     */   
/*     */   protected static final byte MAPPED_ADDRESS_PRESENTITY_INDEX = 0;
/*     */   
/*     */   protected static final byte RESPONSE_ADDRESS_PRESENTITY_INDEX = 1;
/*     */   
/*     */   protected static final byte CHANGE_REQUEST_PRESENTITY_INDEX = 2;
/*     */   
/*     */   protected static final byte SOURCE_ADDRESS_PRESENTITY_INDEX = 3;
/*     */   
/*     */   protected static final byte CHANGED_ADDRESS_PRESENTITY_INDEX = 4;
/*     */   
/*     */   protected static final byte USERNAME_PRESENTITY_INDEX = 5;
/*     */   
/*     */   protected static final byte PASSWORD_PRESENTITY_INDEX = 6;
/*     */   
/*     */   protected static final byte MESSAGE_INTEGRITY_PRESENTITY_INDEX = 7;
/*     */   
/*     */   protected static final byte ERROR_CODE_PRESENTITY_INDEX = 8;
/*     */   
/*     */   protected static final byte UNKNOWN_ATTRIBUTES_PRESENTITY_INDEX = 9;
/*     */   
/*     */   protected static final byte REFLECTED_FROM_PRESENTITY_INDEX = 10;
/*     */   
/*     */   protected static final byte XOR_MAPPED_ADDRESS_PRESENTITY_INDEX = 11;
/*     */   
/*     */   protected static final byte XOR_ONLY_PRESENTITY_INDEX = 12;
/*     */   
/*     */   protected static final byte SERVER_PRESENTITY_INDEX = 13;
/*     */   
/*     */   protected static final byte UNKNOWN_OPTIONAL_ATTRIBUTES_PRESENTITY_INDEX = 14;
/*     */   
/* 131 */   protected static final byte[][] attributePresentities = new byte[][] { { 0, 3, 0, 0, 0, 0 }, { 2, 0, 0, 0, 0, 0 }, { 2, 0, 0, 0, 0, 0 }, { 0, 3, 0, 0, 0, 0 }, { 0, 3, 0, 0, 0, 0 }, { 2, 0, 0, 0, 3, 0 }, { 0, 0, 0, 0, 3, 0 }, { 2, 2, 0, 0, 0, 0 }, { 0, 0, 3, 0, 0, 3 }, { 0, 0, 1, 0, 0, 1 }, { 0, 1, 0, 0, 0, 0 }, { 0, 3, 0, 0, 0, 0 }, { 2, 0, 0, 0, 0, 0 }, { 0, 2, 2, 0, 2, 2 }, { 2, 2, 2, 2, 2, 2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 169 */     char length = Character.MIN_VALUE;
/* 170 */     Attribute att = null;
/*     */     
/* 172 */     Iterator iter = this.attributes.entrySet().iterator();
/* 173 */     while (iter.hasNext()) {
/* 174 */       att = (Attribute)((Map.Entry)iter.next()).getValue();
/* 175 */       length = (char)(length + att.getDataLength() + 4);
/*     */     } 
/*     */     
/* 178 */     return length;
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
/*     */   public void addAttribute(Attribute attribute) throws StunException {
/* 191 */     Character attributeType = new Character(attribute.getAttributeType());
/*     */     
/* 193 */     if (getAttributePresentity(attributeType.charValue()) == 0) {
/* 194 */       throw new StunException(2, "The attribute " + attribute.getName() + " is not allowed in a " + getName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     this.attributes.put(attributeType, attribute);
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
/*     */   public Attribute getAttribute(char attributeType) {
/* 213 */     return (Attribute)this.attributes.get(new Character(attributeType));
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
/*     */   public void removeAttribute(char attributeType) {
/* 233 */     this.attributes.remove(new Character(attributeType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttributeCount() {
/* 242 */     return this.attributes.size();
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
/*     */   protected void setMessageType(char messageType) throws StunException {
/* 260 */     this.messageType = messageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getMessageType() {
/* 269 */     return this.messageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionID(byte[] tranID) throws StunException {
/* 280 */     if (tranID == null || tranID.length != 16)
/*     */     {
/* 282 */       throw new StunException(2, "Invalid transaction id");
/*     */     }
/*     */     
/* 285 */     this.transactionID = new byte[16];
/* 286 */     System.arraycopy(tranID, 0, this.transactionID, 0, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTransactionID() {
/* 296 */     return this.transactionID;
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
/*     */   protected byte getAttributePresentity(char attributeType) {
/* 308 */     byte msgIndex = -1;
/* 309 */     byte attributeIndex = -1;
/*     */     
/* 311 */     switch (this.messageType) {
/*     */       case '\001':
/* 313 */         msgIndex = 0; break;
/* 314 */       case 'ā': msgIndex = 1; break;
/* 315 */       case 'đ': msgIndex = 2; break;
/* 316 */       case '\002': msgIndex = 3; break;
/* 317 */       case 'Ă': msgIndex = 4; break;
/* 318 */       case 'Ē': msgIndex = 5;
/*     */         break;
/*     */     } 
/* 321 */     switch (attributeType)
/*     */     { case '\001':
/* 323 */         attributeIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 348 */         return attributePresentities[attributeIndex][msgIndex];case '\002': attributeIndex = 1; return attributePresentities[attributeIndex][msgIndex];case '\003': attributeIndex = 2; return attributePresentities[attributeIndex][msgIndex];case '\004': attributeIndex = 3; return attributePresentities[attributeIndex][msgIndex];case '\005': attributeIndex = 4; return attributePresentities[attributeIndex][msgIndex];case '\006': attributeIndex = 5; return attributePresentities[attributeIndex][msgIndex];case '\007': attributeIndex = 6; return attributePresentities[attributeIndex][msgIndex];case '\b': attributeIndex = 7; return attributePresentities[attributeIndex][msgIndex];case '\t': attributeIndex = 8; return attributePresentities[attributeIndex][msgIndex];case '\n': attributeIndex = 9; return attributePresentities[attributeIndex][msgIndex];case '\013': attributeIndex = 10; return attributePresentities[attributeIndex][msgIndex];case '耠': attributeIndex = 11; return attributePresentities[attributeIndex][msgIndex];case '!': attributeIndex = 12; return attributePresentities[attributeIndex][msgIndex];case '耢': attributeIndex = 13; return attributePresentities[attributeIndex][msgIndex]; }  attributeIndex = 14; return attributePresentities[attributeIndex][msgIndex];
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
/* 359 */     switch (this.messageType) {
/*     */       case '\001':
/* 361 */         return "BINDING-REQUEST";
/* 362 */       case 'ā': return "BINDING-RESPONSE";
/* 363 */       case 'đ': return "BINDING-ERROR-RESPONSE";
/* 364 */       case '\002': return "SHARED-SECRET-REQUEST";
/* 365 */       case 'Ă': return "SHARED-SECRET-RESPONSE";
/* 366 */       case 'Ē': return "SHARED-SECRET-ERROR-RESPONSE";
/*     */     } 
/*     */     
/* 369 */     return "UNKNOWN-MESSAGE";
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
/* 381 */     if (!(obj instanceof Message) || obj == null)
/*     */     {
/* 383 */       return false;
/*     */     }
/* 385 */     if (obj == this) {
/* 386 */       return true;
/*     */     }
/* 388 */     Message msg = (Message)obj;
/* 389 */     if (msg.getMessageType() != getMessageType())
/* 390 */       return false; 
/* 391 */     if (msg.getDataLength() != getDataLength()) {
/* 392 */       return false;
/*     */     }
/*     */     
/* 395 */     Iterator iter = this.attributes.entrySet().iterator();
/* 396 */     while (iter.hasNext()) {
/* 397 */       Attribute localAtt = (Attribute)((Map.Entry)iter.next()).getValue();
/*     */       
/* 399 */       if (!localAtt.equals(msg.getAttribute(localAtt.getAttributeType()))) {
/* 400 */         return false;
/*     */       }
/*     */     } 
/* 403 */     return true;
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
/*     */   public byte[] encode() throws StunException {
/* 417 */     validateAttributePresentity();
/* 418 */     char dataLength = getDataLength();
/* 419 */     byte[] binMsg = new byte[20 + dataLength];
/* 420 */     int offset = 0;
/*     */     
/* 422 */     binMsg[offset++] = (byte)(getMessageType() >> 8);
/* 423 */     binMsg[offset++] = (byte)(getMessageType() & 0xFF);
/*     */     
/* 425 */     binMsg[offset++] = (byte)(dataLength >> 8);
/* 426 */     binMsg[offset++] = (byte)(dataLength & 0xFF);
/*     */     
/* 428 */     System.arraycopy(getTransactionID(), 0, binMsg, offset, 16);
/* 429 */     offset += 16;
/*     */     
/* 431 */     Iterator iter = this.attributes.entrySet().iterator();
/* 432 */     while (iter.hasNext()) {
/*     */       
/* 434 */       Attribute attribute = (Attribute)((Map.Entry)iter.next()).getValue();
/*     */       
/* 436 */       byte[] attBinValue = attribute.encode();
/* 437 */       System.arraycopy(attBinValue, 0, binMsg, offset, attBinValue.length);
/* 438 */       offset += attBinValue.length;
/*     */     } 
/*     */     
/* 441 */     return binMsg;
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
/*     */   public static Message decode(byte[] binMessage, char offset, char arrayLen) throws StunException {
/*     */     Message message;
/* 456 */     arrayLen = (char)Math.min(binMessage.length, arrayLen);
/*     */     
/* 458 */     if (binMessage == null || arrayLen - offset < 20) {
/* 459 */       throw new StunException(2, "The given binary array is not a valid StunMessage");
/*     */     }
/*     */     
/* 462 */     offset = (char)(offset + 1); offset = (char)(offset + 1); char messageType = (char)(binMessage[offset] << 8 | binMessage[offset] & 0xFF);
/*     */     
/* 464 */     if (isResponseType(messageType)) {
/*     */       
/* 466 */       message = new Response();
/*     */     }
/*     */     else {
/*     */       
/* 470 */       message = new Request();
/*     */     } 
/* 472 */     message.setMessageType(messageType);
/*     */     
/* 474 */     offset = (char)(offset + 1); offset = (char)(offset + 1); int length = (char)(binMessage[offset] << 8 | binMessage[offset] & 0xFF);
/*     */     
/* 476 */     if (arrayLen - offset - 16 < length) {
/* 477 */       throw new StunException(2, "The given binary array does not seem to contain a whole StunMessage");
/*     */     }
/*     */ 
/*     */     
/* 481 */     byte[] tranID = new byte[16];
/* 482 */     System.arraycopy(binMessage, offset, tranID, 0, 16);
/* 483 */     message.setTransactionID(tranID);
/* 484 */     offset = (char)(offset + 16);
/*     */     
/* 486 */     while (offset - 20 < length) {
/*     */       
/* 488 */       Attribute att = AttributeDecoder.decode(binMessage, offset, (char)(length - offset));
/*     */ 
/*     */       
/* 491 */       message.addAttribute(att);
/* 492 */       offset = (char)(offset + att.getDataLength() + 4);
/*     */     } 
/*     */     
/* 495 */     return message;
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
/*     */   protected void validateAttributePresentity() throws StunException {
/* 511 */     for (char i = '\001'; i < '\013'; i = (char)(i + 1)) {
/* 512 */       if (getAttributePresentity(i) == 3 && getAttribute(i) == null) {
/* 513 */         throw new StunException(1, "A mandatory attribute (type=" + i + ") is missing!");
/*     */       }
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
/*     */   public static boolean isResponseType(char type) {
/* 528 */     return ((type >> 8 & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRequestType(char type) {
/* 539 */     return !isResponseType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 544 */     return getName() + "(" + getMessageType() + ")[attrib.count=" + getAttributeCount() + " len=" + getDataLength() + " tranID=" + getTransactionID() + "]";
/*     */   }
/*     */ }

