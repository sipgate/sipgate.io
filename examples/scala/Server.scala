import org.http4s.server.blaze.BlazeServer
import org.http4s.server.HttpService
import org.http4s.dsl._
import java.net.URLDecoder

object Server extends App {
  def fromDataFromBody(body: String) = {
    body.split("&").map( s => {
      val m =  s.split("=", 2).map(s => URLDecoder.decode(s, "UTF-8"))
      m(0) -> m(1)
    }).toMap
  }

  val service: HttpService = {
    case req @ POST -> Root =>
      val data = fromDataFromBody(text(req).run)
      println("from: " + data.getOrElse("from", ""))
      println("to: " + data.getOrElse("to", ""))
      Ok("roger that")
  }

  BlazeServer.newBuilder.mountService(service, "/").run()
}

