package net.jxpress.kuromoji4s.ipadic

import org.scalatest.{Matchers, FreeSpec}


class TokenSpec extends FreeSpec with Matchers {

  "Token" - {
    "normalize" - {

      val testcase = Token(
        surface = "本棚",
        isKnown = true,
        isUser = false,
        position =  0,
        pos1 = Some("名詞"),
        pos2 = Some("一般"),
        pos3 = None,
        pos4 = None,
        conjugationForm = None,
        conjugationType = None,
        baseForm = Some("本棚"),
        reading = Some("ほんだな"),
        pronunciation = Some("ホンダナ")
      )

      "It should be baseform if the token has a base form." in {

        for(bf <- List("壁", "布団", "かながわ")){
          testcase.copy(baseForm = Some(bf)).normalize should be (bf)
        }

      }

      "It should be surface if the token has no base form." in {
        for(s <- List("壁", "布団", "かながわ")){
          testcase.copy(surface = s, baseForm = None).normalize should be (s)
        }
      }
    }

  }


}
