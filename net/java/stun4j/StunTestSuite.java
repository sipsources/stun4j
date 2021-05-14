/*    */ package net.java.stun4j;
/*    */ import junit.framework.Test;
/*    */ import junit.framework.TestCase;
/*    */ import junit.framework.TestSuite;
/*    */ import net.java.stun4j.attribute.AddressAttributeTest;
/*    */ import net.java.stun4j.attribute.AttributeDecoderTest;
/*    */ import net.java.stun4j.attribute.ErrorCodeAttributeTest;
/*    */ import net.java.stun4j.attribute.OptionalAttributeAttributeTest;
/*    */ import net.java.stun4j.attribute.TestServerAttribute;
/*    */ import net.java.stun4j.attribute.UnknownAttributesAttributeTest;
/*    */ import net.java.stun4j.client.StunAddressDiscovererTest_v4v6;
/*    */ import net.java.stun4j.message.MessageTest;
/*    */ import net.java.stun4j.stack.ShallowStackTest;
/*    */ 
/*    */ public class StunTestSuite extends TestCase {
/*    */   public StunTestSuite(String s) {
/* 17 */     super(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Test suite() {
/* 22 */     TestSuite suite = new TestSuite();
/*    */ 
/*    */     
/* 25 */     suite.addTestSuite(AddressAttributeTest.class);
/*    */     
/* 27 */     suite.addTestSuite(XorOnlyTest.class);
/*    */     
/* 29 */     suite.addTestSuite(AttributeDecoderTest.class);
/*    */     
/* 31 */     suite.addTestSuite(ChangeRequestAttributeTest.class);
/*    */     
/* 33 */     suite.addTestSuite(ErrorCodeAttributeTest.class);
/*    */     
/* 35 */     suite.addTestSuite(UnknownAttributesAttributeTest.class);
/*    */     
/* 37 */     suite.addTestSuite(TestServerAttribute.class);
/*    */     
/* 39 */     suite.addTestSuite(OptionalAttributeAttributeTest.class);
/*    */ 
/*    */     
/* 42 */     suite.addTestSuite(MessageFactoryTest.class);
/* 43 */     suite.addTestSuite(MessageTest.class);
/*    */ 
/*    */     
/* 46 */     suite.addTestSuite(ShallowStackTest.class);
/*    */ 
/*    */     
/* 49 */     suite.addTestSuite(MessageEventDispatchingTest.class);
/*    */ 
/*    */     
/* 52 */     suite.addTestSuite(TransactionSupportTests.class);
/*    */ 
/*    */     
/* 55 */     suite.addTestSuite(StunAddressDiscovererTest.class);
/* 56 */     suite.addTestSuite(StunAddressDiscovererTest_v6.class);
/* 57 */     suite.addTestSuite(StunAddressDiscovererTest_v4v6.class);
/*    */     
/* 59 */     return (Test)suite;
/*    */   }
/*    */ }


