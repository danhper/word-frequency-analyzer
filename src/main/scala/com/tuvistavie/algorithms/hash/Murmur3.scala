/*
* Copyright (C) 2011 The Guava Authors
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License
* is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing permissions and limitations under
* the License.
*/

/*
* MurmurHash3 was written by Austin Appleby, and is placed in the public
* domain. The author hereby disclaims copyright to this source code.
*/

/*
* Source:
* http://code.google.com/p/smhasher/source/browse/trunk/MurmurHash3.cpp
* (Modified to adapt to Guava coding conventions and to use the HashFunction interface)
*/

/*
 * Ported to Scala
 * @author Daniel Perez
 */


package com.tuvistavie
package algorithms.hash

trait Murmur3 extends HashAlgorithm {
  private val C1: Int = 0xcc9e2d51
  private val C2: Int = 0x1b873593

  protected def seed: Int = 0

  private def fmix(h: Int, length: Int) = {
    var h1 = h
    h1 ^= length
    h1 ^= h1 >>> 16
    h1 *= 0x85ebca6b
    h1 ^= h1 >>> 13
    h1 *= 0xc2b2ae35
    h1 ^= h1 >>> 16
    h1
  }

  private def mixK1(k: Int): Int = {
    var k1 = k
    k1 *= C1
    k1 = Integer.rotateLeft(k1, 15)
    k1 *= C2
    k1
  }

  private def mixH1(h: Int, k1: Int): Int = {
    var h1 = h
    h1 ^= k1
    h1 = Integer.rotateLeft(h1, 13)
    h1 = h1 * 5 + 0xe6546b64
    h1
  }

  private def mixCollHash(hash: Int, count: Int): Int = {
    var h1 = seed
    val k1 = mixK1(hash)
    h1 = mixH1(h1, k1)
    fmix(h1, count)
  }

  def hash(string: String): Int = {
    var hash = 1
    string.foreach { c =>
      hash = 31 * hash + c.toInt
    }
    mixCollHash(hash, string.length)
  }
}
