/*    */ package net.java.stun4j.attribute;
/*    */ 
/*    */ import net.java.stun4j.StunException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeDecoder
/*    */ {
/*    */   public static Attribute decode(byte[] bytes, char offset, char length) throws StunException {
/* 40 */     if (bytes == null || bytes.length < 4) {
/* 41 */       throw new StunException(2, "Could not decode the specified binary array.");
/*    */     }
/*    */ 
/*    */     
/* 45 */     char attributeType = (char)(bytes[offset] << 8 | bytes[offset + 1]);
/* 46 */     char attributeLength = (char)(bytes[offset + 2] << 8 | bytes[offset + 3]);
/*    */     
/* 48 */     if (attributeLength > bytes.length - offset) {
/* 49 */       throw new StunException(2, "The indicated attribute length (" + attributeLength + ") " + "does not match the length of the passed binary array");
/*    */     }
/*    */ 
/*    */     
/* 53 */     Attribute decodedAttribute = null;
/*    */     
/* 55 */     switch (attributeType)
/*    */     
/*    */     { case '\003':
/* 58 */         decodedAttribute = new ChangeRequestAttribute();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 94 */         decodedAttribute.setAttributeType(attributeType);
/*    */         
/* 96 */         decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength);
/*    */         
/* 98 */         return decodedAttribute;case '\005': decodedAttribute = new ChangedAddressAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\001': decodedAttribute = new MappedAddressAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\t': decodedAttribute = new ErrorCodeAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\b': throw new UnsupportedOperationException("The MESSAGE-INTEGRITY Attribute is not yet implemented.");case '\007': throw new UnsupportedOperationException("The PASSWORD Attribute is not yet implemented.");case '\013': decodedAttribute = new ReflectedFromAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\002': decodedAttribute = new ResponseAddressAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\004': decodedAttribute = new SourceAddressAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\n': decodedAttribute = new UnknownAttributesAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '耠': decodedAttribute = new XorMappedAddressAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '!': decodedAttribute = new XorOnlyAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '耢': decodedAttribute = new ServerAttribute(); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;case '\006': throw new UnsupportedOperationException("The USERNAME Attribute is not yet implemented."); }  decodedAttribute = new OptionalAttribute('耀'); decodedAttribute.setAttributeType(attributeType); decodedAttribute.decodeAttributeBody(bytes, (char)(4 + offset), attributeLength); return decodedAttribute;
/*    */   }
/*    */ }

