package net.jxpress.kuromoji4s

import language.implicitConversions

/** A Trait for Basic Tokens ( corresponding to TokenBase.java in kuromoji)
  *
  * @tparam T the type of Dictionaries
  */
trait Token[T <: DictionaryType] {


  val surface: String
  val isKnown: Boolean
  val isUser: Boolean
  val position: Int

  /** Converts this token into a normalized string.
    * Usually returns the base form of this token, or the surface string if this token has no base form.
    */
  def normalize: String
}
