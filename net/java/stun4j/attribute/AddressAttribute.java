/*     */ package net.java.stun4j.attribute;
/*     */ 
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
/*     */ abstract class AddressAttribute
/*     */   extends Attribute
/*     */ {
/*     */   static final byte ADDRESS_FAMILY_IPV4 = 1;
/*     */   static final byte ADDRESS_FAMILY_IPV6 = 2;
/*  59 */   protected StunAddress address = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char DATA_LENGTH_FOR_IPV6 = '\024';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final char DATA_LENGTH_FOR_IPV4 = '\b';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AddressAttribute(char attributeType) {
/*  80 */     super(attributeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTypeValid(char type) {
/*  90 */     return (type == '\001' || type == '\002' || type == '\004' || type == '\005' || type == '\013' || type == '耠');
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
/*     */   protected void setAttributeType(char type) {
/* 103 */     if (!isTypeValid(type)) {
/* 104 */       throw new IllegalArgumentException(type + "is not a valid address attribute!");
/*     */     }
/* 106 */     super.setAttributeType(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 117 */     switch (getAttributeType()) {
/*     */       case '\001':
/* 119 */         return "MAPPED-ADDRESS";
/* 120 */       case '\002': return "RESPONSE-ADDRESS";
/* 121 */       case '\004': return "SOURCE-ADDRESS";
/* 122 */       case '\005': return "CHANGED-ADDRESS";
/* 123 */       case '\013': return "REFLECTED-FROM";
/* 124 */       case '耠': return "XOR-MAPPED-ADDRESS";
/*     */     } 
/*     */     
/* 127 */     return "UNKNOWN ATTRIBUTE";
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
/*     */   public boolean equals(Object obj) {
/* 139 */     if (!(obj instanceof AddressAttribute) || obj == null)
/*     */     {
/* 141 */       return false;
/*     */     }
/* 143 */     if (obj == this) {
/* 144 */       return true;
/*     */     }
/* 146 */     AddressAttribute att = (AddressAttribute)obj;
/* 147 */     if (att.getAttributeType() != getAttributeType() || att.getDataLength() != getDataLength() || att.getFamily() != getFamily() || (att.getAddress() != null && !this.address.equals(att.getAddress())))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       return false;
/*     */     }
/*     */     
/* 157 */     if (att.getAddress() == null && getAddress() == null) {
/* 158 */       return true;
/*     */     }
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getDataLength() {
/* 169 */     if (getFamily() == 2) {
/* 170 */       return '\024';
/*     */     }
/* 172 */     return '\b';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode() {
/* 181 */     char type = getAttributeType();
/* 182 */     if (!isTypeValid(type))
/* 183 */       throw new IllegalStateException(type + "is not a valid address attribute!"); 
/* 184 */     byte[] binValue = new byte[4 + getDataLength()];
/*     */ 
/*     */     
/* 187 */     binValue[0] = (byte)(type >> 8);
/* 188 */     binValue[1] = (byte)(type & 0xFF);
/*     */     
/* 190 */     binValue[2] = (byte)(getDataLength() >> 8);
/* 191 */     binValue[3] = (byte)(getDataLength() & 0xFF);
/*     */     
/* 193 */     binValue[4] = 0;
/*     */     
/* 195 */     binValue[5] = getFamily();
/*     */     
/* 197 */     binValue[6] = (byte)(getPort() >> 8);
/* 198 */     binValue[7] = (byte)(getPort() & 0xFF);
/*     */ 
/*     */     
/* 201 */     if (getFamily() == 2) {
/* 202 */       System.arraycopy(getAddressBytes(), 0, binValue, 8, 16);
/*     */     } else {
/*     */       
/* 205 */       System.arraycopy(getAddressBytes(), 0, binValue, 8, 4);
/*     */     } 
/*     */     
/* 208 */     return binValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAddress(StunAddress address) {
/* 217 */     this.address = address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StunAddress getAddress() {
/* 226 */     return this.address;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getAddressBytes() {
/* 231 */     return this.address.getAddressBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getFamily() {
/* 240 */     if (this.address.getSocketAddress().getAddress() instanceof java.net.Inet6Address) {
/* 241 */       return 2;
/*     */     }
/* 243 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getPort() {
/* 253 */     return this.address.getPort();
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
/*     */   void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws StunException {
/* 271 */     offset = (char)(offset + 1);
/*     */ 
/*     */     
/* 274 */     offset = (char)(offset + 1); byte family = attributeValue[offset];
/*     */ 
/*     */     
/* 277 */     offset = (char)(offset + 1); offset = (char)(offset + 1); char port = (char)(attributeValue[offset] << 8 | attributeValue[offset] & 0xFF);
/*     */ 
/*     */     
/* 280 */     byte[] address = null;
/* 281 */     if (family == 2) {
/* 282 */       address = new byte[16];
/*     */     }
/*     */     else {
/*     */       
/* 286 */       address = new byte[4];
/*     */     } 
/*     */     
/* 289 */     System.arraycopy(attributeValue, offset, address, 0, address.length);
/* 290 */     setAddress(new StunAddress(address, port));
/*     */   }
/*     */ }
