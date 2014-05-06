package com.tuvistavie
package analyzer
package config

import scala.collection.JavaConversions._

import java.io.InputStream


import collections.mutable.{Hash, HashLike, MutableMapWrapper}
import models.WordInfo

trait FrequencyAnalyzer {
  type M = HashLike[String, Int]

  def emptyContainer: M

  def getFrequencies(is: InputStream): M = {
    val frequencies: M = emptyContainer
    val tokenStream = TokenStreamer(is)

    tokenStream.foreach { token =>
      frequencies += (token -> (frequencies.getOrElse(token, 0) + 1))
    }
    frequencies
  }

  def getSortedFrequencies(is: InputStream): java.util.List[WordInfo] = {
    getSortedFrequencies(is, -1)
  }

  def getSortedFrequencies(is: InputStream, limit: Int): java.util.List[WordInfo] = {
    val frequencies = getFrequencies(is)
    val sorted = frequencies.toList sortBy { case (k, v) => -v }
    val limited = if (limit > 0) sorted take limit else sorted
    limited map { case (k, v) => new WordInfo(k, v) }
  }
}

object HashFrequencyAnalyzer extends FrequencyAnalyzer {
  def emptyContainer: M = Hash.empty
}

object MapFrequencyAnalyzer extends FrequencyAnalyzer {
  def emptyContainer: M = HashLike.emptyMap
}