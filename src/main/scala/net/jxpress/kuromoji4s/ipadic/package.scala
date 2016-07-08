package net.jxpress.kuromoji4s


import language.implicitConversions

package object ipadic {
  /** Converts the given string feature of kuromoji tokens into Option[String]
    *
    * Returns None if the feature is '*' stands for Null in kuromoji,
    * otherwise Option(feature)
    *
    * @param feature feature in a kuromoji token
    */
  implicit def featureToStrOpt(feature: String): Option[String] = {
    Option(feature) match {
      case Some("*") | None => None
      case o: Some[String] => o
    }
  }

  /** Converts the given kuromoji token into our token instance
    *
    * @param token kuromoji token
    */
  implicit def toToken(token: com.atilika.kuromoji.ipadic.Token) : ipadic.Token = {
    Token(
      surface = token.getSurface,
      isKnown = token.isKnown,
      isUser = token.isUser,
      position = token.getPosition,
      pos1 = token.getPartOfSpeechLevel1,
      pos2 = token.getPartOfSpeechLevel2,
      pos3 = token.getPartOfSpeechLevel3,
      pos4 = token.getPartOfSpeechLevel4,
      conjugationForm = token.getConjugationForm,
      conjugationType = token.getConjugationType,
      baseForm = token.getBaseForm,
      reading = token.getReading,
      pronunciation = token.getPronunciation
    )
  }
}
