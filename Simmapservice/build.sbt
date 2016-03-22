name := "SimmapserviceConfig"

version := "1.0"

organization := "simmapservice"

mainClass in (Compile, packageBin) := Some("simmapservice.Streetservice")

mainClass in (Compile, run) := Some("simmapservice.Streetservice")

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

libraryDependencies += "io.dropwizard" % "dropwizard-core" % "0.9.2"

libraryDependencies += "org.glassfish.jersey.media" % "jersey-media-multipart" % "2.22.2" % "provided"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "org.json" % "json" % "20160212"