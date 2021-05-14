/*     */ package net.java.stun4j.attribute;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddressAttributeTest
/*     */   extends TestCase
/*     */ {
/*  24 */   private AddressAttribute addressAttribute = null;
/*     */   private MsgFixture msgFixture;
/*     */   
/*     */   public AddressAttributeTest(String name) {
/*  28 */     super(name);
/*     */   }
/*     */   
/*     */   protected void setUp() throws Exception {
/*  32 */     super.setUp();
/*     */     
/*  34 */     this.addressAttribute = new MappedAddressAttribute();
/*  35 */     this.msgFixture = new MsgFixture();
/*     */     
/*  37 */     this.msgFixture.setUp();
/*     */   }
/*     */   
/*     */   protected void tearDown() throws Exception {
/*  41 */     this.addressAttribute = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testAddressAttributeDescendants() {
/*  59 */     this.addressAttribute = new MappedAddressAttribute();
/*     */     
/*  61 */     char expectedType = '\001';
/*  62 */     char actualType = this.addressAttribute.getAttributeType();
/*     */     
/*  64 */     String expectedName = "MAPPED-ADDRESS";
/*  65 */     String actualName = this.addressAttribute.getName();
/*     */     
/*  67 */     assertEquals("MappedAddressAttribute does not the right type.", expectedType, actualType);
/*     */     
/*  69 */     assertEquals("MappedAddressAttribute does not the right name.", expectedName, actualName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.addressAttribute = new SourceAddressAttribute();
/*     */     
/*  76 */     expectedType = '\004';
/*  77 */     actualType = this.addressAttribute.getAttributeType();
/*     */     
/*  79 */     expectedName = "SOURCE-ADDRESS";
/*  80 */     actualName = this.addressAttribute.getName();
/*     */     
/*  82 */     assertEquals("SourceAddressAttribute does not the right type.", expectedType, actualType);
/*     */     
/*  84 */     assertEquals("SourceAddressAttribute does not the right name.", expectedName, actualName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.addressAttribute = new ChangedAddressAttribute();
/*     */     
/*  91 */     expectedType = '\005';
/*  92 */     actualType = this.addressAttribute.getAttributeType();
/*     */     
/*  94 */     expectedName = "CHANGED-ADDRESS";
/*  95 */     actualName = this.addressAttribute.getName();
/*     */     
/*  97 */     assertEquals("ChangedAddressAttribute does not the right type.", expectedType, actualType);
/*     */     
/*  99 */     assertEquals("ChangedAddressAttribute does not the right name.", expectedName, actualName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.addressAttribute = new ResponseAddressAttribute();
/*     */     
/* 106 */     expectedType = '\002';
/* 107 */     actualType = this.addressAttribute.getAttributeType();
/*     */     
/* 109 */     expectedName = "RESPONSE-ADDRESS";
/* 110 */     actualName = this.addressAttribute.getName();
/*     */     
/* 112 */     assertEquals("ResponseAddressAttribute does not the right type.", expectedType, actualType);
/*     */     
/* 114 */     assertEquals("ResponseAddressAttribute does not the right name.", expectedName, actualName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.addressAttribute = new ReflectedFromAttribute();
/*     */     
/* 121 */     expectedType = '\013';
/* 122 */     actualType = this.addressAttribute.getAttributeType();
/*     */     
/* 124 */     expectedName = "REFLECTED-FROM";
/* 125 */     actualName = this.addressAttribute.getName();
/*     */     
/* 127 */     assertEquals("ReflectedFromAttribute does not the right type.", expectedType, actualType);
/*     */     
/* 129 */     assertEquals("ReflectedFromAttribute does not the right name.", expectedName, actualName);
/*     */ 
/*     */ 
/*     */     
/* 133 */     this.addressAttribute = new ReflectedFromAttribute();
/*     */     
/* 135 */     expectedType = '\013';
/* 136 */     actualType = this.addressAttribute.getAttributeType();
/*     */     
/* 138 */     expectedName = "REFLECTED-FROM";
/* 139 */     actualName = this.addressAttribute.getName();
/*     */     
/* 141 */     assertEquals("ReflectedFromAttribute does not the right type.", expectedType, actualType);
/*     */     
/* 143 */     assertEquals("ReflectedFromAttribute does not the right name.", expectedName, actualName);
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.addressAttribute = new XorMappedAddressAttribute();
/*     */     
/* 149 */     expectedType = '耠';
/* 150 */     actualType = this.addressAttribute.getAttributeType();
/*     */     
/* 152 */     expectedName = "XOR-MAPPED-ADDRESS";
/* 153 */     actualName = this.addressAttribute.getName();
/*     */     
/* 155 */     assertEquals("XorMappedAddressAttribute does not the right type.", expectedType, actualType);
/*     */     
/* 157 */     assertEquals("XorMappedAddressAttribute does not the right name.", expectedName, actualName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testXorMappedAddressXoring_v4() {
/* 166 */     XorMappedAddressAttribute addressAttribute = new XorMappedAddressAttribute();
/* 167 */     StunAddress testAddress = new StunAddress("130.79.95.53", 12120);
/*     */ 
/*     */     
/* 170 */     addressAttribute.setAddress(testAddress);
/*     */ 
/*     */     
/* 173 */     StunAddress xorredAddr = addressAttribute.applyXor(new byte[] { -126, 79, 95, 53, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
/*     */ 
/*     */     
/* 176 */     assertTrue("Xorring the address with itself didn't return 00000...", Arrays.equals(xorredAddr.getAddressBytes(), new byte[] { 0, 0, 0, 0 }));
/*     */ 
/*     */     
/* 179 */     assertTrue("Port was not xorred", (testAddress.getPort() != xorredAddr.getPort()));
/*     */ 
/*     */ 
/*     */     
/* 183 */     addressAttribute.setAddress(testAddress);
/* 184 */     xorredAddr = addressAttribute.applyXor(new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
/*     */ 
/*     */     
/* 187 */     xorredAddr = addressAttribute.applyXor(xorredAddr.getAddressBytes());
/*     */ 
/*     */     
/* 190 */     assertTrue("Xorring the original with the xor-ed didn't return the code..", Arrays.equals(xorredAddr.getAddressBytes(), new byte[] { 21, 22, 23, 24 }));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     assertTrue("Port was not xorred", (testAddress.getPort() != Character.MAX_VALUE));
/*     */ 
/*     */ 
/*     */     
/* 199 */     addressAttribute.setAddress(testAddress);
/* 200 */     xorredAddr = addressAttribute.applyXor(new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
/*     */ 
/*     */     
/* 203 */     addressAttribute.setAddress(xorredAddr);
/* 204 */     xorredAddr = addressAttribute.applyXor(new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
/*     */ 
/*     */     
/* 207 */     assertEquals("Double xorring didn't give the original ...", testAddress, xorredAddr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testXorMappedAddressXoring_v6() {
/* 216 */     XorMappedAddressAttribute addressAttribute = new XorMappedAddressAttribute();
/* 217 */     StunAddress testAddress = new StunAddress("2001:660:4701:1001:202:8aff:febe:130b", 12120);
/*     */ 
/*     */     
/* 220 */     addressAttribute.setAddress(testAddress);
/*     */ 
/*     */     
/* 223 */     StunAddress xorredAddr = addressAttribute.applyXor(new byte[] { 32, 1, 6, 96, 71, 1, 16, 1, 2, 2, -118, -1, -2, -66, 19, 11 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     assertTrue("Xorring the address with itself didn't return 00000...", Arrays.equals(xorredAddr.getAddressBytes(), new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     assertTrue("Port was not xorred", (testAddress.getPort() != xorredAddr.getPort()));
/*     */ 
/*     */ 
/*     */     
/* 241 */     addressAttribute.setAddress(testAddress);
/* 242 */     xorredAddr = addressAttribute.applyXor(new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
/*     */ 
/*     */     
/* 245 */     xorredAddr = addressAttribute.applyXor(xorredAddr.getAddressBytes());
/*     */ 
/*     */     
/* 248 */     assertTrue("Xorring the original with the xor-ed didn't return the code..", Arrays.equals(xorredAddr.getAddressBytes(), new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 }));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     assertTrue("Port was not xorred", (testAddress.getPort() != Character.MAX_VALUE));
/*     */ 
/*     */ 
/*     */     
/* 257 */     addressAttribute.setAddress(testAddress);
/* 258 */     xorredAddr = addressAttribute.applyXor(new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
/*     */ 
/*     */     
/* 261 */     addressAttribute.setAddress(xorredAddr);
/* 262 */     xorredAddr = addressAttribute.applyXor(new byte[] { 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
/*     */ 
/*     */     
/* 265 */     assertEquals("Double xorring didn't give the original ...", testAddress, xorredAddr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testDecodeAttributeBody() throws StunException {
/* 274 */     byte[] attributeValue = this.msgFixture.mappedAddress;
/* 275 */     char offset = '\004';
/* 276 */     char length = (char)(attributeValue.length - offset);
/*     */     
/* 278 */     this.addressAttribute.decodeAttributeBody(attributeValue, offset, length);
/*     */ 
/*     */     
/* 281 */     assertEquals("AddressAttribute.decode() did not properly decode the port field.", 'ݰ', this.addressAttribute.getPort());
/*     */ 
/*     */     
/* 284 */     assertTrue("AddressAttribute.decode() did not properly decode the address field.", Arrays.equals(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, this.addressAttribute.getAddressBytes()));
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
/*     */   public void testDecodeAttributeBodyv6() throws StunException {
/* 296 */     byte[] attributeValue = this.msgFixture.mappedAddressv6;
/* 297 */     char offset = '\004';
/* 298 */     char length = (char)(attributeValue.length - offset);
/*     */     
/* 300 */     this.addressAttribute.decodeAttributeBody(attributeValue, offset, length);
/*     */ 
/*     */     
/* 303 */     assertEquals("decode() failed for an IPv6 Addr's port.", 'ݰ', this.addressAttribute.getPort());
/*     */ 
/*     */     
/* 306 */     assertTrue("AddressAttribute.decode() failed for an IPv6 address.", Arrays.equals(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, this.addressAttribute.getAddressBytes()));
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
/* 321 */     byte[] expectedReturn = this.msgFixture.mappedAddress;
/*     */     
/* 323 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */ 
/*     */     
/* 327 */     byte[] actualReturn = this.addressAttribute.encode();
/* 328 */     assertTrue("AddressAttribute.encode() did not properly encode a sample attribute", Arrays.equals(expectedReturn, actualReturn));
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
/*     */   public void testEncodev6() throws StunException {
/* 340 */     byte[] expectedReturn = this.msgFixture.mappedAddressv6;
/*     */     
/* 342 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, 1904));
/*     */ 
/*     */ 
/*     */     
/* 346 */     byte[] actualReturn = this.addressAttribute.encode();
/* 347 */     assertTrue("An AddressAttribute did not properly encode an IPv6 addr.", Arrays.equals(expectedReturn, actualReturn));
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
/* 362 */     AddressAttribute target = null;
/* 363 */     boolean expectedReturn = false;
/* 364 */     boolean actualReturn = this.addressAttribute.equals(target);
/*     */     
/* 366 */     assertEquals("AddressAttribute.equals() failed against a null target.", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 370 */     target = new MappedAddressAttribute();
/*     */     
/* 372 */     char port = (char)(1904 + 1);
/* 373 */     target.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, port));
/*     */ 
/*     */     
/* 376 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */ 
/*     */     
/* 380 */     expectedReturn = false;
/* 381 */     actualReturn = this.addressAttribute.equals(target);
/* 382 */     assertEquals("AddressAttribute.equals() failed against a different target.", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 386 */     target.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */     
/* 389 */     expectedReturn = true;
/* 390 */     actualReturn = this.addressAttribute.equals(target);
/* 391 */     assertEquals("AddressAttribute.equals() failed against an equal target.", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 395 */     target.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, 1904));
/*     */ 
/*     */     
/* 398 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, 1904));
/*     */ 
/*     */     
/* 401 */     expectedReturn = true;
/* 402 */     actualReturn = this.addressAttribute.equals(target);
/* 403 */     assertEquals("AddressAttribute.equals() failed for IPv6 addresses.", expectedReturn, actualReturn);
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
/*     */   public void testGetDataLength() throws StunException {
/* 415 */     char expectedReturn = '\b';
/*     */     
/* 417 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */ 
/*     */     
/* 421 */     char actualReturn = this.addressAttribute.getDataLength();
/*     */     
/* 423 */     assertEquals("Datalength is not propoerly calculated", expectedReturn, actualReturn);
/*     */ 
/*     */     
/* 426 */     expectedReturn = '\024';
/* 427 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, 1904));
/*     */ 
/*     */ 
/*     */     
/* 431 */     actualReturn = this.addressAttribute.getDataLength();
/*     */     
/* 433 */     assertEquals("Datalength is not propoerly calculated", expectedReturn, actualReturn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testGetFamily() {
/* 441 */     byte expectedReturn = 1;
/* 442 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS, 1904));
/*     */ 
/*     */     
/* 445 */     byte actualReturn = this.addressAttribute.getFamily();
/* 446 */     assertEquals("Address family was not 1 for an IPv4", expectedReturn, actualReturn);
/*     */ 
/*     */ 
/*     */     
/* 450 */     expectedReturn = 2;
/* 451 */     this.addressAttribute.setAddress(new StunAddress(MsgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6, 1904));
/*     */ 
/*     */     
/* 454 */     actualReturn = this.addressAttribute.getFamily();
/* 455 */     assertEquals("Address family was not 2 for an IPv6 address", expectedReturn, actualReturn);
/*     */   }
/*     */ }

