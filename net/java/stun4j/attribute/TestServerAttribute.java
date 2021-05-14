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
/*     */ public class TestServerAttribute
/*     */   extends TestCase
/*     */ {
/*  20 */   private ServerAttribute serverAttribute = null;
/*  21 */   MsgFixture msgFixture = null;
/*  22 */   String serverValue = "turnserver.org";
/*  23 */   byte[] attributeBinValue = new byte[] { Byte.MIN_VALUE, 34, 0, (byte)this.serverValue.length(), 116, 117, 114, 110, 115, 101, 114, 118, 101, 114, 46, 111, 114, 103 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUp() throws Exception {
/*  31 */     super.setUp();
/*  32 */     this.msgFixture = new MsgFixture();
/*     */     
/*  34 */     this.serverAttribute = new ServerAttribute();
/*  35 */     this.serverAttribute.setServer(this.serverValue.getBytes());
/*     */     
/*  37 */     this.msgFixture.setUp();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  42 */     this.serverAttribute = null;
/*  43 */     this.msgFixture.tearDown();
/*     */     
/*  45 */     this.msgFixture = null;
/*  46 */     super.tearDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeAttributeBody() throws StunException {
/*  55 */     char offset = Character.MIN_VALUE;
/*  56 */     ServerAttribute decoded = new ServerAttribute();
/*  57 */     char length = (char)this.serverValue.length();
/*  58 */     decoded.decodeAttributeBody(this.serverValue.getBytes(), offset, length);
/*     */ 
/*     */     
/*  61 */     assertEquals("decode failed", this.serverAttribute, decoded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEncode() {
/*  69 */     assertTrue("encode failed", Arrays.equals(this.serverAttribute.encode(), this.attributeBinValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testEquals() {
/*  79 */     ServerAttribute serverAttribute2 = new ServerAttribute();
/*  80 */     serverAttribute2.setServer(this.serverValue.getBytes());
/*     */ 
/*     */     
/*  83 */     assertEquals("testequals failed", this.serverAttribute, serverAttribute2);
/*     */ 
/*     */     
/*  86 */     serverAttribute2 = new ServerAttribute();
/*  87 */     serverAttribute2.setServer("some other server".getBytes());
/*     */ 
/*     */     
/*  90 */     assertFalse("testequals failed", this.serverAttribute.equals(serverAttribute2));
/*     */ 
/*     */ 
/*     */     
/*  94 */     assertFalse("testequals failed", this.serverAttribute.equals(null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetDataLength() {
/* 103 */     char expectedReturn = (char)this.serverValue.length();
/* 104 */     char actualReturn = this.serverAttribute.getDataLength();
/* 105 */     assertEquals("getDataLength - failed", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetName() {
/* 113 */     String expectedReturn = "SERVER";
/* 114 */     String actualReturn = this.serverAttribute.getName();
/* 115 */     assertEquals("getting name failed", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void testSetGetServer() {
/* 120 */     byte[] expectedReturn = this.serverValue.getBytes();
/*     */     
/* 122 */     ServerAttribute att = new ServerAttribute();
/* 123 */     att.setServer(expectedReturn);
/*     */     
/* 125 */     byte[] actualReturn = att.getServer();
/* 126 */     assertTrue("server setter or getter failed", Arrays.equals(expectedReturn, actualReturn));
/*     */   }
/*     */ }

