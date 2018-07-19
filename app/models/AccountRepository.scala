package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }
import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class AccountRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec:ExecutionContext){
    private val dbConfig  = dbConfigProvider.get[JdbcProfile]

    import dbConfig._
    import profile.api._

    private class AccountTable(tag: Tag) extends Table[Account](tag, "account") {

        def username = column[String]("username", O.PrimaryKey)  

        def email = column[String]("email")

        def image = column[String]("image")

        def * = (username, email, image) <> ((Account.apply _).tupled, Account.unapply)
    }

    private val account = TableQuery[AccountTable]

  def create( user : Account) = db.run{         
       account += user                        
  } 

  def getAll : Future[Seq[Account]] = db.run{
      account.result
  }

  def getByusername(username: String): Future[Option[Account]]= db.run{
    account.filter(_.username === username).result.headOption
  }

  def removeUser(acc : String) = db.run{
      account.filter(_.username === acc).delete
  }

}

