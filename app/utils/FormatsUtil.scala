package utils

import java.net.URL

import infra.http_server.api.JsonParsable
import play.api.data.FormError
import play.api.data.format.Formats._
import play.api.data.format.Formatter
import play.api.libs.json._

/**
  * Created by jamol on 16/10/2017.
  */
object FormatsUtil {
  implicit object UrlFormatter extends Formatter[URL] {

    override val format = Some(("format.url", Nil))

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], URL] ={
      val updatedData = data.map{
        case (k, v) => if(k equals key){
          val url = if(!v.startsWith("http")) "http://" + v else v
          (k, url)
        }
        else (k, v)
      }
      parsing(new URL(_), "error.url", Nil)(key, updatedData)
    }


    override def unbind(key: String, value: URL) = Map(key -> value.toString)
  }


  implicit class ListToJson[A <: JsonParsable](list: Seq[A]){
    def toJson: JsValue = JsArray(list.map(_.toJson))
  }

  implicit class ListToApiJson[A <: JsonParsable](list: Seq[A]){
    def toApiJson: JsValue = JsArray(list.map(_.toJson))
  }

  implicit class BooleanToJson(bool: Boolean){
    def toJson: JsValue = JsBoolean(bool)
  }

  implicit class StringToJson(str: String){
    def toJson: JsValue = JsString(str)
  }

  implicit class LongToJson(value: Long){
    def toJson: JsValue = JsNumber(value)
  }

  implicit class IntToJson(value: Int){
    def toJson: JsValue = JsNumber(value)
  }

  implicit class OptionStringJson(value: Option[String]){
    def toJson: JsValue = value.map(_.toJson).getOrElse(JsNull)
  }

  implicit class OptionIntJson(value: Option[Int]){
    def toJson: JsValue = value.map(_.toJson).getOrElse(JsNull)
  }

  implicit class OptionLongJson(value: Option[Long]){
    def toJson: JsValue = value.map(_.toJson).getOrElse(JsNull)
  }

  implicit class OptionJson[A <: JsonParsable](value: Option[A]){
    def toJson: JsValue = value.map(_.toJson).getOrElse(JsNull)
  }
}
