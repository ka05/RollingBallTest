package com.example.clay.rollingballtest.observer;

/**
 * Created by wyattmcbain on 4/25/15.
 */
public interface GamePublisher {
    public void addSubscriber(GameSubscriber subscriber);
    public void removeSubscriber(GameSubscriber subscriber);
    public void notifySubscribers();
}
