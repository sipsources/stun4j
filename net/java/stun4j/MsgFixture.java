/*     */ package net.java.stun4j;
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
/*     */ public class MsgFixture
/*     */ {
/*     */   public static final char ADDRESS_ATTRIBUTE_PORT = 'ݰ';
/*  16 */   public static final byte[] ADDRESS_ATTRIBUTE_ADDRESS = new byte[] { -126, 79, -100, -119 };
/*  17 */   public static final byte[] ADDRESS_ATTRIBUTE_ADDRESS_V6 = new byte[] { 32, 1, 6, 96, 71, 17, 17, 1, 2, 48, 5, -1, -2, 26, Byte.MIN_VALUE, 95 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  23 */   public byte[] mappedAddress = new byte[] { 0, 1, 0, 8, 0, 1, 7, 112, -126, 79, -100, -119 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   public byte[] mappedAddressv6 = new byte[] { 0, 1, 0, 20, 0, 2, 7, 112, 32, 1, 6, 96, 71, 17, 17, 1, 2, 48, 5, -1, -2, 26, Byte.MIN_VALUE, 95 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public byte[] unknownOptionalAttribute = new byte[] { Byte.MIN_VALUE, 56, 0, 16, -126, 79, -100, -119, 0, 1, 7, 112, 0, 1, 7, 112, -126, 79, -100, -119 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public char optionalAttributeType = '耸';
/*     */   public static final char ADDRESS_ATTRIBUTE_PORT_2 = 'ඖ';
/*  47 */   public static final byte[] ADDRESS_ATTRIBUTE_ADDRESS_2 = new byte[] { 69, 0, -48, 27 };
/*  48 */   public static final byte[] ADDRESS_ATTRIBUTE_ADDRESS_2_V6 = new byte[] { 32, 1, 6, 96, 71, 17, 17, 1, 2, 48, 85, -1, -2, 26, -126, 95 };
/*     */ 
/*     */ 
/*     */   
/*     */   public static final char ADDRESS_ATTRIBUTE_PORT_3 = '඗';
/*     */ 
/*     */   
/*  55 */   public static final byte[] ADDRESS_ATTRIBUTE_ADDRESS_3 = new byte[] { 69, 0, -47, 22 };
/*  56 */   public static final byte[] ADDRESS_ATTRIBUTE_ADDRESS_3_V6 = new byte[] { 32, 1, 6, 96, 71, 17, 17, 1, 2, 48, 5, -1, -2, 26, -121, 94 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public byte[] sourceAddress = new byte[] { 0, 4, 0, 8, 0, 1, 13, -106, 69, 0, -48, 27 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public byte[] changedAddress = new byte[] { 0, 5, 0, 8, 0, 1, 13, -105, 69, 0, -47, 22 };
/*     */ 
/*     */   
/*     */   public static final char UNKNOWN_ATTRIBUTES_1ST_ATT = ' ';
/*     */ 
/*     */   
/*     */   public static final char UNKNOWN_ATTRIBUTES_2ND_ATT = '!';
/*     */ 
/*     */   
/*     */   public static final char UNKNOWN_ATTRIBUTES_3D_ATT = '"';
/*     */ 
/*     */   
/*     */   public static final char UNKNOWN_ATTRIBUTES_CNT_DEC_TST = '\003';
/*     */   
/*  81 */   public byte[] unknownAttsDecodeTestValue = new byte[] { 0, 10, 0, 8, 0, 32, 0, 33, 0, 34, 0, 34 };
/*     */ 
/*     */ 
/*     */   
/*     */   public static final char UNKNOWN_ATTRIBUTES_CNT_ENC_TST = '\002';
/*     */ 
/*     */   
/*  88 */   public byte[] unknownAttsEncodeExpectedResult = new byte[] { 0, 10, 0, 4, 0, 32, 0, 33 };
/*     */ 
/*     */   
/*     */   public static final boolean CHANGE_IP_FLAG_1 = false;
/*     */   
/*     */   public static final boolean CHANGE_PORT_FLAG_1 = false;
/*     */   
/*  95 */   public byte[] chngReqTestValue1 = new byte[] { 0, 3, 0, 4, 0, 0, 0, 0 };
/*     */   
/*     */   public static final boolean CHANGE_IP_FLAG_2 = true;
/*     */   
/*     */   public static final boolean CHANGE_PORT_FLAG_2 = true;
/*     */   
/* 101 */   public byte[] chngReqTestValue2 = new byte[] { 0, 3, 0, 4, 0, 0, 0, 6 };
/*     */ 
/*     */   
/*     */   public static final byte ERROR_CLASS = 4;
/*     */   
/*     */   public static final byte ERROR_NUMBER = 20;
/*     */   
/*     */   public static final char ERROR_CODE = 'Ƥ';
/*     */   
/*     */   public static final String REASON_PHRASE = "Test error reason phrase.";
/*     */   
/* 112 */   public byte[] errCodeTestValue = new byte[] { 0, 9, 0, 56, 0, 0, 4, 20, 0, 84, 0, 101, 0, 115, 0, 116, 0, 32, 0, 101, 0, 114, 0, 114, 0, 111, 0, 114, 0, 32, 0, 114, 0, 101, 0, 97, 0, 115, 0, 111, 0, 110, 0, 32, 0, 112, 0, 104, 0, 114, 0, 97, 0, 115, 0, 101, 0, 46, 0, 32 };
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final byte[] TRANSACTION_ID = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public byte[] bindingRequest = new byte[] { 0, 1, 0, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 3, 0, 4, 0, 0, 0, 0 };
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
/* 151 */   public byte[] bindingResponse = new byte[] { 1, 1, 0, 36, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 1, 0, 8, 0, 1, 7, 112, -126, 79, -100, -119, 0, 4, 0, 8, 0, 1, 13, -106, 69, 0, -48, 27, 0, 5, 0, 8, 0, 1, 13, -105, 69, 0, -47, 22 };
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
/* 167 */   public byte[] bindingErrorResponse = new byte[] { 1, 17, 0, 36, 1, 74, 84, 50, 10, 119, 111, 100, 88, 4, -82, 70, -123, 25, -52, 60, 0, 9, 0, 56, 0, 0, 4, 20, 0, 84, 0, 101, 0, 115, 0, 116, 0, 32, 0, 101, 0, 114, 0, 114, 0, 111, 0, 114, 0, 32, 0, 114, 0, 101, 0, 97, 0, 115, 0, 111, 0, 110, 0, 32, 0, 112, 0, 104, 0, 114, 0, 97, 0, 115, 0, 101, 0, 46, 0, 32, 0, 10, 0, 8, 0, 32, 0, 33, 0, 34, 0, 34 };
/*     */   
/*     */   public void setUp() {}
/*     */   
/*     */   public void tearDown() {}
/*     */ }
