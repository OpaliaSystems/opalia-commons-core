package systems.opalia.commons.core.vfs

import java.io.OutputStream


trait FileSystem {

  def name: String

  def commit(id: Seq[Byte]): Unit

  def committed(id: Seq[Byte]): Boolean

  def exists(id: Seq[Byte]): Boolean

  def delete(id: Seq[Byte]): Boolean

  def create(id: Array[Byte],
             fileName: String = "",
             contentType: String = "",
             checksum: Option[Checksum] = None): OutputStream

  def fetchFileContent(id: Seq[Byte]): FileContent
}
