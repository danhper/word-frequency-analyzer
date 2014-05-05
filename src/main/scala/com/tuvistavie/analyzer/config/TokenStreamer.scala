package com.tuvistavie
package analyzer.config

import org.apache.lucene.util.Version
import org.apache.lucene.analysis.{Analyzer, Token, TokenStream}
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.miscellaneous.LengthFilter


import java.io.{Reader, InputStream, InputStreamReader}

trait TokenStreamer extends Iterable[String] {

  val tokenStream: TokenStream

  override def iterator: Iterator[String] = {
    new Iterator[String] {
      private var isClosed = false
      def next: String = {
        val charTerm = tokenStream.getAttribute(classOf[CharTermAttribute])
        charTerm.toString
      }

      def hasNext: Boolean = {
        if (isClosed) return false
        val remaining = tokenStream.incrementToken()
        if (remaining) return true
        isClosed = true
        false
      }
    }
  }

}


object TokenStreamer {
  val luceneVersion: Version = Version.LUCENE_47

  def apply(reader: Reader): TokenStreamer = {
    val analyzer: Analyzer = new StandardAnalyzer(luceneVersion)
    val source = analyzer.tokenStream("dummyField", reader)
    val ts = new LengthFilter(luceneVersion, true, source, 3, Integer.MAX_VALUE);
    ts.reset()
    new TokenStreamer {
      override val tokenStream = ts
    }
  }

  def apply(is: InputStream): TokenStreamer = TokenStreamer(new InputStreamReader(is))
}
