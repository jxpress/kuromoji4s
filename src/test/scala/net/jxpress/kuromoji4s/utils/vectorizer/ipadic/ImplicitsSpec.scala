package net.jxpress.kuromoji4s.utils.vectorizer.ipadic


import net.jxpress.kuromoji4s.Token
import org.scalatest.{FreeSpec, Matchers}

class ImplicitsSpec extends FreeSpec with Matchers {

  "bagOfWords" - {
    import net.jxpress.kuromoji4s.utils.vectorizer.ipadic.Implicits.defaultTokenizer
    import net.jxpress.kuromoji4s.utils.vectorizer.ipadic.Implicits.bagOfWords

    "bagOfwords(sentence) should return a map that maps a word to the frequency in the sentence." - {

      "No stop words" in {
        implicit val filter: Token[_] => Boolean = { token => true }

        val bows = bagOfWords("私は今日山に登るつもり山")

        bows.size should be (7)
        bows("私") should be (1.0)
        bows("は") should be (1.0)
        bows("今日") should be (1.0)
        bows("山") should be (2.0)
        bows("に") should be (1.0)
        bows("登る") should be (1.0)
        bows("つもり") should be (1.0)
      }

      "Only Noun" in {
        implicit val filter: Token[_] => Boolean = { case token: net.jxpress.kuromoji4s.ipadic.Token => token.pos1.contains("名詞")}

        val bows = bagOfWords("私は今日山に登るつもり山")

        bows.size should be (4)
        bows("私") should be (1.0)
        bows("今日") should be (1.0)
        bows("山") should be (2.0)
        bows("つもり") should be (1.0)
      }
    }
  }

  "WordVector" - {
    val bows0 : Map[String, Double] = Map()
    val bows1 : Map[String, Double] = Map("a" -> 1.0, "b" -> 2.0, "ccc" -> 3.0)
    val bows2 : Map[String, Double] = Map("a" -> 1.0, "B" -> 1.0, "X" -> 1.0)
    val bows3 : Map[String, Double] = Map("B" -> 2.0, "CCC" -> 7.0)

    import net.jxpress.kuromoji4s.utils.vectorizer.ipadic.Implicits.WordVector

    "squaredL2norm should be squared L2-norm of this vector." in {
      val testcases = Seq(bows0, bows1, bows2, bows3)
      val expects = Seq(0.0, 14.0, 3.0, 53.0)

      testcases.map { _.squaredL2norm }.zip(expects).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }
    }

    "value(word)" - {
      "It should be the value of the word if this vector has the word." in {

        bows1.value("a") should be (1.0)
        bows1.value("b") should be (2.0)
        bows1.value("ccc") should be (3.0)

        bows2.value("a") should be (1.0)
        bows2.value("B") should be (1.0)
        bows2.value("X") should be (1.0)

      }

      "It should be the 0.0 if this vector does not have the word." in {
        for(word <- List("ab", "Bb", "B", "CCC", "cccc",  "", "1.0")){
          bows1.value(word) should be (0.0)
          bows0.value(word) should be (0.0)
        }
      }
    }

    "dot(that) should be the value of inner product between this vector and that vector." in {

      val testcases = Seq(bows0, bows1, bows2, bows3)

      testcases.map { bows0 dot _}.zip(Seq(0.0, 0.0, 0.0, 0.0)).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

      testcases.map { bows1 dot _}.zip(Seq(0.0, 14.0, 1.0, 0.0)).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

      testcases.map { bows2 dot _}.zip(Seq(0.0, 1.0, 3.0, 2.0)).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

      testcases.map { bows3 dot _}.zip(Seq(0.0, 0.0, 2.0, 53.0)).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

    }

    "cos(that) should be the value of cosine between this vector and that vector." in {
      val testcases = Seq(bows0, bows1, bows2, bows3)

      testcases.map { bows0 cos _}.zip(Seq(0.0, 0.0, 0.0, 0.0)).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

      testcases.map { bows1 cos _}.zip(
        Seq(
          0.0,
          1.0,
          1.0 / math.sqrt(14.0 * 3.0),
          0.0)
      ).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

      testcases.map { bows2 cos _}.zip(
        Seq(
          0.0,
          1.0 / math.sqrt(3.0 * 14.0),
          1.0,
          2.0 / math.sqrt(3.0 * 53.0))
      ).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }

      testcases.map { bows3 cos _}.zip(
        Seq(
          0.0,
          0.0,
          2.0 / math.sqrt(53.0 * 3.0),
          1.0)
      ).foreach {
        case (actual, expect) =>
          actual should be (expect)
      }
    }

  }
}
