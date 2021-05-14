/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import net.java.stun4j.StunAddress;
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
/*     */ public class AttributeFactory
/*     */ {
/*     */   public static ChangeRequestAttribute createChangeRequestAttribute() {
/*  38 */     return createChangeRequestAttribute(false, false);
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
/*     */   public static ChangeRequestAttribute createChangeRequestAttribute(boolean changeIP, boolean changePort) {
/*  51 */     ChangeRequestAttribute attribute = new ChangeRequestAttribute();
/*     */     
/*  53 */     attribute.setChangeIpFlag(changeIP);
/*  54 */     attribute.setChangePortFlag(changePort);
/*     */     
/*  56 */     return attribute;
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
/*     */   public static ChangedAddressAttribute createChangedAddressAttribute(StunAddress address) {
/*  70 */     ChangedAddressAttribute attribute = new ChangedAddressAttribute();
/*     */     
/*  72 */     attribute.setAddress(address);
/*     */     
/*  74 */     return attribute;
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
/*     */   public static ErrorCodeAttribute createErrorCodeAttribute(byte errorClass, byte errorNumber) throws StunException {
/*  94 */     return createErrorCodeAttribute(errorClass, errorNumber, null);
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
/*     */   public static ErrorCodeAttribute createErrorCodeAttribute(byte errorClass, byte errorNumber, String reasonPhrase) throws StunException {
/* 116 */     ErrorCodeAttribute attribute = new ErrorCodeAttribute();
/*     */     
/* 118 */     attribute.setErrorClass(errorClass);
/* 119 */     attribute.setErrorNumber(errorNumber);
/*     */     
/* 121 */     attribute.setReasonPhrase((reasonPhrase == null) ? ErrorCodeAttribute.getDefaultReasonPhrase(attribute.getErrorCode()) : reasonPhrase);
/*     */ 
/*     */ 
/*     */     
/* 125 */     return attribute;
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
/*     */   public static ErrorCodeAttribute createErrorCodeAttribute(char errorCode) throws StunException {
/* 141 */     return createErrorCodeAttribute(errorCode, (String)null);
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
/*     */   public static ErrorCodeAttribute createErrorCodeAttribute(char errorCode, String reasonPhrase) throws StunException {
/* 161 */     ErrorCodeAttribute attribute = new ErrorCodeAttribute();
/*     */     
/* 163 */     attribute.setErrorCode(errorCode);
/* 164 */     attribute.setReasonPhrase((reasonPhrase == null) ? ErrorCodeAttribute.getDefaultReasonPhrase(attribute.getErrorCode()) : reasonPhrase);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     return attribute;
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
/*     */   public static MappedAddressAttribute createMappedAddressAttribute(StunAddress address) {
/* 183 */     MappedAddressAttribute attribute = new MappedAddressAttribute();
/*     */     
/* 185 */     attribute.setAddress(address);
/*     */     
/* 187 */     return attribute;
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
/*     */   public static ReflectedFromAttribute createReflectedFromAttribute(StunAddress address) {
/* 202 */     ReflectedFromAttribute attribute = new ReflectedFromAttribute();
/*     */     
/* 204 */     attribute.setAddress(address);
/*     */     
/* 206 */     return attribute;
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
/*     */   public static ResponseAddressAttribute createResponseAddressAttribute(StunAddress address) {
/* 220 */     ResponseAddressAttribute attribute = new ResponseAddressAttribute();
/*     */     
/* 222 */     attribute.setAddress(address);
/*     */     
/* 224 */     return attribute;
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
/*     */   public static SourceAddressAttribute createSourceAddressAttribute(StunAddress address) {
/* 238 */     SourceAddressAttribute attribute = new SourceAddressAttribute();
/*     */     
/* 240 */     attribute.setAddress(address);
/*     */     
/* 242 */     return attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UnknownAttributesAttribute createUnknownAttributesAttribute() {
/* 253 */     UnknownAttributesAttribute attribute = new UnknownAttributesAttribute();
/*     */     
/* 255 */     return attribute;
/*     */   }
/*     */ }

