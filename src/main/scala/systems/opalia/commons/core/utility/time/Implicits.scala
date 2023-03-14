package systems.opalia.commons.core.utility.time

import java.time.temporal.{ChronoUnit, TemporalAmount}
import java.time.{Duration as JDuration, *}
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.{Duration, FiniteDuration}


object Implicits {

  implicit class IntToPeriodConversion(x: Int) {

    def dayAsPeriod: Period =
      daysAsPeriod

    def daysAsPeriod: Period =
      Period.ofDays(x)

    def weekAsPeriod: Period =
      weeksAsPeriod

    def weeksAsPeriod: Period =
      Period.ofWeeks(x)

    def monthAsPeriod: Period =
      monthsAsPeriod

    def monthsAsPeriod: Period =
      Period.ofMonths(x)

    def yearAsPeriod: Period =
      yearsAsPeriod

    def yearsAsPeriod: Period =
      Period.ofYears(x)
  }

  implicit class ConcurrentDurationAdaptation(x: Duration) {

    def chronoUnit: ChronoUnit =
      x.unit match {
        case TimeUnit.DAYS => ChronoUnit.DAYS
        case TimeUnit.HOURS => ChronoUnit.HOURS
        case TimeUnit.MINUTES => ChronoUnit.MINUTES
        case TimeUnit.SECONDS => ChronoUnit.SECONDS
        case TimeUnit.MILLISECONDS => ChronoUnit.MILLIS
        case TimeUnit.MICROSECONDS => ChronoUnit.MICROS
        case TimeUnit.NANOSECONDS => ChronoUnit.NANOS
      }

    def asJava: JDuration =
      JDuration.of(x.length, x.chronoUnit)
  }

  implicit class JavaDurationAdaptation(x: JDuration)
    extends Ordered[JDuration] {

    def +(that: JDuration): JDuration =
      x.plus(that)

    def -(that: JDuration): JDuration =
      x.minus(that)

    def +(that: FiniteDuration): JDuration =
      x.plus(that.length, that.chronoUnit)

    def -(that: FiniteDuration): JDuration =
      x.minus(that.length, that.chronoUnit)

    def *(scalar: Long): JDuration =
      x.multipliedBy(scalar)

    def /(divisor: Long): JDuration =
      x.dividedBy(divisor)

    def unary_! : JDuration =
      x.negated

    def compare(that: JDuration): Int =
      x.compareTo(that)
  }

  implicit class JavaPeriodAdaptation(x: Period) {

    def +(that: Period): Period =
      x.plus(that)

    def -(that: Period): Period =
      x.minus(that)

    def *(scalar: Int): Period =
      x.multipliedBy(scalar)

    def unary_! : Period =
      x.negated
  }

  implicit class JavaInstantAdaptation(x: Instant)
    extends Ordered[Instant] {

    def +(that: TemporalAmount): Instant =
      x.plus(that)

    def -(that: TemporalAmount): Instant =
      x.minus(that)

    def +(that: FiniteDuration): Instant =
      x.plus(that.length, that.chronoUnit)

    def -(that: FiniteDuration): Instant =
      x.minus(that.length, that.chronoUnit)

    def compare(that: Instant): Int =
      x.compareTo(that)
  }

  implicit class JavaOffsetDateTimeAdaptation(x: OffsetDateTime)
    extends Ordered[OffsetDateTime] {

    def +(that: TemporalAmount): OffsetDateTime =
      x.plus(that)

    def -(that: TemporalAmount): OffsetDateTime =
      x.minus(that)

    def +(that: FiniteDuration): OffsetDateTime =
      x.plus(that.length, that.chronoUnit)

    def -(that: FiniteDuration): OffsetDateTime =
      x.minus(that.length, that.chronoUnit)

    def compare(that: OffsetDateTime): Int =
      x.compareTo(that)
  }

  implicit class JavaOffsetTimeAdaptation(x: OffsetTime)
    extends Ordered[OffsetTime] {

    def +(that: TemporalAmount): OffsetTime =
      x.plus(that)

    def -(that: TemporalAmount): OffsetTime =
      x.minus(that)

    def +(that: FiniteDuration): OffsetTime =
      x.plus(that.length, that.chronoUnit)

    def -(that: FiniteDuration): OffsetTime =
      x.minus(that.length, that.chronoUnit)

    def compare(that: OffsetTime): Int =
      x.compareTo(that)
  }
}
