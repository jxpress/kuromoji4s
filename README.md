# kuromoji4s 

kuromoji4s is a wrapper library of kuromoji (http://www.atilika.org/) for scala 

## Requirement

* JDK7
* scala 2.11.7 or later
* sbt 0.13.8 or later

## Build

```shell
% git clone https://github.com/jxpress/kuromoji4s.git
% cd kuromoji4s
% sbt compile
```

Or, add the following statements to your build.sbt 

```build.sbt
libraryDependencies += "net.jxpress" % "kuromoji4s_2.11" % "0.0.6"

```

## Limitation

**Only kuromoji-ipadic is supported in the current version (0.0.6)**

if you want to use [neologd](https://github.com/neologd/mecab-ipadic-neologd) dictionary, you need to be the following additional dependency: 

```build.sbt
libraryDependencies += "net.jxpress" % "kuromoji-neologd-dic_2.11" % "0.9.0"  from "https://github.com/jxpress/kuromoji-neologd-dic/releases/download/0.9.0/kuromoji-neologd-dic_2.11-0.0.9.jar"
```

the detail for kuomoji-neologd-dic is [here](https://github.com/jxpress/kuromoji-neologd-dic) 

## Sample code 

see [here](https://github.com/jxpress/kuromoji4s/blob/master/src/test/scala/net/jxpress/kuromoji4s/ipadic/Sample.scala)

