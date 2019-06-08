package infra.http_server.api

import play.api.libs.json.JsValue

/**
  * Created by jamol on 29/07/2017.
  */
trait JsonParsable {
  def toJson: JsValue
}



