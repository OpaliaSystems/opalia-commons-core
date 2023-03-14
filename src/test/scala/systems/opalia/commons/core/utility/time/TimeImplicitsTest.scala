package systems.opalia.commons.core.utility.time

import java.time.temporal.ChronoUnit
import java.time.{Duration as JDuration, *}
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import scala.concurrent.duration.*
import scala.language.postfixOps
import systems.opalia.commons.core.utility.time.Implicits.*


class TimeImplicitsTest
  extends AnyFlatSpec
    with Matchers {

  it should "offer an adaptation for java.time.Duration to better integrate with the Scala language" in {

    val duration1 = JDuration.ofNanos(999)
    val duration2 = duration1.plus(3, ChronoUnit.DAYS)

    val scalaDuration = 3 days
    val javaDuration = (3 days).asJava

    (duration1 + scalaDuration) should be(duration2)
    (duration1 + javaDuration) should be(duration2)

    (duration1 * 2) should be((1998 nanoseconds).asJava)
    (duration1 / 2) should be((499 nanoseconds).asJava)
    (!duration1) should be((-999 nanoseconds).asJava)

    duration1 == duration2 should be(false)
    duration1 != duration2 should be(true)
    duration1 < duration2 should be(true)
    duration1 <= duration2 should be(true)
    duration1 > duration2 should be(false)
    duration1 >= duration2 should be(false)
  }

  it should "offer an adaptation for java.time.Period to better integrate with the Scala language" in {

    val period = 28 weeksAsPeriod

    (period + period) should be(56 weeksAsPeriod)

    (period * 2) should be(56 weeksAsPeriod)
    (!period) should be(-28 weeksAsPeriod)
  }

  it should "offer an adaptation for java.time.Instant to better integrate with the Scala language" in {

    val instant1 = Instant.EPOCH
    val instant2 = instant1.plus(73, ChronoUnit.DAYS)

    val scalaDuration = 73 days
    val javaDuration = (73 days).asJava
    val javaPeriod = 73 daysAsPeriod

    (instant1 + scalaDuration) should be(instant2)
    (instant1 + javaDuration) should be(instant2)
    (instant1 + javaPeriod) should be(instant2)

    instant1 == instant2 should be(false)
    instant1 != instant2 should be(true)
    instant1 < instant2 should be(true)
    instant1 <= instant2 should be(true)
    instant1 > instant2 should be(false)
    instant1 >= instant2 should be(false)
  }

  it should "offer an adaptation for java.time.OffsetDateTime to better integrate with the Scala language" in {

    val dateTime1 = Instant.EPOCH.atOffset(ZoneOffset.UTC)
    val dateTime2 = dateTime1.plus(42, ChronoUnit.DAYS)

    val scalaDuration = 42 days
    val javaDuration = (42 days).asJava
    val javaPeriod = 42 daysAsPeriod

    (dateTime1 + scalaDuration) should be(dateTime2)
    (dateTime1 + javaDuration) should be(dateTime2)
    (dateTime1 + javaPeriod) should be(dateTime2)

    dateTime1 == dateTime2 should be(false)
    dateTime1 != dateTime2 should be(true)
    dateTime1 < dateTime2 should be(true)
    dateTime1 <= dateTime2 should be(true)
    dateTime1 > dateTime2 should be(false)
    dateTime1 >= dateTime2 should be(false)
  }

  it should "offer an adaptation for java.time.OffsetTime to better integrate with the Scala language" in {

    val time1 = OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC)
    val time2 = time1.plus(42, ChronoUnit.HOURS)

    val scalaDuration = 42 hours
    val javaDuration = (42 hours).asJava

    (time1 + scalaDuration) should be(time2)
    (time1 + javaDuration) should be(time2)

    time1 == time2 should be(false)
    time1 != time2 should be(true)
    time1 < time2 should be(true)
    time1 <= time2 should be(true)
    time1 > time2 should be(false)
    time1 >= time2 should be(false)
  }
}
