package com.tuvistavie
package collections.mutable

import algorithms.hash.{HashAlgorithm, Murmur3}

trait Hash[A, B] extends Iterable[(A, B)] { this: HashAlgorithm =>
  implicit val manifest: Manifest[B]

  private var _capacity: Int = 2
  def capacity: Int = _capacity

  private var _size: Int = 0
  override def size: Int = _size

  var data = new Array[List[(A, B)]](capacity)

  override def iterator: Iterator[(A, B)] = new HashIterator

  protected def positiveHash(key: A): Long = hash(key.toString) & (-1L >>> 32)

  protected def hashedValue(key: A): Int = (positiveHash(key) % capacity).toInt

  protected def grow(): Unit = {
    _capacity <<= 1
    val oldData = data
    data = new Array[List[(A, B)]](capacity)
    _size = 0
    for (list <- oldData if list != null; (key, value) <- list) {
      update(key, value)
    }
  }

  def get(key: A): Option[B] = {
    val h = hashedValue(key)
    data(h) match {
      case null => None
      case (_, v) :: Nil => Some(v)
      case list => getElem(key, list)
    }
  }

  def getOrElse(key: A, default: B): B = get(key) match {
    case Some(v) => v
    case None => default
  }

  def has(key: A): Boolean = {
    val h = hashedValue(key)
    data(h) match {
      case null | Nil => false
      case _ => true
    }
  }

  def += (kv: (A, B)): Unit = update(kv._1, kv._2)

  def update(key: A, value: B): Unit = {
    val h = hashedValue(key)
    data(h) match {
      case null =>
        _size += 1
        data.update(h, List((key, value)))
      case list =>
        list.indexWhere { case (k, v) => k == key } match {
          case -1 if size > capacity / 2 =>
            grow()
            update(key, value)
          case -1 => {
            _size += 1
            data.update(h, (key, value) :: list)
          }
          case index => data.update(h, list.updated(index, (key, value)))
        }
    }
  }

  private def getElem(key: A, list: List[(A, B)]): Option[B] = {
    list.foreach { case (k, v) =>
      if (key == k) return Some(v)
    }
    None
  }

  def apply(key: A): B = get(key) match {
    case Some(v) => v
    case None => throw new NoSuchElementException
  }

  protected class HashIterator extends Iterator[(A, B)] {
    private var iteratedElems = 0
    private var i = 0
    private var listIterator: Iterator[(A, B)] = null

    def hasNext: Boolean = iteratedElems < Hash.this.size

    def next(): (A, B) = {
      iteratedElems += 1
      if (listIterator != null && listIterator.hasNext) return listIterator.next()
      while (i < data.length && (data(i) == null || data(i) == Nil)) i += 1
      listIterator = data(i).iterator
      i += 1
      listIterator.next()
    }
  }
}

object Hash {
  def empty[A, B](implicit m: Manifest[B]) = new {
    implicit val manifest = m
  } with Hash[A, B] with Murmur3
}
