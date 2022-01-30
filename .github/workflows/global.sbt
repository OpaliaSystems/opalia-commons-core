
sonarProperties ++= Map(
  "sonar.host.url" -> "https://sonarcloud.io",
  "sonar.projectKey" -> "OpaliaSystems_opalia-commons-core",
  "sonar.organization" -> "opalia-systems",
  "sonar.sourceEncoding" -> "UTF-8",
  "sonar.sources" -> "src/main/scala",
  "sonar.tests" -> "src/test/scala",
  "sonar.junit.reportPaths" -> "target/test-reports",
  "sonar.coverage.jacoco.xmlReportPaths" -> s"target/scala-${scalaVersion.value}/jacoco/report/jacoco.xml",
  "sonar.scala.version" -> scalaVersion.value
)

Test / jacocoReportSettings := JacocoReportSettings().withFormats(JacocoReportFormats.XML)

Test / publishArtifact := false

publishMavenStyle := true

publishTo := {
  if (isSnapshot.value)
    Some("snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots/")
  else
    Some("releases" at "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
}

credentials ++= (
  if (sys.env.contains("OSSRH_USERNAME") && sys.env.contains("OSSRH_PASSWORD"))
    Seq(Credentials(
      "Sonatype Nexus Repository Manager",
      "s01.oss.sonatype.org",
      sys.env("OSSRH_USERNAME"),
      sys.env("OSSRH_PASSWORD")
    ))
  else
    Seq.empty
  )

credentials ++= (
  if (sys.env.contains("GPG_KEY_NAME"))
    Seq(Credentials(
      "GnuPG Key ID",
      "gpg",
      sys.env("GPG_KEY_NAME"),
      "ignored"
    ))
  else
    Seq.empty
  )
