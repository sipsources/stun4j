/*    */ package net.java.stun4j.attribute;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import junit.framework.TestCase;
/*    */ import net.java.stun4j.MsgFixture;
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
/*    */ public class XorOnlyTest
/*    */   extends TestCase
/*    */ {
/* 19 */   private XorOnlyAttribute xorOnly = null;
/* 20 */   private MsgFixture msgFixture = null;
/*    */ 
/*    */   
/*    */   protected void setUp() throws Exception {
/* 24 */     super.setUp();
/* 25 */     this.xorOnly = new XorOnlyAttribute();
/* 26 */     this.msgFixture = new MsgFixture();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tearDown() throws Exception {
/* 31 */     this.xorOnly = null;
/* 32 */     this.msgFixture = null;
/* 33 */     super.tearDown();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void testDecodeAttributeBody() throws StunException {
/* 43 */     byte[] attributeValue = new byte[0];
/* 44 */     char offset = Character.MIN_VALUE;
/* 45 */     char length = Character.MIN_VALUE;
/* 46 */     this.xorOnly.decodeAttributeBody(attributeValue, offset, length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void testEncode() {
/* 54 */     byte[] expectedReturn = { 0, 33, 0, 0 };
/*    */ 
/*    */     
/* 57 */     byte[] actualReturn = this.xorOnly.encode();
/* 58 */     assertTrue("XorOnly failed to encode", Arrays.equals(expectedReturn, actualReturn));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void testEquals() throws Exception {
/* 68 */     XorOnlyAttribute xor2 = new XorOnlyAttribute();
/* 69 */     assertEquals("equals() failes for XorOnly", this.xorOnly, xor2);
/*    */     
/* 71 */     MappedAddressAttribute maatt = new MappedAddressAttribute();
/* 72 */     maatt.decodeAttributeBody(this.msgFixture.mappedAddress, false, (char)this.msgFixture.mappedAddress.length);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     assertFalse("equals failed to see a difference", this.xorOnly.equals(maatt));
/* 78 */     assertFalse("equals failed for null", this.xorOnly.equals(null));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void testGetDataLength() {
/* 86 */     char expectedReturn = Character.MIN_VALUE;
/* 87 */     char actualReturn = this.xorOnly.getDataLength();
/* 88 */     assertEquals("data length was not 0", expectedReturn, actualReturn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void testGetName() {
/* 96 */     String expectedReturn = "XOR-ONLY";
/* 97 */     String actualReturn = this.xorOnly.getName();
/* 98 */     assertEquals("Is name correct", expectedReturn, actualReturn);
/*    */   }
/*    */ }

