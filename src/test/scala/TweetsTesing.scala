package  com.edu
import org.scalatest.FunSuite
import scala.concurrent.Await
import scala.concurrent.duration._



class TweetsTesing extends FunSuite {

  val tweetsObj = new Tweets
  val noOfTweets = Await.result(tweetsObj.searchQuery("#scala"),3.second)

  test("testing whether tweets are retrieved or not?") {
    assert(noOfTweets > 0)
  }

  test("testing number of tweets")
  {
    assert(noOfTweets == 100)
  }
  test("testing average number of retweets")
  {
    assert((Await.result(tweetsObj.calculateNoOfRetweets("#scala"),3.second))/100 == 2)
  }

  test("testing average number of likes")
  {
    assert((Await.result(tweetsObj.calculateNoOfLikes("#scala"),3.second))/100 == 0)
  }

}

