package com.example.mazeh.twitter_timeline;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.conf.ConfigurationBuilder;

public class TweetRepository {
    private static TweetRepository instance;

    private TweetRepository() {

    }

    public static TweetRepository getInstance() {
        if (instance == null) {
            instance = new TweetRepository();
        }
        return instance;
    }

    private ConfigurationBuilder getConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("H51osxmkL9AAuBYZVDG8qq16b")
                .setOAuthConsumerSecret("RKkThHDQklURnKdbSje3fazGCcuzflwG3adiBFe02MJ8y9uvTe")
                .setOAuthAccessToken("618546314-SSWZZ812UEebRKlz7J12Q7FCFcpfSF0L3HWm6z79")
                .setOAuthAccessTokenSecret("InG05ERZ5jOJU3af88FTAAYixTAPkAC9H5PjTPtizPwB0");

        return cb;
    }

    public void getTimelineAsync(TwitterListener listener) {
        AsyncTwitterFactory factory = new AsyncTwitterFactory((getConfiguration().build()));
        AsyncTwitter asyncTwitter = factory.getInstance();
        asyncTwitter.addListener(listener);
        asyncTwitter.getHomeTimeline();
    }
}
