name := "simmapeditor"

version := "1.0"

lazy val `simmapeditor` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs , specs2 % Test)

libraryDependencies += "org.easytesting" % "fest-assert" % "1.4" % Test

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  