package net.jxpress.kuromoji4s.ipadic

import com.atilika.kuromoji.util.ResourceResolver
import net.jxpress.kuromoji4s
import net.jxpress.kuromoji4s.DictionaryType.IPA
import net.jxpress.kuromoji4s.DictionaryType.IPA._

import collection.JavaConversions._
import language.implicitConversions

class Tokenizer private (private val impl : com.atilika.kuromoji.ipadic.Tokenizer) extends kuromoji4s.Tokenizer[IPA, Token] {
  override def apply(text: String): Seq[Token] = impl.tokenize(text).map { toToken }
}

object Tokenizer {

  def apply(dictType: IPA = Default): Tokenizer = {
    dictType match {
      case Neologd =>
        val builder = new Builder {
          override protected def createResolver(): ResourceResolver = new DictionaryResourceResolver("ipadic/neologd")
        }
        new Tokenizer(builder.build())
      case _ =>
        new Tokenizer(new com.atilika.kuromoji.ipadic.Tokenizer())
    }
  }
}
