name := "core"
scalaVersion := "2.12.2"
version := "1.0.0"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.10.0"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

parallelExecution in Test := false

publishArtifact := false