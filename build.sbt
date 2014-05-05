import AssemblyKeys._

organization := "com.tuvistavie"

name := "frequency-analyzer"

version := "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.apache.lucene" % "lucene-core" % "4.7.2",
  "org.apache.lucene" % "lucene-analyzers-common" % "4.7.2",
  "commons-cli" % "commons-cli" % "1.2"
)

scalacOptions ++= Seq(
  "-feature"
)

javacOptions ++= Seq(
  "-Xlint:unchecked"
)

assemblySettings

jfxSettings

JFX.mainClass := Some("com.tuvistavie.analyzer.Main")
