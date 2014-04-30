organization := "com.tuvistavie"

name := "frequency-analyzer"

version := "0.1.0-SNAPSHOT"

libraryDependencies += "org.apache.lucene" % "lucene-core" % "4.7.2"

libraryDependencies += "org.apache.lucene" % "lucene-analyzers-common" % "4.7.2"

jfxSettings

JFX.mainClass := Some("com.tuvistavie.analyzer.Main")
