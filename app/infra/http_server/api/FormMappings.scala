package infra.http_server.api

import play.api.data.Forms.of
import play.api.data.Mapping
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.data.format.Formats._

object FormMappings {

  private val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r

  private val emailConstraint: Constraint[String] = Constraint("constraint.email")({plainText =>
    if(plainText == null || plainText.trim.isEmpty)
      Invalid(ValidationError("Email field is empty", "USR_02"))
    else emailRegex.findFirstMatchIn(plainText)
      .map(_ => Valid)
      .getOrElse(Invalid(ValidationError("Invalid email", "USR_03")))
  })

  private val passwordConstraint: Constraint[String] = Constraint("constraint.password")({ plainText =>
      if(plainText.length < 5)
        Invalid(ValidationError("Password is too short", "USR_01"))
      else if(plainText.length > 100)
        Invalid(ValidationError("Password is too long", "USR_07"))
      else Valid


  })

  def emailMapping: Mapping[String] = of[String] verifying emailConstraint


  def passwordMapping: Mapping[String] = of[String] verifying passwordConstraint


}
