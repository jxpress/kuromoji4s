package net.jxpress.kuromoji4s.ipadic

import java.util

import com.atilika.kuromoji.dict._
import com.atilika.kuromoji.trie.DoubleArrayTrie
import com.atilika.kuromoji.util.ResourceResolver

abstract class Builder extends com.atilika.kuromoji.ipadic.Tokenizer.Builder {
  final val DEFAULT_KANJI_LENGTH_THRESHOLD = 2
  final val DEFAULT_OTHER_LENGTH_THRESHOLD = 7
  final val DEFAULT_KANJI_PENALTY = 3000
  final val DEFAULT_OTHER_PENALTY = 1700

  val kanjiPenaltyLengthTreshold = DEFAULT_KANJI_LENGTH_THRESHOLD
  val kanjiPenalty = DEFAULT_KANJI_PENALTY
  val otherPenaltyLengthThreshold = DEFAULT_OTHER_LENGTH_THRESHOLD
  val otherPenalty = DEFAULT_OTHER_PENALTY

  val nakaguroSplit = false

  protected def createResolver(): ResourceResolver

  override def loadDictionaries(): Unit = {
    penalties = new util.ArrayList[Integer]()
    penalties.add(kanjiPenaltyLengthTreshold)
    penalties.add(kanjiPenalty)
    penalties.add(otherPenaltyLengthThreshold)
    penalties.add(otherPenalty)

    val resolver = createResolver()

    try {
      doubleArrayTrie = DoubleArrayTrie.newInstance(resolver)
      connectionCosts = ConnectionCosts.newInstance(resolver)
      tokenInfoDictionary = TokenInfoDictionary.newInstance(resolver)
      characterDefinitions = CharacterDefinitions.newInstance(resolver)
      if (nakaguroSplit) {
        characterDefinitions.setCategories('・', Array[String]("SYMBOL"))
      }
      unknownDictionary = UnknownDictionary.newInstance(resolver, characterDefinitions, totalFeatures)
      insertedDictionary = new InsertedDictionary(totalFeatures)
    }
    catch {
      case ouch: Exception =>
        throw new RuntimeException("Could not load dictionaries.", ouch)
    }
  }
}
