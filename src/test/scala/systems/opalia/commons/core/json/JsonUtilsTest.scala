package systems.opalia.commons.core.json

import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*


class JsonUtilsTest
  extends AnyFlatSpec
    with Matchers {

  it should "be able to prettify and minify JSON correctly" in {

    val jsonMinified =
      """{"host":"petstore.com","basePath":"/v2","tags":[{"name":"pet","description":"Everything about your Pets","externalDocs":{"description":"Find out more","url":"http://petstore.com"}}],"schemes":["https","http"],"paths":{"/pet/{petId}/uploadImage":{"post":{"tags":["pet"],"summary":"uploads an image","description":"","operationId":"uploadFile","consumes":["multipart/form-data"],"produces":[],"parameters":[{"name":"petId","in":"path","description":"ID of pet to update","required":true,"type":"integer","format":"int64","default":115},{"name":"additionalMetadata","in":"formData","description":"Additional data to pass to server","required":false,"type":"string"},{"name":"file","in":"formData","description":"file to upload","required":false,"type":null}],"responses":{},"security":[{}]}}}}"""

    val jsonPrettified =
      """
        |{
        |  "host": "petstore.com",
        |  "basePath": "/v2",
        |  "tags": [
        |    {
        |      "name": "pet",
        |      "description": "Everything about your Pets",
        |      "externalDocs": {
        |        "description": "Find out more",
        |        "url": "http://petstore.com"
        |      }
        |    }
        |  ],
        |  "schemes": [
        |    "https",
        |    "http"
        |  ],
        |  "paths": {
        |    "/pet/{petId}/uploadImage": {
        |      "post": {
        |        "tags": [
        |          "pet"
        |        ],
        |        "summary": "uploads an image",
        |        "description": "",
        |        "operationId": "uploadFile",
        |        "consumes": [
        |          "multipart/form-data"
        |        ],
        |        "produces": [],
        |        "parameters": [
        |          {
        |            "name": "petId",
        |            "in": "path",
        |            "description": "ID of pet to update",
        |            "required": true,
        |            "type": "integer",
        |            "format": "int64",
        |            "default": 115
        |          },
        |          {
        |            "name": "additionalMetadata",
        |            "in": "formData",
        |            "description": "Additional data to pass to server",
        |            "required": false,
        |            "type": "string"
        |          },
        |          {
        |            "name": "file",
        |            "in": "formData",
        |            "description": "file to upload",
        |            "required": false,
        |            "type": null
        |          }
        |        ],
        |        "responses": {},
        |        "security": [
        |          {}
        |        ]
        |      }
        |    }
        |  }
        |}
        |
      """.stripMargin.trim

    JsonUtils.minify(jsonMinified) shouldBe jsonMinified
    JsonUtils.minify(jsonPrettified) shouldBe jsonMinified
    JsonUtils.minify(JsonUtils.minify(jsonMinified)) shouldBe jsonMinified
    JsonUtils.minify(JsonUtils.minify(jsonPrettified)) shouldBe jsonMinified

    JsonUtils.prettify(jsonMinified) shouldBe jsonPrettified
    JsonUtils.prettify(jsonPrettified) shouldBe jsonPrettified
    JsonUtils.prettify(JsonUtils.prettify(jsonMinified)) shouldBe jsonPrettified
    JsonUtils.prettify(JsonUtils.prettify(jsonPrettified)) shouldBe jsonPrettified
  }

  it should "be able to escape strings for JSON serialization" in {

    val unescaped =
      (for (x <- 0 until 128) yield x.toChar).mkString(",")

    val escaped =
      "\\u0000,\\u0001,\\u0002,\\u0003,\\u0004,\\u0005,\\u0006,\\u0007,\\b,\\t,\\n,\\u000B,\\f,\\r,\\u000E,\\u000F," +
        "\\u0010,\\u0011,\\u0012,\\u0013,\\u0014,\\u0015,\\u0016,\\u0017,\\u0018,\\u0019,\\u001A,\\u001B,\\u001C," +
        "\\u001D,\\u001E,\\u001F, ,!,\\\",#,$,%,&,',(,),*,+,,,-,.,/,0,1,2,3,4,5,6,7,8,9,:,;,<,=,>,?,@,A,B,C,D,E,F," +
        "G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,[,\\\\,],^,_,`,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y," +
        "z,{,|,},~,\\u007F"

    JsonUtils.escape(unescaped) shouldBe escaped
  }
}
