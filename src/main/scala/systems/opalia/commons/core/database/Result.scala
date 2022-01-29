package systems.opalia.commons.core.database

import systems.opalia.commons.core.cursor.impl.VersatileCursor


trait Result {

  val columns: IndexedSeq[String]
  val cursor: VersatileCursor[Row]
}
