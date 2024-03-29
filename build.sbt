
organizationName := "Opalia Systems"

organizationHomepage := Some(url("https://opalia.systems"))

organization := "systems.opalia"

name := "commons-core"

description := "A basic collection of patterns and utilities for Scala"

homepage := Some(url("https://github.com/OpaliaSystems/opalia-commons-core"))

licenses += "Apache License, Version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt")

version := "3.1.0"

scalaVersion := "3.2.2"

scmInfo := Some(
  ScmInfo(
    url("https://github.com/OpaliaSystems/opalia-commons-core"),
    "scm:git:https://github.com/OpaliaSystems/opalia-commons-core.git",
    "scm:git:git@github.com:OpaliaSystems/opalia-commons-core.git"
  )
)

developers := List(
  Developer(
    id = "brettaufheber",
    name = "Eric Löffler",
    email = "eric.loeffler@opalia.systems",
    url = url("https://github.com/brettaufheber")
  )
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.15" % Test
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature"
)
