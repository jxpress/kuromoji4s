name := "kuromoji4s"

organization := "net.jxpress"

version := "0.0.6"

scalaVersion := "2.11.7"

resolvers += "Maven Repository on Github" at "https://jxpress.github.io/mvnrepos/"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.atilika.kuromoji" % "kuromoji-ipadic" % "0.9.0",
  "net.jxpress" % "kuromoji-neologd-dic_2.11" % "0.9.0" % "test" from "https://github.com/jxpress/kuromoji-neologd-dic/releases/download/0.9.0/kuromoji-neologd-dic_2.11-0.0.9.jar",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

mavenRepositoryName := "mvnrepos"
gitHubURI := "git@github.com:jxpress/mvnrepos.git"
MvnReposOnGitHubPlugin.projectSettings

publishMavenStyle := true

publishArtifact in (Compile, packageBin) := true
publishArtifact in (Compile, packageDoc) := false
publishArtifact in (Compile, packageSrc) := false
publishArtifact in Test := false