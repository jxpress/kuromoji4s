package net.jxpress.kuromoji4s.ipadic

import net.jxpress.kuromoji4s
import net.jxpress.kuromoji4s.DictionaryType.IPA
import collection.JavaConversions._
import language.implicitConversions

class Tokenizer extends kuromoji4s.Tokenizer[IPA, Token] {

  import Token._

  private val impl = new com.atilika.kuromoji.ipadic.Tokenizer()

  override def apply(text: String): Seq[Token] = impl.tokenize(text).map { toToken }

}
