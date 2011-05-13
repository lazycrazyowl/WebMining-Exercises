package de.tudarmstadt.webmining2011.crawler

import collection.mutable.Queue

/**
 * A queue trait
 */
trait MyQueue[A] {
  def dequeue(): A
  def enqueue(elems: A*): Unit
}

/**
 * The BFS Queue. This is simple a wrapper of scala default queue
 */
class BFSQueue[A] extends MyQueue[A] {
  val  q: Queue[A] = Queue()

  def dequeue: A = q.dequeue
  def enqueue(elems: A*) = q.enqueue(elems:_*)
}

/**
 * The DFS queue
 */
class DFSQueue[A] extends MyQueue[A] {

  var list: Seq[A] = List.empty

  def dequeue =  {
    if (list.isEmpty) {
      throw new NoSuchElementException("queue empty")
    }

    val r = list.head
    list = list.tail
    r
  }
  def enqueue(elems: A*) = list = elems ++ list
}