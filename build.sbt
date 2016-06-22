name := "kuromoji4s"

organization := "net.jxpress"

version := "0.0.5"

scalaVersion := "2.11.7"

resolvers += "Maven Repository on Github" at "http://jxpress.github.io/mvnrepos/"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.atilika.kuromoji" % "kuromoji-ipadic" % "0.9.0",
  "org.scala-lang" % "scala-reflect" % "2.11.7",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
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