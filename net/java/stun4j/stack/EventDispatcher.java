/*     */ package net.java.stun4j.stack;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import net.java.stun4j.NetAccessPointDescriptor;
/*     */ import net.java.stun4j.StunMessageEvent;
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
/*     */ public class EventDispatcher
/*     */ {
/*     */   private Vector requestListeners;
/*     */   private Hashtable requestListenersChildren;
/*     */   
/*     */   public synchronized void addRequestListener(RequestListener listener) {
/*  51 */     if (this.requestListeners == null)
/*     */     {
/*  53 */       this.requestListeners = new Vector();
/*     */     }
/*     */     
/*  56 */     this.requestListeners.addElement(listener);
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
/*     */   public synchronized void addRequestListener(NetAccessPointDescriptor descriptor, RequestListener listener) {
/*  72 */     if (this.requestListenersChildren == null)
/*     */     {
/*  74 */       this.requestListenersChildren = new Hashtable();
/*     */     }
/*  76 */     EventDispatcher child = (EventDispatcher)this.requestListenersChildren.get(descriptor);
/*     */     
/*  78 */     if (child == null) {
/*     */       
/*  80 */       child = new EventDispatcher();
/*  81 */       this.requestListenersChildren.put(descriptor, child);
/*     */     } 
/*  83 */     child.addRequestListener(listener);
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
/*     */   public synchronized void removeRequestListener(RequestListener listener) {
/*  98 */     if (this.requestListeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     this.requestListeners.removeElement(listener);
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
/*     */   public synchronized void removeRequestListener(NetAccessPointDescriptor apDescriptor, RequestListener listener) {
/* 117 */     if (this.requestListenersChildren == null) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     EventDispatcher child = (EventDispatcher)this.requestListenersChildren.get(apDescriptor);
/*     */ 
/*     */     
/* 124 */     if (child == null) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     child.removeRequestListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireMessageEvent(StunMessageEvent evt) {
/* 139 */     NetAccessPointDescriptor apDescriptor = evt.getSourceAccessPoint();
/* 140 */     if (this.requestListeners != null) {
/*     */       
/* 142 */       Iterator iterator = this.requestListeners.iterator();
/* 143 */       while (iterator.hasNext()) {
/*     */         
/* 145 */         RequestListener target = iterator.next();
/*     */         
/* 147 */         target.requestReceived(evt);
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     if (this.requestListenersChildren != null && apDescriptor != null) {
/*     */       
/* 153 */       EventDispatcher child = (EventDispatcher)this.requestListenersChildren.get(apDescriptor);
/*     */       
/* 155 */       apDescriptor.getAddress().toString();
/*     */       
/* 157 */       Enumeration enumer = this.requestListenersChildren.elements();
/* 158 */       while (enumer.hasMoreElements())
/*     */       {
/* 160 */         Object item = enumer.nextElement();
/*     */       }
/*     */ 
/*     */       
/* 164 */       if (child != null)
/*     */       {
/* 166 */         child.fireMessageEvent(evt);
/*     */       }
/*     */     } 
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
/*     */   public synchronized boolean hasRequestListeners(NetAccessPointDescriptor apDescriptor) {
/* 182 */     if (this.requestListeners != null && !this.requestListeners.isEmpty())
/*     */     {
/*     */       
/* 185 */       return true;
/*     */     }
/* 187 */     if (this.requestListenersChildren != null) {
/*     */       
/* 189 */       EventDispatcher child = (EventDispatcher)this.requestListenersChildren.get(apDescriptor);
/*     */       
/* 191 */       if (child != null && child.requestListeners != null)
/*     */       {
/* 193 */         return !child.requestListeners.isEmpty();
/*     */       }
/*     */     } 
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllListeners() {
/* 204 */     if (this.requestListeners != null)
/* 205 */       this.requestListeners.removeAllElements(); 
/* 206 */     this.requestListenersChildren = null;
/*     */   }
/*     */ }


