package net.jxpress.kuromoji4s.ipadic

object StopWords {

  val Default: net.jxpress.kuromoji4s.Token[_] => Boolean  = {
    case token: net.jxpress.kuromoji4s.ipadic.Token =>
        token.pos1 match {
          case Some("名詞") =>
            token.pos2 match {
              case Some("接尾" | "非自立" | "数" | "代名詞") => false
              case _ => true
            }
          case Some("動詞") =>

            token.pos2 match {
              case Some("接尾") => false
              case _ =>
                token.baseForm match {
                  case Some("ある" | "する" | "いる" | "おる" | "なる" | "できる" | "くださる" | "下さる") => false
                  case _ => true
                }
            }
          case Some("形容詞" | "形容動詞") => true
          case _ => false
        }
      case _ => false
  }
}
