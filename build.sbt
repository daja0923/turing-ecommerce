name := """turing-ecommerce"""

version := "1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(Defaults.itSettings : _*)
  .settings(itSettings)
  .configs(IntegrationTestAltConf)

scalaVersion := "2.12.8"

logLevel in compile := Level.Warn
logLevel in Test := Level.Info

lazy val IntegrationTestAltConf = config("it") extend Test


def itSettings = {
  sourceDirectories in IntegrationTest += baseDirectory.value / "it"
  sourceDirectory in IntegrationTest := baseDirectory.value / "it"
  scalaSource in IntegrationTest := baseDirectory.value / "it"
}



libraryDependencies ++=Seq(
  ehcache,
  guice,
  "com.typesafe.play" %% "play-json" % "2.6.6",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)





