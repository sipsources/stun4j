/*     */ package net.java.stun4j.message;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.attribute.Attribute;
/*     */ import net.java.stun4j.attribute.AttributeFactory;
/*     */ import net.java.stun4j.attribute.ChangeRequestAttribute;
/*     */ import net.java.stun4j.attribute.ChangedAddressAttribute;
/*     */ import net.java.stun4j.attribute.ErrorCodeAttribute;
/*     */ import net.java.stun4j.attribute.MappedAddressAttribute;
/*     */ import net.java.stun4j.attribute.SourceAddressAttribute;
/*     */ import net.java.stun4j.attribute.UnknownAttributesAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageFactory
/*     */ {
/*  27 */   private static final Logger logger = Logger.getLogger(MessageFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request createBindingRequest() {
/*  36 */     Request bindingRequest = new Request();
/*     */     
/*     */     try {
/*  39 */       bindingRequest.setMessageType('\001');
/*     */     }
/*  41 */     catch (StunException ex) {
/*     */ 
/*     */       
/*  44 */       logger.log(Level.FINE, "Failed to set message type.", (Throwable)ex);
/*     */     } 
/*     */ 
/*     */     
/*  48 */     ChangeRequestAttribute attribute = AttributeFactory.createChangeRequestAttribute();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  53 */       bindingRequest.addAttribute((Attribute)attribute);
/*     */     }
/*  55 */     catch (StunException ex) {
/*     */ 
/*     */       
/*  58 */       throw new RuntimeException("Failed to add a change request attribute to a binding request!");
/*     */     } 
/*     */     
/*  61 */     return bindingRequest;
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
/*     */   public static Response createBindingResponse(StunAddress mappedAddress, StunAddress sourceAddress, StunAddress changedAddress) throws StunException {
/*  80 */     Response bindingResponse = new Response();
/*  81 */     bindingResponse.setMessageType('ā');
/*     */ 
/*     */     
/*  84 */     MappedAddressAttribute mappedAddressAttribute = AttributeFactory.createMappedAddressAttribute(mappedAddress);
/*     */ 
/*     */ 
/*     */     
/*  88 */     SourceAddressAttribute sourceAddressAttribute = AttributeFactory.createSourceAddressAttribute(sourceAddress);
/*     */ 
/*     */ 
/*     */     
/*  92 */     ChangedAddressAttribute changedAddressAttribute = AttributeFactory.createChangedAddressAttribute(changedAddress);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  97 */       bindingResponse.addAttribute((Attribute)mappedAddressAttribute);
/*  98 */       bindingResponse.addAttribute((Attribute)sourceAddressAttribute);
/*  99 */       bindingResponse.addAttribute((Attribute)changedAddressAttribute);
/*     */     }
/* 101 */     catch (StunException ex) {
/*     */       
/* 103 */       throw new StunException(3, "Failed to add a mandatory attribute to the binding response.");
/*     */     } 
/*     */ 
/*     */     
/* 107 */     return bindingResponse;
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
/*     */   
/*     */   private static Response createBindingErrorResponse(char errorCode, String reasonPhrase, char[] unknownAttributes) throws StunException {
/* 130 */     Response bindingErrorResponse = new Response();
/* 131 */     bindingErrorResponse.setMessageType('đ');
/*     */ 
/*     */     
/* 134 */     UnknownAttributesAttribute unknownAttributesAttribute = null;
/* 135 */     ErrorCodeAttribute errorCodeAttribute = AttributeFactory.createErrorCodeAttribute(errorCode, reasonPhrase);
/*     */ 
/*     */     
/* 138 */     bindingErrorResponse.addAttribute((Attribute)errorCodeAttribute);
/*     */     
/* 140 */     if (unknownAttributes != null) {
/*     */       
/* 142 */       unknownAttributesAttribute = AttributeFactory.createUnknownAttributesAttribute();
/*     */       
/* 144 */       for (int i = 0; i < unknownAttributes.length; i++)
/*     */       {
/* 146 */         unknownAttributesAttribute.addAttributeID(unknownAttributes[i]);
/*     */       }
/* 148 */       bindingErrorResponse.addAttribute((Attribute)unknownAttributesAttribute);
/*     */     } 
/*     */     
/* 151 */     return bindingErrorResponse;
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
/*     */   public static Response createBindingErrorResponseUnknownAttributes(char[] unknownAttributes) throws StunException {
/* 169 */     return createBindingErrorResponse('Ƥ', null, unknownAttributes);
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
/*     */   public static Response createBindingErrorResponseUnknownAttributes(String reasonPhrase, char[] unknownAttributes) throws StunException {
/* 191 */     return createBindingErrorResponse('Ƥ', reasonPhrase, unknownAttributes);
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
/*     */   public static Response createBindingErrorResponse(char errorCode, String reasonPhrase) throws StunException {
/* 212 */     return createBindingErrorResponse(errorCode, reasonPhrase, null);
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
/*     */   public static Response createBindingErrorResponse(char errorCode) throws StunException {
/* 229 */     return createBindingErrorResponse(errorCode, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Request createShareSecretRequest() {
/* 235 */     throw new UnsupportedOperationException("Shared Secret Support is not currently impolemented");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Response createSharedSecretResponse() {
/* 241 */     throw new UnsupportedOperationException("Shared Secret Support is not currently impolemented");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Response createSharedSecretErrorResponse() {
/* 247 */     throw new UnsupportedOperationException("Shared Secret Support is not currently impolemented");
/*     */   }
/*     */ }

