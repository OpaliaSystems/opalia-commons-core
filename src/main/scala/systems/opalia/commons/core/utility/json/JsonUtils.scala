package systems.opalia.commons.core.utility.json


object JsonUtils {

  // https://www.rfc-editor.org/rfc/rfc8259

  def escape(str: String): String = {

    val sbw = StringBuilderWrapper(str)

    sbw.run {
      case '"' | '\\' =>
        sbw.sb.append('\\')
        sbw.append()
      case '\b' =>
        sbw.sb.append("\\b")
        sbw.skip()
      case '\f' =>
        sbw.sb.append("\\f")
        sbw.skip()
      case '\n' =>
        sbw.sb.append("\\n")
        sbw.skip()
      case '\r' =>
        sbw.sb.append("\\r")
        sbw.skip()
      case '\t' =>
        sbw.sb.append("\\t")
        sbw.skip()
      case x if (x.isControl) =>
        sbw.sb.append(f"\\u${x.toInt}%04X")
        sbw.skip()
      case _ =>
        sbw.append()
    }

    sbw.sb.toString()
  }

  def minify(str: String): String = {

    val sbw = StringBuilderWrapper(str)
    var quotes = false

    sbw.run {
      case '"' =>
        sbw.append()
        quotes = !quotes
      case '\\' if (quotes) =>
        sbw.append()
        sbw.append()
      case _ if (quotes) =>
        sbw.append()
      case x if (x.isWhitespace) =>
        sbw.skip()
      case _ =>
        sbw.append()
    }

    sbw.sb.toString()
  }

  def prettify(str: String): String =
    prettify(str, 2)

  def prettify(str: String, indentSize: Int): String = {

    val sbw = StringBuilderWrapper(str)
    var quotes = false
    var depth = 0

    def indent(): Unit =
      for (_ <- 1 to (depth * indentSize))
        sbw.sb.append(" ")

    sbw.run {
      case '"' =>
        sbw.append()
        quotes = !quotes
      case '\\' if (quotes) =>
        sbw.append()
        sbw.append()
      case _ if (quotes) =>
        sbw.append()
      case x if (x.isWhitespace) =>
        sbw.skip()
      case ':' =>
        sbw.append()
        sbw.sb.append(' ')
      case ',' =>
        sbw.append()
        sbw.sb.append('\n')
        indent()
      case '{' | '[' if (sbw.isNext('}') || sbw.isNext(']')) =>
        sbw.append()
        sbw.append()
      case '{' | '[' =>
        sbw.append()
        sbw.sb.append('\n')
        depth += 1
        indent()
      case '}' | ']' =>
        sbw.sb.append('\n')
        depth -= 1
        indent()
        sbw.append()
      case _ =>
        sbw.append()
    }

    sbw.sb.toString()
  }

  private class StringBuilderWrapper(str: String) {

    val sb: StringBuilder = StringBuilder()

    private var index = 0

    def isNext(c: Char): Boolean =
      index + 1 < str.length && str.charAt(index + 1) == c

    def append(): Unit =
      if (index < str.length) {
        sb.append(str.charAt(index))
        index += 1
      }

    def skip(): Unit =
      index += 1

    def run(f: (Char) => Unit): Unit = {

      index = 0

      while (index < str.length) {

        f(str.charAt(index))
      }
    }
  }
}
