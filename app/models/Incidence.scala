package models


import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.json.{Json,_}
import javax.inject.{Inject,Singleton}


@Singleton  //add location to the models
case class Incidence  @Inject()( username:String, description:String, incidencelocation:String, postdate: Option[DateTime]=None )


object Incidence {
 val pattern = "yyyy-MM-dd'--'HH:mm:ss"

 implicit val  myFormat = Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))
 implicit val jsonFormat = Json.format[Incidence]



  implicit val IncidenceWrites = new Writes[Incidence] 
{

    def writes(incidence: Incidence)= Json.obj(
        // "Id" -> incidence.id,
        "username" -> incidence.username,
        "description" -> incidence.description,
        "postdate" -> incidence.postdate,
        "incidencelocation" -> incidence.incidencelocation)
      }


  implicit val IncidenceReads = new Reads[Incidence] 
{
        def reads(json: JsValue): JsResult[Incidence] = {
        // val id = (json \ "id").as[Int]
        val username = (json \ "username").as[String]
        val description = (json \ "description").as[String]
        // val postdate = (json\ "postdate").as[DateTime]
        val incidencelocation = (json \ "incidencelocation").as[String]
        
        JsSuccess(Incidence( username, description, incidencelocation ))
    }
}
}