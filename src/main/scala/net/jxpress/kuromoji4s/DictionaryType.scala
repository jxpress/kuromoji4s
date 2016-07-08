package net.jxpress.kuromoji4s


trait DictionaryType


object DictionaryType {

  trait IPA extends DictionaryType

  object IPA {
    case object Default extends IPA
    case object Neologd extends IPA
  }

}