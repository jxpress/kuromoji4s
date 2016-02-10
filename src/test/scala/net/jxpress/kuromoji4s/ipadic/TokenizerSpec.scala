package net.jxpress.kuromoji4s.ipadic

import org.scalatest.FlatSpec
import scala.collection.JavaConversions._

class TokenizerSpec extends FlatSpec {
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

  def assertEquals(token1: Token, token2: TOKEN) : Unit = {
    assert(token1.surface == token2.getSurface)
    assert(token1.isKnown == token2.isKnown)
    assert(token1.isUser == token2.isUser)
    assert(token1.position == token2.getPosition)
    assert(toString(token1.pos1) == token2.getPartOfSpeechLevel1)
    assert(toString(token1.pos2) == token2.getPartOfSpeechLevel2)
    assert(toString(token1.pos3) == token2.getPartOfSpeechLevel3)
    assert(toString(token1.pos4) == token2.getPartOfSpeechLevel4)
    assert(toString(token1.conjugationForm) == token2.getConjugationForm)
    assert(toString(token1.conjugationType) == token2.getConjugationType)
    assert(toString(token1.baseForm) == token2.getBaseForm)
    assert(toString(token1.reading) == token2.getReading)
    assert(toString(token1.pronunciation) == token2.getPronunciation)
  }

  "Kuromoji4s.Tokenizer" should "return same results to the original Kuromoji Tokenizer" in {
    import net.jxpress.kuromoji4s.Tokenizer._

    val tokenizer = net.jxpress.kuromoji4s.Tokenizer()

    val num = testcase.map {
      sentence =>

        val actual = tokenizer(sentence)
        val expect = TOKENIZER.tokenize(sentence)

        assert(actual.size == expect.size)
        actual.indices.foreach {
          i =>
            assertEquals(actual(i), expect(i))
        }

        expect.size
    }.sum

    assert(num > testcase.size)

  }

}
