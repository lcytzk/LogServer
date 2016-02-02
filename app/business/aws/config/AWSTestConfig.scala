package business.aws.config

import com.amazonaws.regions.{Region, Regions}

object AWSTestConfig extends AWSConfig {

  override var AWS_ACCESS_ID: String = "AKIAIM7CSWO7VMSKQJLA"

  override var AWS_BUCKET: String = "sUC8apNxHcf6odVcEcNa8PQovY9s68RXSkCbJrXQ"

  override var AWS_SECRET_KEY: String = "test-jide-com"

  override val REGION: Region = Region.getRegion(Regions.US_WEST_2)
}
