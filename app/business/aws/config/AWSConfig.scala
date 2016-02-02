package business.aws.config

import com.amazonaws.regions.Region

trait AWSConfig {

  var AWS_ACCESS_ID: String

  var AWS_SECRET_KEY: String

  var AWS_BUCKET: String

  val REGION: Region

}
