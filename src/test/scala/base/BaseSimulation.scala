package base

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BaseSimulation extends Simulation{
  val http_config = http
    .baseUrl("http://localhost:3000/")
    .acceptHeader("application/json")
}
