/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import junit.framework.TestCase;
/*     */ import net.java.stun4j.MsgFixture;
/*     */ import net.java.stun4j.StunException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnknownAttributesAttributeTest
/*     */   extends TestCase
/*     */ {
/*  15 */   private UnknownAttributesAttribute unknownAttributesAttribute = null;
/*     */   private MsgFixture binMessagesFixture;
/*     */   
/*     */   public UnknownAttributesAttributeTest(String name) {
/*  19 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  23 */     super.setUp();
/*  24 */     this.unknownAttributesAttribute = new UnknownAttributesAttribute();
/*  25 */     this.binMessagesFixture = new MsgFixture();
/*     */     
/*  27 */     this.binMessagesFixture.setUp();
/*     */   }
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  31 */     this.unknownAttributesAttribute = null;
/*  32 */     this.binMessagesFixture.tearDown();
/*     */     
/*  34 */     this.binMessagesFixture = null;
/*  35 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testUnknownAttributesAttribute() {
/*  43 */     this.unknownAttributesAttribute = new UnknownAttributesAttribute();
/*     */     
/*  45 */     assertEquals("UnknownAttributesAttribute() did not properly set the Attribute's type field!", 10, this.unknownAttributesAttribute.getAttributeType());
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
/*     */   public void testAddAttributeID() {
/*  58 */     char attributeID = '"';
/*     */     
/*  60 */     this.unknownAttributesAttribute.addAttributeID(attributeID);
/*     */     
/*  62 */     assertEquals("addAttributeID does not seem to properly add the attribute ID", attributeID, this.unknownAttributesAttribute.getAttribute(0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     assertEquals("addAttributeID does not seem to properly add the attribute ID", 1, this.unknownAttributesAttribute.getAttributeCount());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.unknownAttributesAttribute.addAttributeID(attributeID);
/*     */     
/*  75 */     assertEquals("Adding a 2nd time the same attributeID should not change the number of attributes", 1, this.unknownAttributesAttribute.getAttributeCount());
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
/*     */   public void testDecodeAttributeBody() throws StunException {
/*  89 */     byte[] attributeValue = this.binMessagesFixture.unknownAttsDecodeTestValue;
/*     */     
/*  91 */     this.unknownAttributesAttribute.decodeAttributeBody(attributeValue, '\004', (char)(attributeValue.length - 4));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     assertTrue("The 32 attribute id was not found after decoding a binary array that contained it.", this.unknownAttributesAttribute.contains(' '));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     assertTrue("The 33 attribute id was not found after decoding a binary array that contained it.", this.unknownAttributesAttribute.contains('!'));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     assertTrue("The 34 attribute id was not found after decoding a binary array that contained it.", this.unknownAttributesAttribute.contains('"'));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     assertEquals("The decoded attribute contained " + this.unknownAttributesAttribute.getAttributeCount() + " attribute ids when there were only " + '\003' + " in the original binary array.", 3, this.unknownAttributesAttribute.getAttributeCount());
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
/*     */   public void testEncode() {
/* 129 */     byte[] expectedReturn = this.binMessagesFixture.unknownAttsEncodeExpectedResult;
/*     */     
/* 131 */     this.unknownAttributesAttribute.addAttributeID(' ');
/*     */     
/* 133 */     this.unknownAttributesAttribute.addAttributeID('!');
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     byte[] actualReturn = this.unknownAttributesAttribute.encode();
/* 139 */     assertTrue("UnknownAttributesAttribute did not encode properly.", Arrays.equals(actualReturn, expectedReturn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEquals() {
/* 148 */     UnknownAttributesAttribute target = new UnknownAttributesAttribute();
/*     */     
/* 150 */     boolean expectedReturn = false;
/* 151 */     boolean actualReturn = this.unknownAttributesAttribute.equals((Object)null);
/* 152 */     assertEquals("Equals failed for a null object", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 155 */     this.unknownAttributesAttribute.addAttributeID('\031');
/* 156 */     target.addAttributeID('\031');
/*     */     
/* 158 */     this.unknownAttributesAttribute.addAttributeID('\032');
/* 159 */     actualReturn = this.unknownAttributesAttribute.equals(target);
/* 160 */     assertEquals("Equals failed when comparing different objects.", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 163 */     target.addAttributeID('\032');
/* 164 */     expectedReturn = true;
/* 165 */     actualReturn = this.unknownAttributesAttribute.equals(target);
/* 166 */     assertEquals("Equals failed to recognize identical objects.", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetAttribute() {
/* 176 */     char expectedId1 = '\024';
/* 177 */     char expectedId2 = '\025';
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.unknownAttributesAttribute.addAttributeID(expectedId1);
/* 183 */     this.unknownAttributesAttribute.addAttributeID(expectedId2);
/*     */     
/* 185 */     char actualId1 = this.unknownAttributesAttribute.getAttribute(0);
/* 186 */     char actualId2 = this.unknownAttributesAttribute.getAttribute(1);
/*     */     
/* 188 */     assertEquals("getAttribute() return value mismatch", expectedId1, actualId1);
/* 189 */     assertEquals("getAttribute() return value mismatch", expectedId2, actualId2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetAttributeCount() {
/* 197 */     int expectedReturn = 5;
/*     */     
/* 199 */     this.unknownAttributesAttribute.addAttributeID('\025');
/* 200 */     this.unknownAttributesAttribute.addAttributeID('\026');
/* 201 */     this.unknownAttributesAttribute.addAttributeID('\027');
/* 202 */     this.unknownAttributesAttribute.addAttributeID('\030');
/* 203 */     this.unknownAttributesAttribute.addAttributeID('\031');
/* 204 */     this.unknownAttributesAttribute.addAttributeID('\031');
/*     */     
/* 206 */     int actualReturn = this.unknownAttributesAttribute.getAttributeCount();
/* 207 */     assertEquals("getAttributeCount did not return the expected value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetAttributes() {
/* 216 */     char expectedId1 = '\024';
/* 217 */     char expectedId2 = '\025';
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     this.unknownAttributesAttribute.addAttributeID(expectedId1);
/* 223 */     this.unknownAttributesAttribute.addAttributeID(expectedId2);
/*     */     
/* 225 */     Iterator iterator = this.unknownAttributesAttribute.getAttributes();
/*     */     
/* 227 */     char actualId1 = ((Character)iterator.next()).charValue();
/* 228 */     char actualId2 = ((Character)iterator.next()).charValue();
/*     */     
/* 230 */     assertEquals("getAttributes() return value mismatch", expectedId1, actualId1);
/* 231 */     assertEquals("getAttributes() return value mismatch", expectedId2, actualId2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetDataLength() {
/* 242 */     char expectedReturn = '\b';
/*     */     
/* 244 */     this.unknownAttributesAttribute.addAttributeID('\024');
/* 245 */     this.unknownAttributesAttribute.addAttributeID('\025');
/* 246 */     this.unknownAttributesAttribute.addAttributeID('\026');
/*     */     
/* 248 */     char actualReturn = this.unknownAttributesAttribute.getDataLength();
/* 249 */     assertEquals("Incorrect testGetDataLength() return value", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 252 */     this.unknownAttributesAttribute.addAttributeID('\027');
/*     */     
/* 254 */     actualReturn = this.unknownAttributesAttribute.getDataLength();
/* 255 */     assertEquals("Incorrect testGetDataLength() return value", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetName() {
/* 264 */     String expectedReturn = "UNKNOWN-ATTRIBUTES";
/* 265 */     String actualReturn = this.unknownAttributesAttribute.getName();
/* 266 */     assertEquals("getName() return", expectedReturn, actualReturn);
/*     */   }
/*     */ }

