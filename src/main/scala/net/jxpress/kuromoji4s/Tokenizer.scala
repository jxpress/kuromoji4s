package net.jxpress.kuromoji4s

import net.jxpress.kuromoji4s.DictionaryType.IPA

/** A basic trait for Tokenizer
  *
  * @tparam D the type of Dictionaries
  * @tparam T the type of Tokens
  */
trait Tokenizer[D <: DictionaryType, T <: Token[D]]  {

  /** Tokenizes the given text to a sequence of tokens
    *
    * @param text text
    */
  def apply(text: String): Seq[T]
}


object Tokenizer {

  trait Builder[D <: DictionaryType, T <: Token[D]] {
    def build(): Tokenizer[D, T]
  }

  implicit case object IPADIC extends Builder[DictionaryType.IPA, ipadic.Token] {
    override def build(): Tokenizer[IPA, ipadic.Token] = new ipadic.Tokenizer()
  }

  def apply[D <: DictionaryType,  T <: Token[D]]()(implicit builder: Builder[D, T]): Tokenizer[D, T] = builder.build()

}