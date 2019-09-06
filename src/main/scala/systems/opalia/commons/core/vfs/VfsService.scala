package systems.opalia.commons.core.vfs


trait VfsService {

  def getFileSystem(name: String): FileSystem
}
