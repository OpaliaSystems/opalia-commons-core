package systems.opalia.commons.core.utility.io

import java.io.*
import java.nio.charset.Charset
import java.util.Properties
import scala.jdk.CollectionConverters.*


object PropertiesUtils {

  def fromResource(resource: String, classLoader: ClassLoader,
                   charset: Charset = Charset.defaultCharset): Map[String, String] =
    Option(classLoader.getResourceAsStream(resource)) match {
      case Some(source) => fromInputStream(source, charset)
      case None => throw new FileNotFoundException(s"Cannot find resource $resource")
    }

  def fromFile(file: String, charset: Charset = Charset.defaultCharset): Map[String, String] =
    fromFileHandle(new File(file), charset)

  def fromFileHandle(source: File, charset: Charset = Charset.defaultCharset): Map[String, String] =
    fromInputStream(new FileInputStream(source), charset)

  def fromInputStream(source: InputStream, charset: Charset = Charset.defaultCharset): Map[String, String] =
    fromReader(new InputStreamReader(source, charset))

  def fromString(source: String): Map[String, String] =
    fromReader(new StringReader(source))

  def fromReader(source: Reader): Map[String, String] = {

    val properties = new Properties()

    FileUtils.using(source) {
      reader =>

        properties.load(reader)
    }

    properties.asScala.toMap
  }

  def fromSystemProperties(keys: Seq[String]): Map[String, String] =
    keys.flatMap(key => sys.props.get(key).map(value => key -> value)).toMap
}
