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
/*     */ public class OptionalAttributeAttributeTest
/*     */   extends TestCase
/*     */ {
/*  19 */   private OptionalAttribute optionalAttribute = null;
/*  20 */   private MsgFixture msgFixture = null;
/*  21 */   byte[] expectedAttributeValue = null;
/*     */ 
/*     */   
/*     */   protected void setUp() throws Exception {
/*  25 */     super.setUp();
/*     */     
/*  27 */     this.msgFixture = new MsgFixture();
/*  28 */     int offset = 4;
/*     */ 
/*     */     
/*  31 */     this.expectedAttributeValue = new byte[this.msgFixture.unknownOptionalAttribute.length - offset];
/*     */ 
/*     */     
/*  34 */     System.arraycopy(this.msgFixture.unknownOptionalAttribute, offset, this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */ 
/*     */ 
/*     */     
/*  38 */     this.optionalAttribute = new OptionalAttribute(this.msgFixture.optionalAttributeType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  44 */     this.optionalAttribute = null;
/*  45 */     this.expectedAttributeValue = null;
/*  46 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeAttributeBody() throws StunException {
/*  57 */     char offset = '\004';
/*  58 */     char length = (char)(this.msgFixture.unknownOptionalAttribute.length - offset);
/*     */     
/*  60 */     this.optionalAttribute.decodeAttributeBody(this.msgFixture.unknownOptionalAttribute, offset, length);
/*     */ 
/*     */ 
/*     */     
/*  64 */     assertTrue("OptionalAttribute did not decode properly.", Arrays.equals(this.expectedAttributeValue, this.optionalAttribute.getBody()));
/*     */ 
/*     */ 
/*     */     
/*  68 */     assertEquals("Lenght was not properly decoded", length, this.optionalAttribute.getDataLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEncode() {
/*  78 */     this.optionalAttribute.setBody(this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */ 
/*     */     
/*  81 */     byte[] actualReturn = this.optionalAttribute.encode();
/*     */     
/*  83 */     assertTrue("encode failed", Arrays.equals(this.msgFixture.unknownOptionalAttribute, actualReturn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEquals() {
/*  93 */     Object obj = null;
/*  94 */     boolean expectedReturn = false;
/*  95 */     this.optionalAttribute.setBody(this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */ 
/*     */     
/*  98 */     boolean actualReturn = this.optionalAttribute.equals(obj);
/*  99 */     assertEquals("failed null comparison", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 102 */     obj = new String("hehe :)");
/* 103 */     actualReturn = this.optionalAttribute.equals(obj);
/* 104 */     assertEquals("failed wrong type comparison", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 108 */     obj = new OptionalAttribute(this.msgFixture.optionalAttributeType);
/*     */     
/* 110 */     ((OptionalAttribute)obj).setBody(this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */     
/* 112 */     expectedReturn = true;
/* 113 */     actualReturn = this.optionalAttribute.equals(obj);
/* 114 */     assertEquals("failed null comparison", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void testGetDataLength() {
/* 119 */     char expectedReturn = (char)this.expectedAttributeValue.length;
/*     */     
/* 121 */     this.optionalAttribute.setBody(this.expectedAttributeValue, 0, this.expectedAttributeValue.length);
/*     */ 
/*     */     
/* 124 */     char actualReturn = this.optionalAttribute.getDataLength();
/* 125 */     assertEquals("return value", expectedReturn, actualReturn);
/*     */   }
/*     */ }

