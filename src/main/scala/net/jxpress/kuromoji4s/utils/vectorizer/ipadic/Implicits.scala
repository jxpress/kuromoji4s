package net.jxpress.kuromoji4s.utils.vectorizer.ipadic

import net.jxpress.kuromoji4s.ipadic.Tokenizer
import net.jxpress.kuromoji4s.Token

object Implicits {

  object default {
    implicit lazy val Tokenizer : Tokenizer = net.jxpress.kuromoji4s.ipadic.Tokenizer()
    implicit lazy val StoWords = net.jxpress.kuromoji4s.ipadic.StopWords.Default
  }

  implicit def bagOfWords(sentence: String)(implicit tokenizer: Tokenizer, stopWords : Token[_] => Boolean) : Map[String, Double] = {
    tokenizer(sentence)
      .filter{ stopWords }
      .map { _.normalize -> 1 }
      .groupBy { _._1 }
      .map { case (w, v) => w -> v.map{ _._2 }.sum.toDouble }
  }

  implicit def string2wordVector(str: String)(implicit tokenizer: Tokenizer, stopWords : Token[_] => Boolean):  WordVector = {
    bagOfWords(str)
  }


  implicit class WordVector(val x: Map[String, Double]) extends AnyVal {

    /** Returns the value of squared L2-norm of this vector
      *
      */
    def squaredL2norm: Double = x.foldLeft(0.0){ (v, x) => v + x._2 * x._2 }

    /** Returns the value of given Element t.
      * Returns 0.0 if this vector dose not contain the element t.
      *
      * @param t an element
      */
    def value(t: String): Double = x.getOrElse(t, 0.0)

    /** Returns the value of inner product between this vector and that vector
      *
      * @param that vector
      */
    def dot(that: WordVector) : Double = that.x.foldLeft(0.0) { case (d, (w, v)) => d + value(w) * v }


    /** Returns the value of cosine between this vector and that vector.
      *
      * Returns 0.0 if either the norm of this vector or that one's is zero
      *
      * @param that vector
      */
    def cos(that: WordVector) : Double = squaredL2norm * that.squaredL2norm match {
      case 0.0 => 0.0
      case m: Double =>
        dot(that) / math.sqrt(m)
    }

  }
}
