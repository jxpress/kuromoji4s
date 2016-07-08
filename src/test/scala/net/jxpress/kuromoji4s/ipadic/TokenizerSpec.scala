package net.jxpress.kuromoji4s.ipadic

import net.jxpress.kuromoji4s.DictionaryType.IPA.Neologd
import org.scalatest.{FreeSpec, Matchers}

import scala.collection.JavaConversions._

class TokenizerSpec extends FreeSpec
with Matchers
{
  val testcase =
    """メロスは激怒した。
      |必ず、かの邪智暴虐じゃちぼうぎゃくの王を除かなければならぬと決意した。
      |メロスには政治がわからぬ。
      |メロスは、村の牧人である。笛を吹き、羊と遊んで暮して来た。
      |けれども邪悪に対しては、人一倍に敏感であった。
      |きょう未明メロスは村を出発し、野を越え山越え、十里はなれた此このシラクスの市にやって来た。
      |メロスには父も、母も無い。
      |女房も無い。
      |This is a pen.
      |十六の、内気な妹と二人暮しだ。
      |この妹は、村の或る律気な一牧人を、近々、花婿はなむことして迎える事になっていた。
      |結婚式も間近かなのである。
      |メロスは、それゆえ、花嫁の衣裳やら祝宴の御馳走やらを買いに、はるばる市にやって来たのだ。
      |先ず、その品々を買い集め、それから都の大路をぶらぶら歩いた。
      |メロスには竹馬の友があった。セリヌンティウスである。今は此のシラクスの市で、石工をしている。
      |その友を、これから訪ねてみるつもりなのだ。
      |久しく逢わなかったのだから、訪ねて行くのが楽しみである。
      |歩いているうちにメロスは、まちの様子を怪しく思った。
      |ひっそりしている。
      |もう既に日も落ちて、まちの暗いのは当りまえだが、けれども、なんだか、夜のせいばかりでは無く、市全体が、やけに寂しい。
      |のんきなメロスも、だんだん不安になって来た。
      |路で逢った若い衆をつかまえて、何かあったのか、二年まえに此の市に来たときは、夜でも皆が歌をうたって、まちは賑やかであった筈はずだが、と質問した。""".stripMargin.split("\n")


  type TOKEN = com.atilika.kuromoji.ipadic.Token
  val TOKENIZER = new com.atilika.kuromoji.ipadic.Tokenizer()

  def toString(s : Option[String]): String = s match {
    case Some(str) => str
    case _ => "*"
  }

  def sameToken(token1: Token, token2: TOKEN) : Boolean = {

    token1.surface should be (token2.getSurface)
    token1.isKnown should be (token2.isKnown)
    token1.isUser should be (token2.isUser)

    token1.position should be (token2.getPosition)
    toString(token1.pos1) should be (token2.getPartOfSpeechLevel1)

    toString(token1.pos2) should be (token2.getPartOfSpeechLevel2)
    toString(token1.pos3) should be (token2.getPartOfSpeechLevel3)
    toString(token1.pos4) should be (token2.getPartOfSpeechLevel4)
    toString(token1.conjugationForm) should be (token2.getConjugationForm)
    toString(token1.conjugationType) should be (token2.getConjugationType)
    toString(token1.baseForm) should be (token2.getBaseForm)
    toString(token1.reading) should be (token2.getReading)
    toString(token1.pronunciation) should be (token2.getPronunciation)

    true
  }

  def similarToken(token1: Token, token2: TOKEN) : Boolean = {

    token1.surface should be (token2.getSurface)

    token1.position should be (token2.getPosition)
    toString(token1.pos1) should be (token2.getPartOfSpeechLevel1)
    toString(token1.pos2) should be (token2.getPartOfSpeechLevel2)
    toString(token1.baseForm) should be (token2.getBaseForm)

    true
  }

  "Kuromoji4s.Tokenizer should return same results to the original Kuromoji Tokenizer" in {

    val tokenizer = Tokenizer()

    testcase.foreach {
      sentence =>

        val actual = tokenizer(sentence)
        val expect = TOKENIZER.tokenize(sentence)

        actual.size should be (expect.size)

        actual.zip(expect).foreach {
          case (t1, t2) =>
            sameToken(t1, t2) should be (true)
        }
    }
  }

  "The results of kuromoji4s.ipadic.neologd.tokenizer should be similar to the original kuromoji tokenizer ones" in {

    val tokenizer = Tokenizer(Neologd)

    List(
      "けれども邪悪に対しては、人一倍に敏感であった。",
      "ありがとう、さようなら。"
    ).foreach {
      sentence =>
        val actual = tokenizer(sentence)
        val expect = TOKENIZER.tokenize(sentence)
        actual.size should be (expect.size)
        actual.zip(expect).foreach {
          case (t1, t2) =>
            similarToken(t1, t2) should be (true)
        }
    }
  }

  "The results of kuromoji4s.ipadic.neologd.tokenizer should be different from the original kuromoji tokenizer ones for new words" in {

    val tokenizer = Tokenizer(Neologd)

    List(
      "きゃりーぱみゅぱみゅ"
    ).foreach {
      sentence =>
        val actual = tokenizer(sentence)
        val expect = TOKENIZER.tokenize(sentence)
        actual.size should not be expect.size

    }
  }

}
