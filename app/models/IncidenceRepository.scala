package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import org.joda.time.DateTime
import scala.concurrent.{ Future, ExecutionContext }
import com.github.tototoshi.slick.PostgresJodaSupport._


@Singleton
class IncidenceRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec:ExecutionContext){
  private val dbConfig  = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._


  private class IncidenceTable(tag: Tag) extends Table[Incidence](tag, "incidence") {  

    def username = column[String]("username")    

    def description = column[String]("description")

    def postdate = column[Option[DateTime]]("postdate")

    def incidencelocation = column[String]("incidencelocation")

    def * = ( username, description, postdate,incidencelocation ) <> ((Incidence.apply _).tupled, Incidence.unapply)

    def incidenceBindAccount = foreignKey("incidenceKey",username, account )(_.username, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  }

  private val incidence = TableQuery[IncidenceTable]

  //  Adding message
  def createIncidence(inc: Incidence) = db.run{
      incidence += inc
    }

    // Returning list of incidence
  def getAllIncidence(): Future[Seq[Incidence]] = db.run(incidence.result)
    

// Returning a single message by a username, filtering with postdate  
  def getUserIncidenceByDate(username : String, postdate:DateTime):Future[Seq[Incidence]]  = db.run{
        incidence.filter(_.username === username).filter(_.postdate === postdate).result
  }

// Returning messages by a username
  def getUserIncidence(username : String):Future[Seq[Incidence]]  = db.run{
        incidence.filter(_.username === username).result
  }

  //get all incidence of a uploaded by a single user 
  def getAllIncidenceOfASingleUser(username:String) : Future[Seq[Incidence]] = db.run(incidence.filter(_.username === username).result)

//update incidence 
  def updateIncidence(incid:Incidence) = db.run{
    incidence.filter(_.username === incid.username).filter(_.postdate === incid.postdate).update(incid)
  }

  // Removing Incident record
   def removeIncidence(detail: Incidence) = db.run(
    incidence.filter(_.username === detail.username).filter(_.postdate === detail.postdate)delete
  )

  private class AccountTable(tag: Tag) extends Table[Account](tag, "account") {

        def username = column[String]("username", O.PrimaryKey)  

        def email = column[String]("email")

        def image = column[String]("image")

        def * = (username, email, image) <> ((Account.apply _).tupled, Account.unapply)
    }

    private val account = TableQuery[AccountTable]

  def createAccount( user : Account) = db.run{         
       account += user                        
  } 

  def getAllAccount : Future[Seq[Account]] = db.run{
      account.result
  }

  def getAccountByusername(username: String): Future[Option[Account]]= db.run{
    account.filter(_.username === username).result.headOption
  }

  def removeAccount(acc : String) = db.run{
      account.filter(_.username === acc).delete
  }

}



