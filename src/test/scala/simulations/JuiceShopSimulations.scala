package simulations

import base.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.http.check.HttpCheckScope.Status

import scala.util.Random

class JuiceShopSimulations extends BaseSimulation{


  val scn = scenario("Juice shop user journey")
    .exec(
      http("login to juice shop")
        .post("rest/user/login")
        .body(ElFileBody("login_user.json")).asJson
        .check(status.is(200))
        .check(jsonPath("$.authentication.token").saveAs("authToken"))
        .check(jsonPath("$.authentication.bid").saveAs("bid")))
//    .exec (
//          session => {
//            println(session("authToken").as[String])
//            session
//          }
//        )
      .exec(
        http("list all products")
          .get("api/Quantitys/")
          .check(status.is(200))
          .check(jsonPath("$.data[0].id").saveAs("productId"))
      )
      .exec(
        http("review first product")
          .post("api/BasketItems/")
          .header("Authorization", "Bearer " + "${authToken}")
          .body(StringBody("{\"ProductId\":${productId},\"BasketId\":\"${bid}\",\"quantity\":1}")).asJson
          .check(status.is(200))
      )


  setUp(
    scn.inject(
      atOnceUsers(10), // 2
      rampUsers(10) during (5 seconds), // 3
      constantUsersPerSec(20) during (15 seconds), // 4
//      constantUsersPerSec(20) during (15 seconds) randomized, // 5
//      rampUsersPerSec(10) to 20 during (10 minutes), // 6
//      rampUsersPerSec(10) to 20 during (10 minutes) randomized, // 7
    )
  ).protocols(http_config)

}
