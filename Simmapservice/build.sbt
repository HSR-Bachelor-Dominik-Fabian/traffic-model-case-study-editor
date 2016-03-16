name := "SimmapserviceConfig"

version := "1.0"

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

libraryDependencies += "io.dropwizard" % "dropwizard-core" % "0.9.2"
