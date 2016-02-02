package business.aws.config

import com.amazonaws.regions.Region
import play.api.Play

trait AWSConfig {

  var AWS_ACCESS_ID: String

  var AWS_SECRET_KEY: String

  var AWS_BUCKET: String

  val REGION: Region
}

object AWSConfig {
  def getProperConfig: AWSConfig = {
    if (Play.current.configuration.getString("env").get.equals("Test")) {
      AWSTestConfig
    } else {
      AWSProdConfig
    }
  }
}
