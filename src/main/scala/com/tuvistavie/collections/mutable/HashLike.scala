package com.tuvistavie.collections.mutable

import collection.mutable

trait HashLike[A, B] {
  def += (kv: (A, B)): this.type
  def getOrElse[B1 >: B](s: A, i: => B1): B1
  def toList: List[(A, B)]
}

class MutableMapWrapper[A, B] extends mutable.HashMap[A, B] with HashLike[A, B]

object HashLike {
  implicit def emptyMap[A, B]: HashLike[A, B] = new MutableMapWrapper[A, B]
}
