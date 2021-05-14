package net.java.stun4j.stack;

interface ErrorHandler {
  void handleError(String paramString, Throwable paramThrowable);
  
  void handleFatalError(Runnable paramRunnable, String paramString, Throwable paramThrowable);
}

