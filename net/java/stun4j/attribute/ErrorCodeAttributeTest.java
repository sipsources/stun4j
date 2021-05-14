/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.MsgFixture;
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
/*     */ public class ErrorCodeAttributeTest
/*     */   extends TestCase
/*     */ {
/*  25 */   private ErrorCodeAttribute errorCodeAttribute = null;
/*     */   private MsgFixture msgFixture;
/*     */   
/*     */   public ErrorCodeAttributeTest(String name) {
/*  29 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  33 */     super.setUp();
/*  34 */     this.errorCodeAttribute = new ErrorCodeAttribute();
/*  35 */     this.msgFixture = new MsgFixture();
/*     */     
/*  37 */     this.msgFixture.setUp();
/*     */   }
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  41 */     this.errorCodeAttribute = null;
/*  42 */     this.msgFixture.tearDown();
/*     */     
/*  44 */     this.msgFixture = null;
/*  45 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testErrorCodeAttribute() {
/*  54 */     this.errorCodeAttribute = new ErrorCodeAttribute();
/*     */     
/*  56 */     assertEquals("ErrorCodeAttribute() constructed an attribute with an invalid type", '\t', this.errorCodeAttribute.getAttributeType());
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
/*     */   public void testDecodeAttributeBody() throws StunException {
/*  68 */     byte[] attributeValue = this.msgFixture.errCodeTestValue;
/*  69 */     char offset = '\004';
/*  70 */     char length = (char)(attributeValue.length - 4);
/*  71 */     this.errorCodeAttribute.decodeAttributeBody(attributeValue, offset, length);
/*     */     
/*  73 */     assertEquals("Error Class was not correctly decoded", (byte)4, this.errorCodeAttribute.getErrorClass());
/*     */ 
/*     */ 
/*     */     
/*  77 */     assertEquals("Error Number was not correctly decoded", (byte)20, this.errorCodeAttribute.getErrorNumber());
/*     */ 
/*     */ 
/*     */     
/*  81 */     assertEquals("Reason phrase was not correctly decoded", "Test error reason phrase.".trim(), this.errorCodeAttribute.getReasonPhrase().trim());
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
/*     */   public void testEncode() throws StunException {
/*  96 */     byte[] expectedReturn = this.msgFixture.errCodeTestValue;
/*     */     
/*  98 */     this.errorCodeAttribute.setErrorClass((byte)4);
/*  99 */     this.errorCodeAttribute.setErrorNumber((byte)20);
/*     */     
/* 101 */     this.errorCodeAttribute.setReasonPhrase("Test error reason phrase.");
/*     */     
/* 103 */     byte[] actualReturn = this.errorCodeAttribute.encode();
/* 104 */     assertTrue("encode() did not return the expected binary array.", Arrays.equals(expectedReturn, actualReturn));
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
/*     */   public void testEquals() throws StunException {
/* 119 */     ErrorCodeAttribute target = null;
/* 120 */     boolean expectedReturn = false;
/* 121 */     boolean actualReturn = this.errorCodeAttribute.equals(target);
/* 122 */     assertEquals("equals() failed against a null value target.", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 126 */     target = new ErrorCodeAttribute();
/* 127 */     expectedReturn = false;
/*     */     
/* 129 */     target.setErrorClass((byte)4);
/* 130 */     target.setErrorNumber((byte)20);
/*     */     
/* 132 */     this.errorCodeAttribute.setErrorClass((byte)(4 + 1));
/* 133 */     this.errorCodeAttribute.setErrorNumber((byte)(20 + 1));
/*     */     
/* 135 */     actualReturn = this.errorCodeAttribute.equals(target);
/* 136 */     assertEquals("equals() failed against a not equal target.", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 140 */     target = new ErrorCodeAttribute();
/* 141 */     this.errorCodeAttribute = new ErrorCodeAttribute();
/* 142 */     expectedReturn = true;
/*     */     
/* 144 */     target.setErrorClass((byte)4);
/* 145 */     target.setErrorNumber((byte)20);
/*     */     
/* 147 */     this.errorCodeAttribute.setErrorClass((byte)4);
/* 148 */     this.errorCodeAttribute.setErrorNumber((byte)20);
/*     */     
/* 150 */     actualReturn = this.errorCodeAttribute.equals(target);
/* 151 */     assertEquals("equals() failed against a not equal target.", expectedReturn, actualReturn);
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
/*     */   public void testGetDataLength() throws StunException {
/* 165 */     char expectedReturn = (char)(this.msgFixture.errCodeTestValue.length - 4);
/*     */ 
/*     */ 
/*     */     
/* 169 */     this.errorCodeAttribute.setErrorClass((byte)4);
/* 170 */     this.errorCodeAttribute.setErrorNumber((byte)20);
/* 171 */     this.errorCodeAttribute.setReasonPhrase("Test error reason phrase.");
/*     */     
/* 173 */     char actualReturn = this.errorCodeAttribute.getDataLength();
/* 174 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetErrorCode() throws StunException {
/* 185 */     char expectedReturn = (char)(100 * 4 + 20);
/*     */ 
/*     */     
/* 188 */     this.errorCodeAttribute.setErrorClass((byte)4);
/* 189 */     this.errorCodeAttribute.setErrorNumber((byte)20);
/*     */     
/* 191 */     char actualReturn = this.errorCodeAttribute.getErrorCode();
/* 192 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetName() {
/* 199 */     String expectedReturn = "ERROR-CODE";
/* 200 */     String actualReturn = this.errorCodeAttribute.getName();
/* 201 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testSetErrorCode() throws StunException {
/* 211 */     char errorCode = (char)(4 * 100 + 20);
/* 212 */     this.errorCodeAttribute.setErrorCode(errorCode);
/*     */     
/* 214 */     assertEquals("An error class was not properly set after decoding an error code.", 4, this.errorCodeAttribute.getErrorClass());
/*     */ 
/*     */     
/* 217 */     assertEquals("An error number was not properly set after decoding an error code.", 20, this.errorCodeAttribute.getErrorNumber());
/*     */   }
/*     */ }
