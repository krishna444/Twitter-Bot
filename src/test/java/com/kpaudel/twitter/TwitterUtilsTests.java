package com.kpaudel.twitter;

import org.junit.Test;

public class TwitterUtilsTests {
    @Test
    public void testRetweetTopics(){
        try {
            TwitterUtils.retweetTopics("deutschland","germany", "coronavirus", "update", "covid");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
