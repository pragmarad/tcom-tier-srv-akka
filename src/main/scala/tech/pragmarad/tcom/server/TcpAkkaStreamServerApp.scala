package tech.pragmarad.tcom.server

import akka.stream.scaladsl.Flow
import akka.util.ByteString
import cats.implicits._
import com.monovore.decline.{Command, Opts}
import com.typesafe.config.Config
import org.slf4j.LoggerFactory
import tech.pragmarad.tcom.commons.{ConfigCommonConstants, HoconConfigUtil, StringUtil}

import scala.util.Try

/**
 * Tcp server app starter.<br>
 * This app accepts arguments: srvhost,srvport
 * @author pragmarad
 * @since 2020-03-09
 */
object TcpAkkaStreamServerApp {
  private val logger = LoggerFactory.getLogger(this.getClass.getName)
  private val cfg: Config = HoconConfigUtil.getConfig()

  private val appOptions = buildAppOptions()

  private val appCommand = Command(
    name = "TcpAkkaStreamServerApp",
    header = "Launch receiving messages via TCP as an akka stream."
  ) {
    appOptions
  }

  /**
   * Main
   *
   * @param args
   */
  def main(args: Array[String]):Unit = appCommand.parse(args, sys.env) match {
    case Left(help) => {
      System.err.println(s"Args << ${args}>> could not be parsed.\n ${help}")
      sys.exit(1)
    }
    case Right(appOptions) => {
      logger.info("TcpServer starting with options '{}'..", appOptions)

      // Extract Actor system name:
      val actorSysNameFromConf = cfg.getString(Constants.PropNames.ACTOR_SYSTEM_NAME)
      val actorSysName = StringUtil.getValueWithDefaultFallback(actorSysNameFromConf, Constants.Default.ACTOR_SYSTEM_NAME)

      // Extract options:
      val srvHost = appOptions._1
      val srvPort = appOptions._2

      // Variations of logic:
      val flowLogic = Flow[ByteString]

      // Now start a server:
      // Now, let's start a server:
      val tcpServer = TcpAkkaStreamServer.getServer(srvHost, srvPort, actorSysName)
      tcpServer.start(flowLogic)

      logger.info("TcpServer started.")
    }
  }

  /**
   * TODO: consider some kind of map instead of brittle tuple
   *
   * @return Options of srvhost,srvport
   */
  private def buildAppOptions(): Opts[(String, Int)] = {

    //------------
    // TODO: Figure out more type safe options extraction
    // Params: srvhost,srvport

    // 1. srvhost
    val srvhostFromConf = HoconConfigUtil.getStringOption(cfg, Constants.PropNames.HOST).getOrElse(ConfigCommonConstants.Default.HOST)
    val srvhostOrDefault = Opts.option[String]("srvhost", short = "h", metavar = "string"
      , help = "Server host name or IP.").withDefault(srvhostFromConf)

    // 2. srvport
    val srvportFromConf = HoconConfigUtil.getStringOption(cfg, Constants.PropNames.PORT).getOrElse(ConfigCommonConstants.Default.PORT)
    val srvportOrDefault = Opts.option[String]("srvport", short = "p", metavar = "int"
      , help = "Server port.").withDefault(srvportFromConf)
      .validate("Port must be positive int!")({ value => Try(value.toInt).isSuccess && value.toInt > 0 }).map {
      _.toInt
    }

    // Gather all options:
    val appOptions = (srvhostOrDefault, srvportOrDefault).tupled
    // TODO: Consider mapN to map into some kind of Map (positional config is brittle)

    appOptions
  }

}