package infra.http_server.api

import play.api.libs.json.JsObject
import scala.collection.immutable.ListMap
import utils.FormatsUtil._

trait JsonResponse extends JsonParsable{
  def status:Int
}


case class ErrorResponse(status: Int, code: String, message: String, field: Option[String]) extends JsonResponse {
  override def toJson = JsObject(Map(
    "error" -> JsObject (ListMap(
      "status" -> status.toJson,
      "code" -> code.toJson,
      "message" -> message.toJson,
      "field" -> field.toJson
    ))
  ))
}
