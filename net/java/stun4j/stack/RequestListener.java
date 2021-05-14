package net.java.stun4j.stack;

import net.java.stun4j.StunMessageEvent;

public interface RequestListener {
  void requestReceived(StunMessageEvent paramStunMessageEvent);
}
