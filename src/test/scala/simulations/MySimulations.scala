package simulations

import base.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class MySimulations extends BaseSimulation {
  val scn = scenario("list all products")
    .exec(
      http("request_1")
          .get("api/Quantitys/")
          .check(status.is(400))
    )


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(http_config)

}
