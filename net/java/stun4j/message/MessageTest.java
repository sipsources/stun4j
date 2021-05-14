/*     */ package net.java.stun4j.message;
/*     */ import java.util.Arrays;
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.MsgFixture;
/*     */ import net.java.stun4j.StunAddress;
/*     */ import net.java.stun4j.StunException;
/*     */ import net.java.stun4j.attribute.Attribute;
/*     */ import net.java.stun4j.attribute.AttributeFactory;
/*     */ import net.java.stun4j.attribute.ChangedAddressAttribute;
/*     */ import net.java.stun4j.attribute.MappedAddressAttribute;
/*     */ import net.java.stun4j.attribute.SourceAddressAttribute;
/*     */ import net.java.stun4j.attribute.UnknownAttributesAttribute;
/*     */ 
/*     */ public class MessageTest extends TestCase {
/*  15 */   private Message bindingRequest = null;
/*  16 */   private Message bindingResponse = null;
/*  17 */   private Message bindingErrorResponse = null;
/*     */   
/*  19 */   private MappedAddressAttribute mappedAddress = null;
/*  20 */   private SourceAddressAttribute sourceAddress = null;
/*  21 */   private ChangedAddressAttribute changedAddress = null;
/*     */   
/*  23 */   private ChangeRequestAttribute changeRequest = null;
/*     */   
/*  25 */   private ErrorCodeAttribute errorCodeAttribute = null;
/*  26 */   private UnknownAttributesAttribute unknownAttributesAttribute = null;
/*     */ 
/*     */   
/*     */   private MsgFixture msgFixture;
/*     */ 
/*     */   
/*     */   protected void setUp() throws Exception {
/*  33 */     super.setUp();
/*  34 */     this.msgFixture = new MsgFixture();
/*  35 */     this.msgFixture.setUp();
/*     */ 
/*     */ 
/*     */     
/*  39 */     this.bindingRequest = new Request();
/*  40 */     this.bindingRequest.setMessageType('\001');
/*     */     
/*  42 */     this.changeRequest = AttributeFactory.createChangeRequestAttribute(false, false);
/*     */     
/*  44 */     this.bindingRequest.addAttribute((Attribute)this.changeRequest);
/*  45 */     this.bindingRequest.setTransactionID(MsgFixture.TRANSACTION_ID);
/*     */ 
/*     */     
/*  48 */     this.bindingResponse = new Response();
/*  49 */     this.bindingResponse.setMessageType('ā');
/*     */     
/*  51 */     this.mappedAddress = AttributeFactory.createMappedAddressAttribute(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */     
/*  54 */     this.bindingResponse.addAttribute((Attribute)this.mappedAddress);
/*     */     
/*  56 */     this.sourceAddress = AttributeFactory.createSourceAddressAttribute(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_2, 3478));
/*     */ 
/*     */     
/*  59 */     this.bindingResponse.addAttribute((Attribute)this.sourceAddress);
/*     */     
/*  61 */     this.changedAddress = AttributeFactory.createChangedAddressAttribute(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_3, 3479));
/*     */ 
/*     */     
/*  64 */     this.bindingResponse.addAttribute((Attribute)this.changedAddress);
/*  65 */     this.bindingResponse.setTransactionID(MsgFixture.TRANSACTION_ID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  72 */     this.bindingRequest = null;
/*  73 */     this.bindingResponse = null;
/*  74 */     this.bindingErrorResponse = null;
/*  75 */     this.mappedAddress = null;
/*  76 */     this.sourceAddress = null;
/*  77 */     this.changedAddress = null;
/*  78 */     this.changeRequest = null;
/*  79 */     this.errorCodeAttribute = null;
/*  80 */     this.unknownAttributesAttribute = null;
/*  81 */     this.changeRequest = null;
/*     */     
/*  83 */     this.msgFixture.tearDown();
/*     */     
/*  85 */     this.msgFixture = null;
/*  86 */     super.tearDown();
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
/*     */   public void testAddAndGetAttribute() throws StunException {
/*  99 */     Response message = new Response();
/* 100 */     message.setMessageType('ā');
/* 101 */     message.addAttribute((Attribute)this.mappedAddress);
/*     */     
/* 103 */     Attribute getResult = null;
/*     */ 
/*     */     
/* 106 */     getResult = message.getAttribute(this.mappedAddress.getAttributeType());
/* 107 */     assertEquals("Originally added attribute did not match the one retrned by getAttribute()", this.mappedAddress, getResult);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     message.addAttribute((Attribute)this.sourceAddress);
/*     */     
/* 115 */     getResult = message.getAttribute(this.sourceAddress.getAttributeType());
/*     */ 
/*     */     
/* 118 */     assertEquals("The second attribute could not be extracted.", this.sourceAddress, getResult);
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
/*     */   public void testEncode() throws StunException {
/* 136 */     byte[] expectedReturn = this.msgFixture.bindingRequest;
/*     */     
/* 138 */     byte[] actualReturn = this.bindingRequest.encode();
/* 139 */     assertTrue("A binding request was not properly encoded", Arrays.equals(expectedReturn, actualReturn));
/*     */ 
/*     */ 
/*     */     
/* 143 */     expectedReturn = this.msgFixture.bindingResponse;
/*     */     
/* 145 */     actualReturn = this.bindingResponse.encode();
/*     */     
/* 147 */     assertTrue("A binding response was not properly encoded", Arrays.equals(expectedReturn, actualReturn));
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
/*     */   public void testDecode() throws StunException {
/* 163 */     Message expectedReturn = this.bindingRequest;
/*     */     
/* 165 */     Message actualReturn = Message.decode(this.msgFixture.bindingRequest, false, (char)this.msgFixture.bindingRequest.length);
/*     */ 
/*     */ 
/*     */     
/* 169 */     assertEquals("A binding request was not properly decoded", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 173 */     expectedReturn = this.bindingResponse;
/*     */     
/* 175 */     actualReturn = Message.decode(this.msgFixture.bindingResponse, false, (char)this.msgFixture.bindingResponse.length);
/*     */ 
/*     */ 
/*     */     
/* 179 */     assertEquals("A binding response was not properly decoded", expectedReturn, actualReturn);
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
/*     */   public void testEquals() throws StunException {
/* 196 */     Object target = null;
/* 197 */     boolean expectedReturn = false;
/* 198 */     boolean actualReturn = this.bindingRequest.equals(target);
/* 199 */     assertEquals("Equals failed against a null target", expectedReturn, actualReturn);
/*     */     
/* 201 */     actualReturn = this.bindingResponse.equals(target);
/* 202 */     assertEquals("Equals failed against a null target", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 205 */     actualReturn = this.bindingRequest.equals(this.bindingResponse);
/* 206 */     assertEquals("Equals failed against a different target", expectedReturn, actualReturn);
/*     */     
/* 208 */     actualReturn = this.bindingResponse.equals(this.bindingRequest);
/* 209 */     assertEquals("Equals failed against a different target", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 212 */     expectedReturn = true;
/*     */ 
/*     */     
/* 215 */     Request binReqTarget = new Request();
/* 216 */     binReqTarget.setMessageType('\001');
/* 217 */     binReqTarget.addAttribute((Attribute)this.changeRequest);
/* 218 */     actualReturn = this.bindingRequest.equals(binReqTarget);
/* 219 */     assertEquals("Equals failed against an equal target", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 222 */     Response binResTarget = new Response();
/* 223 */     binResTarget.setMessageType('ā');
/* 224 */     binResTarget.addAttribute((Attribute)this.mappedAddress);
/* 225 */     binResTarget.addAttribute((Attribute)this.sourceAddress);
/* 226 */     binResTarget.addAttribute((Attribute)this.changedAddress);
/* 227 */     actualReturn = this.bindingResponse.equals(binResTarget);
/* 228 */     assertEquals("Equals failed against a different target", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetAttributeCount() {
/* 238 */     int expectedReturn = 1;
/* 239 */     int actualReturn = this.bindingRequest.getAttributeCount();
/* 240 */     assertEquals("getAttributeCount failed for a bindingRequest", expectedReturn, actualReturn);
/*     */     
/* 242 */     expectedReturn = 3;
/* 243 */     actualReturn = this.bindingResponse.getAttributeCount();
/* 244 */     assertEquals("getAttributeCount failed for a bindingRequest", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testRemoveAttribute() {
/* 254 */     this.bindingRequest.removeAttribute(this.changeRequest.getAttributeType());
/*     */     
/* 256 */     assertNull("An attribute was still in the request after being removed", this.bindingRequest.getAttribute(this.changeRequest.getAttributeType()));
/*     */ 
/*     */ 
/*     */     
/* 260 */     int expectedReturn = 0;
/* 261 */     int actualReturn = this.bindingRequest.getAttributeCount();
/* 262 */     assertEquals("Attribute count did not change after removing an attribute", expectedReturn, actualReturn);
/*     */   }
/*     */ }

