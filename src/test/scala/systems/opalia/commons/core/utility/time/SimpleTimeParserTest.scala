package systems.opalia.commons.core.utility.time

import java.time.{Duration as JDuration, *}
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import scala.concurrent.duration.*
import scala.language.postfixOps


class SimpleTimeParserTest
  extends AnyFlatSpec
    with Matchers {

  val dateAsEpochMilli =
    OffsetDateTime.of(2011, 2, 3, 0, 0, 0, 0, ZoneOffset.UTC).toInstant.toEpochMilli

  val dateParts =
    List(
      ("2011-02-03", dateAsEpochMilli),
      ("2011-034", dateAsEpochMilli),
      ("2011-W05-4", dateAsEpochMilli)
    )

  val timeParts =
    List(
      ("04:15", ((4 hours) + (15 minutes)).toMillis),
      ("04:15:30", ((4 hours) + (15 minutes) + (30 seconds)).toMillis),
      ("04:15:30.432", ((4 hours) + (15 minutes) + (30 seconds) + (432 milliseconds)).toMillis)
    )

  val timePartsForDateTime =
    timeParts.map(x => ("T" + x._1) -> x._2) :+ ("" -> 0L)

  val offsetParts =
    List(
      ("+01:00", -(1 hour).toMillis),
      ("+01:30", -((1 hour) + (30 minutes)).toMillis),
      ("+01:30:20", -((1 hour) + (30 minutes) + (20 seconds)).toMillis),
      ("-08:00", (8 hours).toMillis),
      ("Z", 0L),
      ("", 0L)
    )

  it should "allow to parse local time" in {

    for (a <- timeParts) {

      JDuration.between(
        LocalTime.of(0, 0, 0, 0),
        SimpleTimeParser.parseLocalTime(a._1)
      ) shouldBe JDuration.ofMillis(a._2)
    }
  }

  it should "allow to parse offset time" in {

    for (a <- timeParts; b <- offsetParts) {

      JDuration.between(
        OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC),
        SimpleTimeParser.parseOffsetTime(a._1 + b._1)
      ) shouldBe JDuration.ofMillis(a._2 + b._2)
    }
  }

  it should "allow to parse local date-time" in {

    for (a <- dateParts; b <- timePartsForDateTime) {

      SimpleTimeParser.parseLocalDateTime(a._1 + b._1).toInstant(ZoneOffset.UTC) shouldBe
        Instant.ofEpochMilli(a._2 + b._2)
    }
  }

  it should "allow to parse offset date-time" in {

    for (a <- dateParts; b <- timePartsForDateTime; c <- offsetParts) {

      SimpleTimeParser.parseOffsetDateTime(a._1 + b._1 + c._1).toInstant shouldBe
        Instant.ofEpochMilli(a._2 + b._2 + c._2)
    }
  }
}
