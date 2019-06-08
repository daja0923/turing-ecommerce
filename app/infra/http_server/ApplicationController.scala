package infra.http_server

import javax.inject.Inject

import infra.http_server.api.JsonResponse
import play.api.Application
import play.api.data.Form
import play.api.data.Forms.tuple
import play.api.mvc.{Action, AnyContent}
import play.api.data.Forms.{email, _}
import play.api.data.{Form, Forms}
import play.api.libs.json.{JsObject, JsString, JsValue}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import infra.http_server.api.FormMappings._

class ApplicationController @Inject()(implicit val currentApp: Application) extends  JsonController{


  def checkErrorResponse: Action[JsValue] = Action.async(parse.json){implicit request =>
    val mapping = tuple(
      "email" -> emailMapping,
      "password" -> passwordMapping
    )

    parseJsonDataToResult(mapping){formData =>
      val (email, data) = formData.bindFromRequest().get
      Future(new JsonResponse {
        override def status: Int = 200

        override def toJson: JsValue = JsObject(Map(
          "message" -> JsString("Success")
        ))
      })
    }
  }
}
