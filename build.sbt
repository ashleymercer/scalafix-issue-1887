ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "scalafix-issue-1887"
  )
  .aggregate(
    `scalafix-rules`,
    `scalafix-test-input`,
    `scalafix-test-output`,
    `scalafix-tests`,
  )

lazy val `scalafix-rules` = (project in file("scalafix/rules"))
  .disablePlugins(ScalafixPlugin)
  .settings(
    libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % _root_.scalafix.sbt.BuildInfo.scalafixVersion,
  )

lazy val `scalafix-test-input` = (project in file("scalafix/input"))
  .disablePlugins(ScalafixPlugin)

lazy val `scalafix-test-output` = (project in file("scalafix/output"))
  .disablePlugins(ScalafixPlugin)

lazy val `scalafix-tests` = (project in file("scalafix/tests"))
  .settings(
    scalafixTestkitInputClasspath          := (`scalafix-test-input`  / Compile / fullClasspath).value,
    scalafixTestkitInputScalaVersion       := (`scalafix-test-input`  / Compile / scalaVersion).value,
    scalafixTestkitInputScalacOptions      := (`scalafix-test-input`  / Compile / scalacOptions).value,
    scalafixTestkitInputSourceDirectories  := (`scalafix-test-input`  / Compile / sourceDirectories).value,
    scalafixTestkitOutputSourceDirectories := (`scalafix-test-output` / Compile / sourceDirectories).value,
  )
  .dependsOn(
    `scalafix-rules`,
    `scalafix-test-input`,
    `scalafix-test-output`,
  )
  .enablePlugins(ScalafixTestkitPlugin)
