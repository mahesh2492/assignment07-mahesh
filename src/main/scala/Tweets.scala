package  com.edu
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.Query
import collection.JavaConversions._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.config.ConfigFactory
import java.io.File


class Tweets {

 val parsedConfig= ConfigFactory.parseFile(new File("C:\\Users\\Pradeep\\TwitterApp\\src\\main\\resources"))
  val cn = ConfigFactory.load(parsedConfig)
  val consumerKey: String = cn.getString("consumerKey")
  val consumerSecretKey: String = cn.getString("consumerSecretKey")
  val accessToken: String = cn.getString("accessToken")
  val accessTokenSecret: String = cn.getString("accessTokenSecret")

    /*  config work to create a twitter object */
    val cb = new ConfigurationBuilder
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecretKey)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret) 

      val tf = new TwitterFactory(cb.build)
      val twitter = tf.getInstance()
    /*
        Method to Search the tweets by particular #tag
     */
    val max = 100
    def searchQuery(str: String): Future[Int] = Future {

      val query = new Query(str)
      query.setCount(max)
      val list = twitter.search(query)
      val tweets = list.getTweets.toList
      tweets.length
    }
    /*
      Method to calculate total retweets on particular #tag
     */
    def calculateNoOfRetweets(str: String) : Future[Int] = Future{

      val query = new Query(str)
      query.setCount(max)
      val list = twitter.search(query)
      val tweets = list.getTweets.toList
      val noOfRetweets = tweets.map(x => x.getRetweetCount)
      noOfRetweets.sum
    }
  /*
     Method to calculate total Likes on particular #tag
   */
  def calculateNoOfLikes(str: String) : Future[Int] = Future{

    val query = new Query(str)
    query.setCount(max)
    val list = twitter.search(query)
    val tweets = list.getTweets.toList
    val noOfLikes = tweets.map(x => x.getFavoriteCount)
    noOfLikes.sum
  }
}
