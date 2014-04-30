package com.tuvistavie
package algorithms.hash

trait JoaatHash extends HashAlgorithm {
  def hash(string: String): Int = {
    var result = 0
    string.foreach { c =>
      result += c
      result += (result << 10)
      result ^= (result >> 6)
    }
    result += (result << 3)
    result ^= (result >> 11)
    result += (result << 15)
    result
  }
}
