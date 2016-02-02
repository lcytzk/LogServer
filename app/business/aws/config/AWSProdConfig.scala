package business.aws.config

import com.amazonaws.regions.Region

object AWSProdConfig extends AWSConfig {

  override var AWS_ACCESS_ID: String = _

  override var AWS_BUCKET: String = _

  override var AWS_SECRET_KEY: String = _

  override val REGION: Region = _
}
