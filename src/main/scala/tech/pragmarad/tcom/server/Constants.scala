package tech.pragmarad.tcom.server

/**
 * Component constants
 *
 * @author pragmarad
 * @since 2020-03-09
 */
object Constants {

  /**
   * Default values
   */
  object Default {
    val ACTOR_SYSTEM_NAME = "TcpAkkaStreamsActorSys"
  }

  object PropNames {
    private val PREFIX = "tcom.srv.akka."

    val ACTOR_SYSTEM_NAME: String = PREFIX + "actor-system-name"

    // Server params
    val HOST: String = PREFIX + "host"
    val PORT: String = PREFIX + "port"

  }


}
