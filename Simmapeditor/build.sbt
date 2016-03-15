name := "simmapeditor"

version := "1.0"

lazy val `simmapeditor` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs , specs2 % Test)

resolvers += "osgeo" at "http://download.osgeo.org/webdav/geotools/"

resolvers += "Jai" at "https://repository.jboss.org/nexus/content/repositories/thirdparty-releases/"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies += "org.easytesting" % "fest-assert" % "1.4" % Test

libraryDependencies += "org.json" % "json" % "20160212"

libraryDependencies += "com.google.guava" % "guava" % "19.0"

libraryDependencies += "javax.media" % "jai_core" % "1.1.3" from "http://download.osgeo.org/webdav/geotools/javax/media/jai_core/1.1.3/jai_core-1.1.3.jar"

libraryDependencies += "org.geotools" % "gt-shapefile" % "14.2"

libraryDependencies += "org.geotools" % "gt-epsg-hsql" % "14.2"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
