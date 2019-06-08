package infra.http_server

import infra.http_server.api.{ErrorResponse, JsonResponse}
import play.api.Application
import play.api.data.{Form, Mapping}
import play.api.libs.json._
import play.api.mvc.{InjectedController, Request, Result}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by jamol on 03/04/2017.
 */
trait JsonController extends InjectedController{

  protected implicit def currentApp: Application


  protected def parseJsonDataToResult[T](mapping:Mapping[T])(process:Form[T] => Future[JsonResponse])(implicit request:Request[JsValue]):Future[Result] = {
    val bindedForm = Form(mapping).bindFromRequest()

    if(bindedForm.hasErrors){
      val error = bindedForm.errors.head
      val field = error.key
      val errorMessage = error.message

      Future(parseErrorResult(ErrorResponse(400, error.args.head.toString, errorMessage, Some(field))))
    }
    else process(bindedForm) map apiJsonToResult
  }


  private def apiJsonToResult(response: JsonResponse):Result = {
    Status(response.status)(response.toJson)
  }


  private def parseErrorResult(response: ErrorResponse):Result = {
    Status(response.status)(response.toJson)
  }


}
