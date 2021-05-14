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
/*     */ public class ChangeRequestAttributeTest
/*     */   extends TestCase
/*     */ {
/*  14 */   private ChangeRequestAttribute changeRequestAttribute = null;
/*     */   private MsgFixture binMessagesFixture;
/*     */   
/*     */   public ChangeRequestAttributeTest(String name) {
/*  18 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  22 */     super.setUp();
/*  23 */     this.changeRequestAttribute = new ChangeRequestAttribute();
/*  24 */     this.binMessagesFixture = new MsgFixture();
/*     */     
/*  26 */     this.binMessagesFixture.setUp();
/*     */   }
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  30 */     this.changeRequestAttribute = null;
/*  31 */     this.binMessagesFixture.tearDown();
/*     */     
/*  33 */     this.binMessagesFixture = null;
/*  34 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testChangeRequestAttribute() {
/*  41 */     this.changeRequestAttribute = new ChangeRequestAttribute();
/*     */     
/*  43 */     assertEquals("ChangeRequestAttribute did not construct an attribute with the correct type.", this.changeRequestAttribute.getAttributeType(), '\003');
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
/*     */   public void testDecodeAttributeBody() throws StunException {
/*  59 */     byte[] attributeValue = this.binMessagesFixture.chngReqTestValue1;
/*  60 */     char offset = '\004';
/*  61 */     char length = (char)(attributeValue.length - offset);
/*  62 */     this.changeRequestAttribute.decodeAttributeBody(attributeValue, offset, length);
/*     */     
/*  64 */     assertEquals("decodeAttributeBody() did not properly decode the changeIpFlag", false, this.changeRequestAttribute.getChangeIpFlag());
/*     */ 
/*     */ 
/*     */     
/*  68 */     assertEquals("decodeAttributeBody() did not properly decode the changePortFlag", false, this.changeRequestAttribute.getChangePortFlag());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     attributeValue = this.binMessagesFixture.chngReqTestValue2;
/*  75 */     this.changeRequestAttribute.decodeAttributeBody(attributeValue, offset, length);
/*  76 */     assertEquals("decodeAttributeBody() did not properly decode the changeIpFlag", true, this.changeRequestAttribute.getChangeIpFlag());
/*     */ 
/*     */ 
/*     */     
/*  80 */     assertEquals("decodeAttributeBody() did not properly decode the changePortFlag", true, this.changeRequestAttribute.getChangePortFlag());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.changeRequestAttribute.getChangePortFlag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEncode() {
/*  93 */     byte[] expectedReturn = this.binMessagesFixture.chngReqTestValue1;
/*     */     
/*  95 */     this.changeRequestAttribute = new ChangeRequestAttribute();
/*     */     
/*  97 */     this.changeRequestAttribute.setChangeIpFlag(false);
/*  98 */     this.changeRequestAttribute.setChangePortFlag(false);
/*     */     
/* 100 */     byte[] actualReturn = this.changeRequestAttribute.encode();
/* 101 */     assertTrue("Object did not encode properly.", Arrays.equals(expectedReturn, actualReturn));
/*     */ 
/*     */ 
/*     */     
/* 105 */     expectedReturn = this.binMessagesFixture.chngReqTestValue2;
/* 106 */     this.changeRequestAttribute = new ChangeRequestAttribute();
/*     */     
/* 108 */     this.changeRequestAttribute.setChangeIpFlag(true);
/* 109 */     this.changeRequestAttribute.setChangePortFlag(true);
/*     */     
/* 111 */     actualReturn = this.changeRequestAttribute.encode();
/* 112 */     assertTrue("Object did not encode properly.", Arrays.equals(expectedReturn, actualReturn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEquals() {
/* 123 */     ChangeRequestAttribute target = null;
/* 124 */     boolean expectedReturn = false;
/*     */ 
/*     */     
/* 127 */     boolean actualReturn = this.changeRequestAttribute.equals(target);
/* 128 */     assertEquals("Null value test failed", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 131 */     target = new ChangeRequestAttribute();
/*     */     
/* 133 */     this.changeRequestAttribute.setChangeIpFlag(true);
/* 134 */     this.changeRequestAttribute.setChangePortFlag(false);
/*     */     
/* 136 */     target.setChangeIpFlag(false);
/* 137 */     target.setChangePortFlag(true);
/*     */     
/* 139 */     actualReturn = this.changeRequestAttribute.equals(target);
/* 140 */     assertEquals("Test against a different value failed", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 144 */     target = new ChangeRequestAttribute();
/*     */     
/* 146 */     this.changeRequestAttribute.setChangeIpFlag(true);
/* 147 */     this.changeRequestAttribute.setChangePortFlag(false);
/*     */     
/* 149 */     target.setChangeIpFlag(true);
/* 150 */     target.setChangePortFlag(false);
/*     */     
/* 152 */     expectedReturn = true;
/* 153 */     actualReturn = this.changeRequestAttribute.equals(target);
/* 154 */     assertEquals("Test against an equals value failed", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetDataLength() {
/* 163 */     char expectedReturn = '\004';
/* 164 */     char actualReturn = this.changeRequestAttribute.getDataLength();
/* 165 */     assertEquals("data length returned an invalid value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetName() {
/* 173 */     String expectedReturn = "CHANGE-REQUEST";
/* 174 */     String actualReturn = this.changeRequestAttribute.getName();
/* 175 */     assertEquals("Invalid name", expectedReturn, actualReturn);
/*     */   }
/*     */ }

