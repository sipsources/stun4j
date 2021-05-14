package net.java.stun4j;

public interface ResponseCollector {
  void processResponse(StunMessageEvent paramStunMessageEvent);
  
  void processTimeout();
}
