package models

import javax.inject.{Inject,Singleton}

case class Account ( username : String, email:String, image: String)


object Account{
import play.api.libs.json._
    
  implicit val AccountWrites = new Writes[Account] 
{

    def writes(account: Account)= Json.obj(
        "username" -> account.username,
        "emailAddress" -> account.email,
        "image" -> account.image)
      }


  implicit val AccountReads = new Reads[Account] 
{
        def reads(json: JsValue): JsResult[Account] = {
        val username = (json \ "username").as[String]
        val email = (json \ "emailAddress").as[String]
        val image = (json\ "image").as[String]
        JsSuccess(Account(username,email,image))
    }
}

}