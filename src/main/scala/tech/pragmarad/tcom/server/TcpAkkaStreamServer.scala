package tech.pragmarad.tcom.server

import akka.actor._
import akka.stream.ThrottleMode
import akka.stream.scaladsl._
import akka.util.ByteString
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

/**
 * Starts receiving  messages from TCP clients and sends response (all is as akka stream via TCP).
 * Credits: Ideas of organizing stream flow below are from EDX course "Reactive Scala".
 *
 * @param host TCP server host (DNS name or IP)
 * @param port TCP server port
 * @param actorSysName
 * @author pragmarad, reactive_scala_edx_course_team
 * @since 2020-03-09
 */
class TcpAkkaStreamServer(host: String, port: Int, actorSysName: String) {
  implicit val system: ActorSystem = ActorSystem(actorSysName)
  private val logger = LoggerFactory.getLogger(this.getClass.getName)

  /**
   * Starts receiving messages from remote clients.
   * NOTE: Use OS specific process termination command (Ctr-C etc)
   * @param flowLogic What kind of logic this server should support
   */
  def start(flowLogic: Flow[ByteString,ByteString,_]): Unit = {
    // Adding TCP support:
    logger.info("Starting TCP server with flowLogic <<{}>> ..", flowLogic)
    Tcp(system).bindAndHandle(flowLogic, host, port)
  }

}

/**
 * TCP server companion object.
 */
object TcpAkkaStreamServer {
  /**
   * Factory method for creating new instance of {{TcpAkkaStreamServer}}.
   * @param host
   * @param port
   * @param actorSysName
   * @return TCP server instance
   */
  def getServer(host: String, port: Int, actorSysName: String): TcpAkkaStreamServer = {
    new TcpAkkaStreamServer(host, port, actorSysName)
  }
}

