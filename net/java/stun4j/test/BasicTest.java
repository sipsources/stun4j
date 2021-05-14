/*     */ package net.java.stun4j.test;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class BasicTest
/*     */ {
/*  25 */   private static final Logger logger = Logger.getLogger(BasicTest.class.getName());
/*     */ 
/*     */   
/*  28 */   String stunSerAddrStr = "stun01.sipphone.com";
/*  29 */   DatagramSocket sock = null;
/*  30 */   private byte[] bindingRequest = new byte[] { 0, 1, 0, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 3, 0, 4, 0, 0, 0, 0 };
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
/*  45 */   private byte[] wrongBindingRequest = new byte[] { 0, 1, 0, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 0, 3, 0, 4, 0, 0, 0, 6 };
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
/*     */   public void sendBindingRequest() {
/*     */     try {
/*  69 */       SocketAddress stunSerAddr = new InetSocketAddress(this.stunSerAddrStr, 3478);
/*     */ 
/*     */       
/*  72 */       DatagramPacket packet = new DatagramPacket(this.bindingRequest, 28, stunSerAddr);
/*  73 */       this.sock = new DatagramSocket();
/*     */       
/*  75 */       this.sock.send(packet);
/*     */     }
/*  77 */     catch (SocketException ex) {
/*     */       
/*  79 */       logger.log(Level.WARNING, "Failed to open a socket to " + this.stunSerAddrStr + ". ", ex);
/*     */ 
/*     */     
/*     */     }
/*  83 */     catch (IOException ex) {
/*     */       
/*  85 */       logger.log(Level.WARNING, "Failed to send the binding request to " + this.stunSerAddrStr + ". ", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendWrongBindingRequest() {
/*     */     try {
/*  96 */       SocketAddress stunSerAddr = new InetSocketAddress(this.stunSerAddrStr, 3478);
/*     */ 
/*     */       
/*  99 */       DatagramPacket packet = new DatagramPacket(this.wrongBindingRequest, 28, stunSerAddr);
/* 100 */       this.sock = new DatagramSocket();
/*     */       
/* 102 */       this.sock.send(packet);
/*     */     }
/* 104 */     catch (SocketException ex) {
/*     */       
/* 106 */       logger.log(Level.WARNING, "Failed to open a socket to " + this.stunSerAddrStr + ". ", ex);
/*     */ 
/*     */     
/*     */     }
/* 110 */     catch (IOException ex) {
/*     */       
/* 112 */       logger.log(Level.WARNING, "Failed to send the binding request to " + this.stunSerAddrStr + ". ", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void receiveBindingResponse() {
/* 122 */     byte[] responseData = new byte[512];
/* 123 */     DatagramPacket responsePacket = new DatagramPacket(responseData, 512);
/*     */     
/*     */     try {
/* 126 */       this.sock.receive(responsePacket);
/*     */     }
/* 128 */     catch (IOException ex) {
/*     */       
/* 130 */       System.err.println("Failed to receive a packet! " + ex.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     System.out.println("====================== Stun Header =============================");
/* 137 */     System.out.println("STUN Message Type: 0x" + byteToHex(responseData[0]) + byteToHex(responseData[1]));
/* 138 */     System.out.println("Message Length:    0x" + byteToHex(responseData[2]) + byteToHex(responseData[3]));
/* 139 */     System.out.println("Transaction ID:    0x" + byteToHex(responseData[4]) + byteToHex(responseData[5]) + byteToHex(responseData[6]) + byteToHex(responseData[7]) + byteToHex(responseData[8]) + byteToHex(responseData[9]) + byteToHex(responseData[10]) + byteToHex(responseData[11]) + byteToHex(responseData[12]) + byteToHex(responseData[13]) + byteToHex(responseData[14]) + byteToHex(responseData[15]) + byteToHex(responseData[16]) + byteToHex(responseData[17]) + byteToHex(responseData[18]) + byteToHex(responseData[19]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     System.out.println("====================== Attributes ==============================");
/* 149 */     for (int i = 20; i < responsePacket.getLength(); ) {
/*     */       
/* 151 */       byte attLen1 = 0;
/* 152 */       byte attLen2 = 0;
/* 153 */       System.out.println("Attribute Type:   0x" + byteToHex(responseData[i++]) + byteToHex(responseData[i++]));
/* 154 */       System.out.println("Attribute Length: 0x" + byteToHex(attLen2 = responseData[i++]) + byteToHex(attLen2 = responseData[i++]));
/* 155 */       int attLen = (attLen1 << 8) + attLen2;
/* 156 */       for (int j = 0; j < attLen; j++)
/*     */       {
/* 158 */         System.out.println("    data[" + j + "]=" + (responseData[j + i] & 0xFF));
/*     */       }
/* 160 */       i += attLen;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String byteToHex(byte b) {
/* 166 */     return ((b < 15) ? "0" : "") + Integer.toHexString(b & 0xFF).toUpperCase();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 171 */     BasicTest basicTest = new BasicTest();
/*     */ 
/*     */     
/* 174 */     basicTest.sendWrongBindingRequest();
/* 175 */     basicTest.receiveBindingResponse();
/*     */   }
/*     */ }
