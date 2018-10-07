name := "minikaf"

organization in ThisBuild := "com.github.anicolaspp"

scalaVersion := "2.12.2"
version := "1.0.0"

//lazy val core = project.in(file("core"))

//lazy val bench = project.in(file("bench"))
//  .dependsOn(core)
//  .enablePlugins(JmhPlugin)

lazy val minikaf = project.in(file("."))
  .settings(
    homepage := Some(url("https://github.com/anicolaspp/minikaf")),

    scmInfo := Some(ScmInfo(url("https://github.com/anicolaspp/minikaf"), "git@github.com:anicolaspp/minikaf.git")),

    pomExtra := <developers>
      <developer>
        <name>Nicolas A Perez</name>
        <email>anicolaspp@gmail.com</email>
        <organization>anicolaspp</organization>
        <organizationUrl>https://github.com/anicolaspp</organizationUrl>
      </developer>
    </developers>,

    licenses += ("MIT License", url("https://opensource.org/licenses/MIT")),

    publishMavenStyle := true,

    publishTo in ThisBuild := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),

    publishArtifact in Test := false,

    pomIncludeRepository := { _ => true }
  )


libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.10.0"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

parallelExecution in Test := false