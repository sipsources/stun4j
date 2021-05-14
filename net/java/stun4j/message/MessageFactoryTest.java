/*     */ package net.java.stun4j.message;
/*     */ import net.java.stun4j.MsgFixture;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.attribute.Attribute;
/*     */ import net.java.stun4j.attribute.AttributeFactory;
/*     */ import net.java.stun4j.attribute.ChangedAddressAttribute;
/*     */ import net.java.stun4j.attribute.ErrorCodeAttribute;
/*     */ import net.java.stun4j.attribute.MappedAddressAttribute;
/*     */ import net.java.stun4j.attribute.SourceAddressAttribute;
/*     */ import net.java.stun4j.attribute.UnknownAttributesAttribute;
/*     */ 
/*     */ public class MessageFactoryTest extends TestCase {
/*  14 */   private MessageFactory messageFactory = null;
/*     */   private MsgFixture msgFixture;
/*     */   
/*     */   public MessageFactoryTest(String name) {
/*  18 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  22 */     super.setUp();
/*  23 */     this.messageFactory = new MessageFactory();
/*  24 */     this.msgFixture = new MsgFixture();
/*     */     
/*  26 */     this.msgFixture.setUp();
/*     */   }
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  30 */     this.messageFactory = null;
/*  31 */     this.msgFixture.tearDown();
/*     */     
/*  33 */     this.msgFixture = null;
/*  34 */     super.tearDown();
/*     */   }
/*     */   
/*     */   public void testCreateBindingErrorResponse() throws StunException {
/*  38 */     char errorCode = 'Ɛ';
/*     */     
/*  40 */     Response expectedReturn = new Response();
/*  41 */     expectedReturn.setMessageType('đ');
/*     */     
/*  43 */     ErrorCodeAttribute errorCodeAttribute = AttributeFactory.createErrorCodeAttribute(errorCode);
/*  44 */     expectedReturn.addAttribute((Attribute)errorCodeAttribute);
/*     */     
/*  46 */     Message actualReturn = MessageFactory.createBindingErrorResponse(errorCode);
/*  47 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void testCreateBindingErrorResponse1() throws StunException {
/*  52 */     char errorCode = 'Ɛ';
/*  53 */     String reasonPhrase = "Bad Request";
/*     */     
/*  55 */     Response expectedReturn = new Response();
/*  56 */     expectedReturn.setMessageType('đ');
/*     */     
/*  58 */     ErrorCodeAttribute errorCodeAttribute = AttributeFactory.createErrorCodeAttribute(errorCode, reasonPhrase);
/*  59 */     expectedReturn.addAttribute((Attribute)errorCodeAttribute);
/*     */     
/*  61 */     Message actualReturn = MessageFactory.createBindingErrorResponse(errorCode, reasonPhrase);
/*  62 */     assertEquals("Failed to create an error code attribute.", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testCreateBindingErrorResponseUnknownAttributes() throws StunException {
/*  69 */     char errorCode = 'Ƥ';
/*  70 */     char[] unknownAttributes = { '\025', '\026', '\027' };
/*     */ 
/*     */     
/*  73 */     Response expectedReturn = new Response();
/*  74 */     expectedReturn.setMessageType('đ');
/*     */     
/*  76 */     ErrorCodeAttribute errorCodeAttribute = AttributeFactory.createErrorCodeAttribute(errorCode);
/*  77 */     errorCodeAttribute.setReasonPhrase(ErrorCodeAttribute.getDefaultReasonPhrase(errorCode));
/*  78 */     expectedReturn.addAttribute((Attribute)errorCodeAttribute);
/*     */     
/*  80 */     UnknownAttributesAttribute unknownAtts = AttributeFactory.createUnknownAttributesAttribute();
/*     */ 
/*     */     
/*  83 */     for (int i = 0; i < unknownAttributes.length; i++) {
/*  84 */       unknownAtts.addAttributeID(unknownAttributes[i]);
/*     */     }
/*  86 */     expectedReturn.addAttribute((Attribute)unknownAtts);
/*     */ 
/*     */     
/*  89 */     Message actualReturn = MessageFactory.createBindingErrorResponseUnknownAttributes(unknownAttributes);
/*     */     
/*  91 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testCreateBindingErrorResponseUnknownAttributes1() throws StunException {
/*  98 */     char errorCode = 'Ƥ';
/*  99 */     String reasonPhrase = "UnknwonAttributes";
/* 100 */     char[] unknownAttributes = { '\025', '\026', '\027' };
/*     */     
/* 102 */     Response expectedReturn = new Response();
/* 103 */     expectedReturn.setMessageType('đ');
/*     */     
/* 105 */     ErrorCodeAttribute errorCodeAttribute = AttributeFactory.createErrorCodeAttribute(errorCode, reasonPhrase);
/*     */     
/* 107 */     expectedReturn.addAttribute((Attribute)errorCodeAttribute);
/*     */     
/* 109 */     UnknownAttributesAttribute unknownAtts = AttributeFactory.createUnknownAttributesAttribute();
/*     */ 
/*     */     
/* 112 */     for (int i = 0; i < unknownAttributes.length; i++)
/*     */     {
/* 114 */       unknownAtts.addAttributeID(unknownAttributes[i]);
/*     */     }
/* 116 */     expectedReturn.addAttribute((Attribute)unknownAtts);
/*     */     
/* 118 */     Message actualReturn = MessageFactory.createBindingErrorResponseUnknownAttributes(reasonPhrase, unknownAttributes);
/*     */     
/* 120 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */   
/*     */   public void testCreateBindingRequest() throws StunException {
/* 124 */     Request bindingRequest = new Request();
/* 125 */     Request expectedReturn = bindingRequest;
/* 126 */     bindingRequest.setMessageType('\001');
/*     */     
/* 128 */     ChangeRequestAttribute changeRequestAttribute = AttributeFactory.createChangeRequestAttribute(false, false);
/*     */     
/* 130 */     bindingRequest.addAttribute((Attribute)changeRequestAttribute);
/*     */     
/* 132 */     Request actualReturn = MessageFactory.createBindingRequest();
/* 133 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void testCreateBindingResponse() throws StunException {
/* 139 */     Response bindingResponse = new Response();
/* 140 */     bindingResponse.setMessageType('ā');
/*     */     
/* 142 */     MappedAddressAttribute mappedAddressAttribute = AttributeFactory.createMappedAddressAttribute(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */     
/* 145 */     bindingResponse.addAttribute((Attribute)mappedAddressAttribute);
/*     */     
/* 147 */     SourceAddressAttribute sourceAddressAttribute = AttributeFactory.createSourceAddressAttribute(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_2, 3478));
/*     */ 
/*     */     
/* 150 */     bindingResponse.addAttribute((Attribute)sourceAddressAttribute);
/*     */     
/* 152 */     ChangedAddressAttribute changedAddressAttribute = AttributeFactory.createChangedAddressAttribute(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_3, 3479));
/*     */ 
/*     */ 
/*     */     
/* 156 */     bindingResponse.addAttribute((Attribute)changedAddressAttribute);
/*     */     
/* 158 */     Message expectedReturn = bindingResponse;
/* 159 */     Message actualReturn = MessageFactory.createBindingResponse(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904), new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_2, 3478), new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_3, 3479));
/*     */ 
/*     */ 
/*     */     
/* 163 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ }

