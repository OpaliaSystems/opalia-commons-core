
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
