package systems.opalia.commons.core.utility.time

import java.time.*
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder, SignStyle}
import java.time.temporal.{ChronoField, IsoFields}
import scala.util.Try


object SimpleTimeParser {

  /*
    offsetDateTime          = dateTime [offset]
    offsetOrdinalDateTime   = ordinalDateTime [offset]
    offsetWeekDateTime      = weekDateTime [offset]

    dateTime                = date ['T' time]
    ordinalDateTime         = ordinalDate ['T' time]
    weekDateTime            = weekDate ['T' time]

    date                    = yyyy '-' MM '-' dd
    ordinalDate             = yyyy '-' DDD
    weekDate                = yyyy '-' 'W' ww '-' e

    offsetTime              = time [offset]

    time                    = HH ':' mm [':' ss [fraction]]
  */

  val offset: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .appendOffsetId()
      .toFormatter()

  val fraction: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
      .toFormatter()

  val time: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .appendValue(ChronoField.HOUR_OF_DAY, 2)
      .appendLiteral(':')
      .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .optionalStart()
      .appendLiteral(':')
      .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
      .appendOptional(fraction)
      .optionalEnd()
      .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
      .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
      .toFormatter()

  val offsetTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(time)
      .appendOptional(offset)
      .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
      .toFormatter()

  val date: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .toFormatter()

  val ordinalDate: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_YEAR, 3)
      .toFormatter()

  val weekDate: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .parseCaseInsensitive
      .appendValue(IsoFields.WEEK_BASED_YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendLiteral("W")
      .appendValue(IsoFields.WEEK_OF_WEEK_BASED_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_WEEK, 1)
      .toFormatter()

  val dateTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(date)
      .optionalStart()
      .appendLiteral('T')
      .append(time)
      .optionalEnd()
      .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
      .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
      .toFormatter()

  val ordinalDateTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(ordinalDate)
      .optionalStart()
      .appendLiteral('T')
      .append(time)
      .optionalEnd()
      .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
      .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
      .toFormatter()

  val weekDateTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(weekDate)
      .optionalStart()
      .appendLiteral('T')
      .append(time)
      .optionalEnd()
      .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
      .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
      .toFormatter()

  val offsetDateTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(dateTime)
      .appendOptional(offset)
      .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
      .toFormatter()

  val offsetOrdinalDateTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(ordinalDateTime)
      .appendOptional(offset)
      .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
      .toFormatter()

  val offsetWeekDateTime: DateTimeFormatter =
    (new DateTimeFormatterBuilder)
      .append(weekDateTime)
      .appendOptional(offset)
      .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
      .toFormatter()

  def parseLocalTime(string: String): LocalTime = {

    val result = time.parse(string)

    LocalTime.of(
      result.get(ChronoField.HOUR_OF_DAY),
      result.get(ChronoField.MINUTE_OF_HOUR),
      result.get(ChronoField.SECOND_OF_MINUTE),
      result.get(ChronoField.NANO_OF_SECOND)
    )
  }

  def parseOffsetTime(string: String): OffsetTime = {

    val result = offsetTime.parse(string)

    OffsetTime.of(
      result.get(ChronoField.HOUR_OF_DAY),
      result.get(ChronoField.MINUTE_OF_HOUR),
      result.get(ChronoField.SECOND_OF_MINUTE),
      result.get(ChronoField.NANO_OF_SECOND),
      ZoneOffset.ofTotalSeconds(result.get(ChronoField.OFFSET_SECONDS))
    )
  }

  def parseLocalDateTime(string: String): LocalDateTime = {

    val result =
      Try(dateTime.parse(string))
        .getOrElse(Try(ordinalDateTime.parse(string))
          .getOrElse(Try(weekDateTime.parse(string))
            .get))

    LocalDateTime.of(
      result.get(ChronoField.YEAR),
      result.get(ChronoField.MONTH_OF_YEAR),
      result.get(ChronoField.DAY_OF_MONTH),
      result.get(ChronoField.HOUR_OF_DAY),
      result.get(ChronoField.MINUTE_OF_HOUR),
      result.get(ChronoField.SECOND_OF_MINUTE),
      result.get(ChronoField.NANO_OF_SECOND)
    )
  }

  def parseOffsetDateTime(string: String): OffsetDateTime = {

    val result =
      Try(offsetDateTime.parse(string))
        .getOrElse(Try(offsetOrdinalDateTime.parse(string))
          .getOrElse(Try(offsetWeekDateTime.parse(string))
            .get))

    OffsetDateTime.of(
      result.get(ChronoField.YEAR),
      result.get(ChronoField.MONTH_OF_YEAR),
      result.get(ChronoField.DAY_OF_MONTH),
      result.get(ChronoField.HOUR_OF_DAY),
      result.get(ChronoField.MINUTE_OF_HOUR),
      result.get(ChronoField.SECOND_OF_MINUTE),
      result.get(ChronoField.NANO_OF_SECOND),
      ZoneOffset.ofTotalSeconds(result.get(ChronoField.OFFSET_SECONDS))
    )
  }
}
