/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.MsgFixture;
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
/*     */ public class AttributeDecoderTest
/*     */   extends TestCase
/*     */ {
/*  18 */   private AttributeDecoder attributeDecoder = null;
/*     */   private MsgFixture msgFixture;
/*  20 */   private byte[] expectedAttributeValue = null;
/*     */   
/*     */   public AttributeDecoderTest(String name) {
/*  23 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  27 */     super.setUp();
/*     */     
/*  29 */     this.attributeDecoder = new AttributeDecoder();
/*  30 */     this.msgFixture = new MsgFixture();
/*     */ 
/*     */     
/*  33 */     int offset = 4;
/*  34 */     this.expectedAttributeValue = new byte[this.msgFixture.unknownOptionalAttribute.length - offset];
/*     */     
/*  36 */     System.arraycopy(this.msgFixture.unknownOptionalAttribute, offset, this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */ 
/*     */ 
/*     */     
/*  40 */     this.msgFixture.setUp();
/*     */   }
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  44 */     this.attributeDecoder = null;
/*  45 */     this.msgFixture.tearDown();
/*     */     
/*  47 */     this.msgFixture = null;
/*  48 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeMappedAddress() throws StunException {
/*  55 */     byte[] bytes = this.msgFixture.mappedAddress;
/*  56 */     char offset = Character.MIN_VALUE;
/*  57 */     char length = (char)bytes.length;
/*     */ 
/*     */     
/*  60 */     MappedAddressAttribute expectedReturn = new MappedAddressAttribute();
/*     */     
/*  62 */     expectedReturn.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */ 
/*     */     
/*  66 */     Attribute actualReturn = AttributeDecoder.decode(bytes, offset, length);
/*  67 */     assertEquals("AttributeDecoder.decode() failed for a MAPPED-ADDRESS attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeMappedAddress_v6() throws StunException {
/*  75 */     byte[] bytes = this.msgFixture.mappedAddressv6;
/*  76 */     char offset = Character.MIN_VALUE;
/*  77 */     char length = (char)bytes.length;
/*     */ 
/*     */     
/*  80 */     MappedAddressAttribute expectedReturn = new MappedAddressAttribute();
/*     */     
/*  82 */     expectedReturn.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, 1904));
/*     */ 
/*     */ 
/*     */     
/*  86 */     Attribute actualReturn = AttributeDecoder.decode(bytes, offset, length);
/*  87 */     assertEquals("AttributeDecoder.decode() failed for a MAPPED-ADDRESS attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeChangeRequest() throws StunException {
/*  95 */     byte[] bytes = this.msgFixture.chngReqTestValue1;
/*  96 */     char offset = Character.MIN_VALUE;
/*  97 */     char length = (char)bytes.length;
/*     */ 
/*     */     
/* 100 */     ChangeRequestAttribute expectedReturn = new ChangeRequestAttribute();
/* 101 */     expectedReturn.setChangeIpFlag(false);
/* 102 */     expectedReturn.setChangePortFlag(false);
/*     */     
/* 104 */     Attribute actualReturn = AttributeDecoder.decode(bytes, offset, length);
/* 105 */     assertEquals("AttributeDecoder.decode() failed for a CHANGE-REQUEST attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeErrorCode() throws StunException {
/* 115 */     byte[] bytes = this.msgFixture.errCodeTestValue;
/* 116 */     char offset = Character.MIN_VALUE;
/* 117 */     char length = (char)bytes.length;
/*     */ 
/*     */     
/* 120 */     ErrorCodeAttribute expectedReturn = new ErrorCodeAttribute();
/* 121 */     expectedReturn.setErrorClass((byte)4);
/* 122 */     expectedReturn.setErrorNumber((byte)20);
/* 123 */     expectedReturn.setReasonPhrase("Test error reason phrase.");
/*     */     
/* 125 */     Attribute actualReturn = AttributeDecoder.decode(bytes, offset, length);
/* 126 */     assertEquals("AttributeDecoder.decode() failed for a ERROR-CODE attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeUnknownAttributes() throws StunException {
/* 135 */     byte[] bytes = this.msgFixture.unknownAttsDecodeTestValue;
/* 136 */     char offset = Character.MIN_VALUE;
/* 137 */     char length = (char)this.msgFixture.mappedAddress.length;
/*     */ 
/*     */     
/* 140 */     UnknownAttributesAttribute expectedReturn = new UnknownAttributesAttribute();
/* 141 */     expectedReturn.addAttributeID(' ');
/* 142 */     expectedReturn.addAttributeID('!');
/* 143 */     expectedReturn.addAttributeID('"');
/*     */     
/* 145 */     Attribute actualReturn = AttributeDecoder.decode(bytes, offset, length);
/* 146 */     assertEquals("AttributeDecoder.decode() failed for a ERROR-CODE attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeUnknownOptionalAttribute() throws StunException {
/* 155 */     byte[] bytes = this.msgFixture.unknownOptionalAttribute;
/* 156 */     char offset = Character.MIN_VALUE;
/* 157 */     char length = (char)this.msgFixture.mappedAddress.length;
/*     */ 
/*     */     
/* 160 */     OptionalAttribute expectedReturn = new OptionalAttribute('耀');
/*     */     
/* 162 */     expectedReturn.setBody(this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */ 
/*     */     
/* 165 */     Attribute actualReturn = AttributeDecoder.decode(bytes, offset, length);
/* 166 */     assertEquals("AttributeDecoder.decode() failed for a UNKNOWN_OPTIONAL attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ }
