package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsError, JsValue, Json}
import org.joda.time.DateTime
import org.joda.time.format._
import scala.concurrent.Future

@Singleton
class IncidenceController @Inject()(cc: ControllerComponents,
repo: IncidenceRepository) extends AbstractController(cc) {

  def getAllIncidence = Action.async { implicit request:RequestHeader =>
   repo.getAllIncidence.map { incidence =>  
    Ok(Json.toJson(incidence))
    }
  }
  
  def getByUsername(username:String) = Action.async { implicit request =>            
         repo.getUserIncidence(username).map {  
          incidence => 
          Ok(Json.toJson(incidence)) }         
    }

    def getByUserAndTime(username:String,date:String) = Action.async { implicit request =>      
      val fmt = DateTimeFormat.forPattern("yyyy-MM-dd'--'HH:mm:ss")
      try {
        val dater = fmt.parseDateTime(date)
        repo.getUserIncidenceByDate(username,dater).map {  
          incidence => Ok(Json.toJson(incidence)) }
      } catch {
        case ex:java.lang.IllegalArgumentException =>  Future{Ok(Json.toJson("invalid date format, kindly adhere to this pattern" 
                                          ->  "yyyy-MM-dd'--'HH:mm:ss....Example: '2016-09-07--12:21:11'"))}
      }
      
    }
  
   def add = Action(parse.json) { request =>
      val placeResult = request.body.validate[Incidence]
      placeResult.fold(
        errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        incident => {
          val tmm = repo.createIncidence(incident)
          Ok(Json.obj("status" ->"OK", "message" -> ("Report '"+incident.username+"' saved.") ))
        }
      )
    }

def remove =  Action(parse.json){ request =>
 val placeResult = request.body.validate[Incidence]
    placeResult.fold(
        errors => {
          BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        incident => {
               val tmm = repo.removeIncidence(incident)
                 Ok(Json.obj("status" ->"OK",
                             "message" -> ("Incident '"+incident.username+"' '"+incident.postdate+"' has been removed.") ))
                  
            }

          )
         
        }
  
  }


  //  val r = for{
  //           incidence <- repo.getSingleIncidence(incident.username, incident.postdate)
  //           } yield (incidence) 
  //           r.map { result => result match {
  //             case Some(x) =>

  //             case None => BadRequest(Json.obj("status" -> " 1000",
  //                                             "message" -> " it seems ypu are trying to delete an incidence that doesnt exist"))       


