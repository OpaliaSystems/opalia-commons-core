package systems.opalia.commons.core.utility.rendering

import java.nio.ByteOrder
import java.nio.charset.Charset


sealed trait Renderable
  extends Serializable

trait StringRenderable
  extends Renderable {

  override final def toString: String =
    renderString(new StringRenderer()).result()

  final def toByteString(implicit charset: Charset = Renderer.appDefaultCharset): Vector[Byte] =
    renderString(new StringRenderer()).result().getBytes(charset).toVector

  def renderString(renderer: StringRenderer): StringRenderer
}

trait ByteRenderable
  extends Renderable {

  final def toBytes(implicit charset: Charset = Renderer.appDefaultCharset,
                    byteOrder: ByteOrder = Renderer.appDefaultByteOrder): Vector[Byte] =
    renderBytes(new ByteRenderer(charset, byteOrder)).result()

  def renderBytes(renderer: ByteRenderer): ByteRenderer
}
