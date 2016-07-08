package net.jxpress.kuromoji4s.ipadic

import java.io.{IOException, InputStream}

import com.atilika.kuromoji.util.ResourceResolver


class DictionaryResourceResolver(path: String) extends ResourceResolver {

  override def resolve(resourceName: String): InputStream = {
    Option(getClass.getClassLoader.getResourceAsStream(path +  "/" + resourceName)).getOrElse { throw new IOException("Classpath resource not found: " + resourceName)}
  }
}
