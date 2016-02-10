package net.jxpress.kuromoji4s.ipadic

import net.jxpress.kuromoji4s.DictionaryType
import language.implicitConversions

object Token {


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
  implicit def toToken(token: com.atilika.kuromoji.ipadic.Token) : Token = {
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

/** A case class for tokens for IPA dictionary
  *
  * A main difference from kuromoji's token is '*' features ('*' feature means null)
  * '*' features in this token are represented None
  *
  * @param surface surface string of this token ( same to the kuromoji's getSurface  )
  * @param isKnown flag whether this token exists on the dictionary or not ( same to the kuromoji's isKnown )
  * @param isUser flag whether this token exists on the user dictionary or not ( same to the kuromoji's isUser )
  * @param position position of this token in a sentence string ( same to the kuromoji's getPosition method )
  * @param pos1 part of speech tag of this token ( same to kuromoji's getPartOfSpeechLevel1 )
  * @param pos2 part of speech tag of this token ( same to kuromoji's getPartOfSpeechLevel2 )
  * @param pos3 part of speech tag of this token ( same to kuromoji's getPartOfSpeechLevel3 )
  * @param pos4 part of speech tag of this token ( same to kuromoji's getPartOfSpeechLevel4 )
  * @param conjugationForm conjugation form of this token ( same to kuromoji's getConjugationForm )
  * @param conjugationType conjugation type of this token ( same to kuromoji's getConjugationType )
  * @param baseForm base form of this token (same to kuromoji's getBaseForm)
  * @param reading reading of this token (same to kuromoji's getReading)
  * @param pronunciation pronunciation of this token (same to kuromoji's getPronunciation )
  */
case class Token(
                  surface: String,
                  isKnown: Boolean,
                  isUser: Boolean,
                  position: Int,
                  pos1: Option[String],
                  pos2: Option[String],
                  pos3: Option[String],
                  pos4: Option[String],
                  conjugationForm : Option[String],
                  conjugationType : Option[String],
                  baseForm : Option[String],
                  reading: Option[String],
                  pronunciation: Option[String]
                ) extends net.jxpress.kuromoji4s.Token[DictionaryType.IPA]

