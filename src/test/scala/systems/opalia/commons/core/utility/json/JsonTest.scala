package systems.opalia.commons.core.utility.json

import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*


class JsonTest
  extends AnyFlatSpec
    with Matchers {

  it should "be able to serialize a JSON AST to a string" in {

    val json =
      """
        |{
        |  "string": "text",
        |  "number": 42.73,
        |  "boolean1": true,
        |  "boolean2": false,
        |  "any": null,
        |  "array": [1, 2, 3],
        |  "object": {"key": "value"}
        |}
        |
      """.stripMargin.trim

    val node =
      Json.obj(
        "string" -> "text",
        "number" -> 42.73,
        "boolean1" -> true,
        "boolean2" -> false,
        "any" -> Json.NullNode,
        "array" -> Json.arr(1, 2, 3),
        "object" -> Json.obj("key" -> "value")
      )

    node.toString shouldBe JsonUtils.minify(json)
  }
}
