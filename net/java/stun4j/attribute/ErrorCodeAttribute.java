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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ErrorCodeAttribute
/*     */   extends Attribute
/*     */ {
/*     */   public static final char BAD_REQUEST = 'Ɛ';
/*     */   public static final char UNAUTHORIZED = 'Ƒ';
/*     */   public static final char UNKNOWN_ATTRIBUTE = 'Ƥ';
/*     */   public static final char STALE_CREDENTIALS = 'Ʈ';
/*     */   public static final char INTEGRITY_CHECK_FAILURE = 'Ư';
/*     */   public static final char MISSING_USERNAME = 'ư';
/*     */   public static final char USE_TLS = 'Ʊ';
/*     */   public static final char SERVER_ERROR = 'Ǵ';
/*     */   public static final char GLOBAL_FAILURE = 'ɘ';
/*  99 */   private byte errorClass = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private byte errorNumber = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private String reasonPhrase = null;
/*     */ 
/*     */   
/*     */   public static final String NAME = "ERROR-CODE";
/*     */ 
/*     */   
/*     */   ErrorCodeAttribute() {
/* 119 */     super('\t');
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
/*     */   public void setErrorCode(char errorCode) throws StunException {
/* 134 */     setErrorClass((byte)(errorCode / 100));
/* 135 */     setErrorNumber((byte)(errorCode % 100));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getErrorCode() {
/* 145 */     return (char)(getErrorClass() * 100 + getErrorNumber());
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
/*     */   public void setErrorNumber(byte errorNumber) throws StunException {
/* 157 */     if (errorNumber < 0 || errorNumber > 99) {
/* 158 */       throw new StunException(2, errorNumber + " is not a valid error number!");
/*     */     }
/* 160 */     this.errorNumber = errorNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getErrorNumber() {
/* 169 */     return this.errorNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorClass(byte errorClass) throws StunException {
/* 180 */     if (errorClass < 0 || errorClass > 99) {
/* 181 */       throw new StunException(2, errorClass + " is not a valid error number!");
/*     */     }
/* 183 */     this.errorClass = errorClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getErrorClass() {
/* 192 */     return this.errorClass;
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
/*     */   public static String getDefaultReasonPhrase(char errorCode) {
/* 206 */     switch (errorCode) {
/*     */       case 'Ɛ':
/* 208 */         return "(Bad Request): The request was malformed.  The client should not retry the request without modification from the previous attempt.";
/*     */       case 'Ƒ':
/* 210 */         return "(Unauthorized): The Binding Request did not contain a MESSAGE-INTEGRITY attribute.";
/*     */       case 'Ƥ':
/* 212 */         return "(Unknown Attribute): The server did not understand a mandatory attribute in the request.";
/*     */       case 'Ʈ':
/* 214 */         return "(Stale Credentials): The Binding Request did contain a MESSAGE-INTEGRITY attribute, but it used a shared secret that has expired.  The client should obtain a new shared secret and tryagain";
/*     */ 
/*     */       
/*     */       case 'Ư':
/* 218 */         return "(Integrity Check Failure): The Binding Request contained a MESSAGE-INTEGRITY attribute, but the HMAC failed verification. This could be a sign of a potential attack, or client implementation error.";
/*     */ 
/*     */       
/*     */       case 'ư':
/* 222 */         return "(Missing Username): The Binding Request contained a MESSAGE-INTEGRITY attribute, but not a USERNAME attribute.  Both must bepresent for integrity checks.";
/*     */       
/*     */       case 'Ʊ':
/* 225 */         return "(Use TLS): The Shared Secret request has to be sent over TLS, butwas not received over TLS.";
/*     */       case 'Ǵ':
/* 227 */         return "(Server Error): The server has suffered a temporary error. Theclient should try again.";
/*     */       case 'ɘ':
/* 229 */         return "(Global Failure:) The server is refusing to fulfill the request.The client should not retry.";
/*     */     } 
/*     */     
/* 232 */     return "Unknown Error";
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
/*     */   public void setReasonPhrase(String reasonPhrase) {
/* 245 */     this.reasonPhrase = reasonPhrase;
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
/*     */   public String getReasonPhrase() {
/* 257 */     return this.reasonPhrase;
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
/* 268 */     return "ERROR-CODE";
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
/* 279 */     char len = (char)(4 + (char)((this.reasonPhrase == null) ? Character.MIN_VALUE : (this.reasonPhrase.length() * 2)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     len = (char)(len + 4 - len % 4);
/* 290 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/* 299 */     byte[] binValue = new byte[4 + getDataLength()];
/*     */ 
/*     */     
/* 302 */     binValue[0] = (byte)(getAttributeType() >> 8);
/* 303 */     binValue[1] = (byte)(getAttributeType() & 0xFF);
/*     */     
/* 305 */     binValue[2] = (byte)(getDataLength() >> 8);
/* 306 */     binValue[3] = (byte)(getDataLength() & 0xFF);
/*     */ 
/*     */     
/* 309 */     binValue[4] = 0;
/* 310 */     binValue[5] = 0;
/*     */ 
/*     */     
/* 313 */     binValue[6] = getErrorClass();
/* 314 */     binValue[7] = getErrorNumber();
/*     */     
/* 316 */     int offset = 8;
/* 317 */     char[] chars = this.reasonPhrase.toCharArray();
/* 318 */     for (int i = 0; i < this.reasonPhrase.length(); i++, offset += 2) {
/* 319 */       binValue[offset] = (byte)(chars[i] >> 8);
/* 320 */       binValue[offset + 1] = (byte)(chars[i] & 0xFF);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 325 */     if (this.reasonPhrase.length() % 4 != 0) {
/*     */       
/* 327 */       binValue[binValue.length - 2] = 0;
/* 328 */       binValue[binValue.length - 1] = 32;
/*     */     } 
/*     */     
/* 331 */     return binValue;
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
/* 343 */     if (!(obj instanceof ErrorCodeAttribute) || obj == null)
/*     */     {
/* 345 */       return false;
/*     */     }
/* 347 */     if (obj == this) {
/* 348 */       return true;
/*     */     }
/* 350 */     ErrorCodeAttribute att = (ErrorCodeAttribute)obj;
/* 351 */     if (att.getAttributeType() != getAttributeType() || att.getDataLength() != getDataLength() || att.getErrorClass() != getErrorClass() || att.getErrorNumber() != getErrorNumber() || (att.getReasonPhrase() != null && !att.getReasonPhrase().equals(getReasonPhrase())))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 359 */       return false;
/*     */     }
/* 361 */     return true;
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
/*     */   void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws StunException {
/* 379 */     offset = (char)(offset + 2);
/*     */ 
/*     */     
/* 382 */     offset = (char)(offset + 1); setErrorClass(attributeValue[offset]);
/* 383 */     offset = (char)(offset + 1); setErrorNumber(attributeValue[offset]);
/*     */ 
/*     */     
/* 386 */     char[] reasonPhrase = new char[(length - 4) / 2];
/*     */     
/* 388 */     for (int i = 0; i < reasonPhrase.length; i++, offset = (char)(offset + 2)) {
/* 389 */       reasonPhrase[i] = (char)(attributeValue[offset] | attributeValue[offset + 1]);
/*     */     }
/*     */     
/* 392 */     setReasonPhrase((new String(reasonPhrase)).trim());
/*     */   }
/*     */ }


