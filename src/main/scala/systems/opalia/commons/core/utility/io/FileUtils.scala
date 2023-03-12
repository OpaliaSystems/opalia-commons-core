package systems.opalia.commons.core.utility.io

import java.io.{File, IOException, InputStream, OutputStream}
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.zip.{ZipEntry, ZipInputStream, ZipOutputStream}
import scala.language.reflectiveCalls
import scala.reflect.Selectable.reflectiveSelectable


object FileUtils {

  def using[A <: {def close(): Unit}, B](closeable: A)(f: A => B): B =
    try {

      f(closeable)

    } finally {

      closeable.close()
    }

  def deleteRecursively(target: File): Unit = {

    deleteRecursively(target.toPath)
  }

  def deleteRecursively(target: Path): Unit = {

    Files.walkFileTree(target, new SimpleFileVisitor[Path]() {

      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {

        Files.delete(file)
        FileVisitResult.CONTINUE
      }

      override def postVisitDirectory(directory: Path, e: IOException): FileVisitResult = {

        if (e != null)
          throw e

        Files.delete(directory)
        FileVisitResult.CONTINUE
      }
    })
  }

  def copyRecursively(source: File, target: File, options: CopyOption*): Unit = {

    copyRecursively(source.toPath, target.toPath, options: _*)
  }

  def copyRecursively(source: Path, target: Path, options: CopyOption*): Unit = {

    Files.walkFileTree(source, new SimpleFileVisitor[Path]() {

      override def preVisitDirectory(directory: Path, attrs: BasicFileAttributes): FileVisitResult = {

        Files.createDirectories(target.resolve(source.relativize(directory)))
        FileVisitResult.CONTINUE
      }

      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {

        Files.copy(file, target.resolve(source.relativize(file)), options: _*)
        FileVisitResult.CONTINUE
      }
    })
  }

  def moveRecursively(source: File, target: File, options: CopyOption*): Unit = {

    moveRecursively(source.toPath, target.toPath, options: _*)
  }

  def moveRecursively(source: Path, target: Path, options: CopyOption*): Unit = {

    Files.walkFileTree(source, new SimpleFileVisitor[Path]() {

      override def preVisitDirectory(directory: Path, attrs: BasicFileAttributes): FileVisitResult = {

        Files.createDirectories(target.resolve(source.relativize(directory)))
        FileVisitResult.CONTINUE
      }

      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {

        Files.move(file, target.resolve(source.relativize(file)), options: _*)
        FileVisitResult.CONTINUE
      }

      override def postVisitDirectory(directory: Path, e: IOException): FileVisitResult = {

        if (e != null)
          throw e

        Files.delete(directory)
        FileVisitResult.CONTINUE
      }
    })
  }

  def zip(target: Path, stream: OutputStream): Unit = {

    val zos = new ZipOutputStream(stream)

    Files.walkFileTree(target, new SimpleFileVisitor[Path]() {

      override def preVisitDirectory(directory: Path, attrs: BasicFileAttributes): FileVisitResult = {

        if (target.relativize(directory) != Paths.get("")) {

          zos.putNextEntry(new ZipEntry(target.relativize(directory).toString + "/"))
          zos.closeEntry()
        }

        FileVisitResult.CONTINUE
      }

      override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {

        zos.putNextEntry(new ZipEntry(target.relativize(file).toString))
        Files.copy(file, zos)
        zos.closeEntry()

        FileVisitResult.CONTINUE
      }
    })

    zos.finish()
  }

  def unzip(target: Path, stream: InputStream, options: CopyOption*): Unit = {

    val zis = new ZipInputStream(stream)

    LazyList.continually(zis.getNextEntry).takeWhile(_ != null).foreach {
      entry =>

        if (entry.isDirectory)
          Files.createDirectories(target.resolve(entry.getName))
        else
          Files.copy(zis, target.resolve(entry.getName), options: _*)
    }
  }
}
