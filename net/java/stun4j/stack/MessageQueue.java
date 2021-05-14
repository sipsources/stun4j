/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.util.Vector;
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
/*     */ class MessageQueue
/*     */ {
/*  26 */   private Vector queue = new Vector();
/*     */ 
/*     */   
/*  29 */   private int size = 0;
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
/*     */   public int getSize() {
/*  45 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  54 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void add(RawMessage rawMessage) {
/*  64 */     this.queue.add(rawMessage);
/*  65 */     this.size++;
/*     */     
/*  67 */     notifyAll();
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
/*     */   public synchronized RawMessage remove() throws InterruptedException {
/*  83 */     waitWhileEmpty();
/*  84 */     RawMessage rawMessage = this.queue.remove(0);
/*     */     
/*  86 */     this.size--;
/*  87 */     return rawMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void waitWhileEmpty() throws InterruptedException {
/*  98 */     while (isEmpty())
/*     */     {
/* 100 */       wait();
/*     */     }
/*     */   }
/*     */ }
