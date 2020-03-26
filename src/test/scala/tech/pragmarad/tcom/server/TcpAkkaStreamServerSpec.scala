package tech.pragmarad.tcom.server

import org.scalatest.flatspec.AnyFlatSpec

/**
  * Validation of tests system work.
  */
class TcpAkkaStreamServerSpec extends AnyFlatSpec {

  "Server" should "return OK status" in {
    val host = "localhost"
    val port = 15621
    val actorSysName = "TestTcpSrvSys"
    val server = new TcpAkkaStreamServer(host, port, actorSysName)
    // TODO: validate this streaming guy
//    val result = TBD
//    val expected  = TBD
//    assert(expected.equals(result))
  }
}
