import javax.inject.Singleton

import infra.http_server.api.ErrorResponse
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.{JsObject, JsString, JsValue}
import play.api.mvc.Results._
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future
import scala.util.parsing.json.JSONObject

/**
  * Created by jamol on 31/05/2017.
  */
@Singleton
class ErrorHandler extends HttpErrorHandler{


  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful{
      val logMessage = if(statusCode == 404) s"Resource not found: Method: ${request.method}, path: ${request.path}"
      else "A client error occurred: " + message

      Logger.logger.info(logMessage)

      val msg = if(statusCode == 404) s"NotFoundError: This route don't exist!"
      else "A client error occurred: " + message

      val errorResponse = JsObject(Map(
        "error" -> JsString(msg)
      ))

      Status(statusCode)(errorResponse)
    }
  }


  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful{
      val builder = Seq.newBuilder[(String, JsValue)]
      exception match {
        case c: Throwable =>
          val errorMessage = {Logger.error("Fatal Error", exception); "A server error occurred"}
          builder += "reason" ->  JsString(errorMessage)
          exception.printStackTrace()
          val jsonData = JsObject(builder.result())

          Status(500)(jsonData)
      }
    }
  }

}
