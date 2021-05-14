package net.java.stun4j.stack;

import net.java.stun4j.StunMessageEvent;

interface MessageEventHandler {
  void handleMessageEvent(StunMessageEvent paramStunMessageEvent);
}
