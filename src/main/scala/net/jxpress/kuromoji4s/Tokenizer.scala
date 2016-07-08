package net.jxpress.kuromoji4s


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
