package com.kpaudel.twitter;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetMain {
	private Twitter twitter = TwitterFactory.getSingleton();

	public Status updateStatus(String statusText) throws TwitterException {
		StatusUpdate statusUpdate = new StatusUpdate(statusText);
		Status status = this.twitter.updateStatus(statusUpdate);
		return status;
	}

	public List<Status> getTimeLines() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = twitter.getHomeTimeline();
		return statuses;
	}

	public List<Status> searchTweets(String queryText) throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(queryText);
		QueryResult result = twitter.search(query);
		return result.getTweets();
	}

	public static void main(String... args) throws Exception {
		TweetMain main = new TweetMain();
		// for (Status status : main.getTimeLines()) {
		// System.out.println(status.getText());
		// }

		String query = "deepawali";
		while (true) {
			try {
				for (Status status : main.searchTweets(query)) {
					main.twitter.retweetStatus(status.getId());
					Thread.sleep(100000);
				}
				Thread.sleep(900000);
			} catch (TwitterException ex) {
				ex.printStackTrace();
				Thread.sleep(900000);
				continue;
			}
		}

	}
}
