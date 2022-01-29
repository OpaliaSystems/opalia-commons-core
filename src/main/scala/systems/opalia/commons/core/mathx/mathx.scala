package systems.opalia.commons.core.mathx


def log(base: Double, value: Double): Double =
  math.log(value) / math.log(base)

def digitCount(base: Double, value: Double): Int =
  math.floor(log(base, value)).toInt + 1

def round(value: Double, pos: Int): Double = {

  val factor = math.pow(10d, pos)

  math.round(factor * value) / factor
}

def floor(value: Double, pos: Int): Double = {

  val factor = math.pow(10d, pos)

  math.floor(factor * value) / factor
}

def ceil(value: Double, pos: Int): Double = {

  val factor = math.pow(10d, pos)

  math.ceil(factor * value) / factor
}

// normalize percent in interval [0, 1]
def normalizePercent(value: Double): Double =
  math.max(0d, math.min(1d, value))

// normalize angle in interval [0,2π]
def normalizeAngle1(value: Double): Double = {

  val angle = value % (2d * math.Pi)

  if (angle < 0d)
    angle + (2d * math.Pi)
  else
    angle
}

// normalize angle in interval [-π,+π]
def normalizeAngle2(value: Double): Double = {

  val angle = value % (2d * math.Pi)

  if (angle > math.Pi)
    angle - 2d * math.Pi
  else if (angle < -math.Pi)
    angle + 2d * math.Pi
  else
    angle
}

// normalize angle in interval [-π/2,+π/2]
def normalizeAngle3(value: Double): Double = {

  val x = if (math.abs(value) % (2d * math.Pi) > math.Pi) -1d else 1d
  val angle = value % math.Pi

  if (angle > math.Pi / 2d)
    (math.Pi - angle) * x
  else if (angle < -math.Pi / 2d)
    (-math.Pi - angle) * x
  else
    angle * x
}

def parseUnsignedLong(chars: Iterable[Char], radix: Int, numberOfBytes: Int): Long = {

  def getNumericValue(char: Char): Int = {

    val value = Character.getNumericValue(char)

    if (value < 0 || value >= radix)
      throw new NumberFormatException("A digit value is out of range.")

    value
  }

  if (chars.isEmpty)
    throw new NumberFormatException("Expect non empty sequence of characters.")

  if (radix < 2 || radix > 36)
    throw new NumberFormatException("The radix value is out of range.")

  if (numberOfBytes <= 0 || numberOfBytes > java.lang.Long.BYTES)
    throw new NumberFormatException("The number of allowed bytes is out of range.")

  val max = digitCount(radix, math.pow(2, numberOfBytes * 8) - 1)

  if (chars.size > max)
    throw new NumberFormatException("The characters sequence is too long.")

  chars.foldLeft(0L)((a, b) => a * radix + getNumericValue(b))
}
