package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsError, JsValue, Json}

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
repo: IncidenceRepository) extends AbstractController(cc) {

  def getAllAccount = Action.async { implicit request =>
  
    repo.getAllAccount.map { account
     =>   Ok(Json.toJson(account))
    }
  }

  def getUser(username: String) = Action.async { implicit request =>
        val result =for{
      account <-  repo.getAccountByusername(username)

    } yield (account)
        result.map {  account =>
        account match {
        case Some(x) => Ok(Json.toJson(account))
        case None => NotFound
      }
    }
  }

   def add = Action(parse.json){ request =>
  val placeResult = request.body.validate[Account]
  placeResult.fold(
    errors => {
     BadRequest(Json.obj("status" ->"BadRequest", "message" -> JsError.toJson(errors)))
    },
    account => {      
      repo.createAccount(account)          
      Ok(Json.obj("status" ->"OK", "message" -> ("User '"+account.username+"'  saved.") ))
    }
  )
}

def remove(username: String) = Action { request =>
        repo.removeAccount(username)          
        Ok("Done")
 }
}