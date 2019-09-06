package systems.opalia.commons.core.vfs

import java.io.InputStream
import java.time.Instant


trait FileContent {

  def id: Seq[Byte]

  def fileName: String

  def fileSize: Long

  def objectSize: Long

  def contentType: String

  def timestamp: Instant

  def checksum: Checksum

  def read(checkIntegrity: Boolean): InputStream

  def validate(failIfFalse: Boolean): Boolean
}
