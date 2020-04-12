package com.kpaudel.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class TwitterUtils {
    private static final String PROPERTIES_FILE = "twitter4j.properties";
    private static Twitter INSTANCE;

    /**
     * Gets twitter instance
     *
     * @return Twitter Instance
     */
    private static Twitter getInstance() throws IOException {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        Properties properties = new Properties();
        InputStream inputStream = TwitterUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        properties.load(inputStream);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(properties.getProperty("oauth.consumerKey"))
                .setOAuthConsumerSecret(properties.getProperty("oauth.consumerSecret"))
                .setOAuthAccessToken(properties.getProperty("oauth.accessToken"))
                .setOAuthAccessTokenSecret(properties.getProperty("oauth.accessTokenSecret"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }

    public static String createTweet(String tweet) throws TwitterException, IOException {
        Twitter twitter = TwitterUtils.getInstance();
        Status status = twitter.updateStatus(tweet);
        return status.getText();
    }

    /**
     * Gets the timeline
     *
     * @return List of timeline
     * @throws TwitterException
     * @throws IOException
     */
    public static List<String> getTimeLine() throws TwitterException, IOException {
        Twitter twitter = TwitterUtils.getInstance();

        return twitter.getHomeTimeline().stream()
                .map(item -> item.getText())
                .collect(Collectors.toList());
    }

    /**
     * Send direct message to a recipient
     *
     * @param recipientName Name of recipient
     * @param msg           Message to send
     * @return Text message sent
     * @throws TwitterException
     * @throws IOException
     */
    public static String sendDirectMessage(String recipientName, String msg) throws TwitterException, IOException {
        Twitter twitter = TwitterUtils.getInstance();
        DirectMessage message = twitter.sendDirectMessage(recipientName, msg);
        return message.getText();
    }


    /**
     * Searches tweets with the topic provided
     *
     * @param topic Topic
     * @return list of topics
     * @throws TwitterException
     * @throws IOException
     */
    public static List<Status> searchTweets(String topic) throws TwitterException, IOException {

        Twitter twitter = TwitterUtils.getInstance();
        Query query = new Query(topic);
        QueryResult result = twitter.search(query);

        return result.getTweets();
    }

    /**
     * Retweets a status
     *
     * @param statusId id of status
     * @return Status retweeted
     * @throws IOException
     * @throws TwitterException
     */
    public static Status retweet(long statusId) throws IOException, TwitterException {
        Status status = TwitterUtils.getInstance().retweetStatus(statusId);
        return status;
    }

    public static void retweetTopics(String... topics) throws TwitterException, IOException {
        for (String topic : topics) {
            List<Status> filteredList = searchTweets(topic).stream().filter(status -> status.getRetweetCount() > 10 && status.getLang().equals("en")).collect(Collectors.toList());
            filteredList.forEach(status -> {
                try {
                    retweet(status.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
