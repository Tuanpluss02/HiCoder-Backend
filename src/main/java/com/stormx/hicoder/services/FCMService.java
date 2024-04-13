package com.stormx.hicoder.services;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface FCMService {
    String sendNotificationToDevice(String title, String body,String registrationToken) throws FirebaseMessagingException;
    List<String> sendNotificationToManyDevice(String title, String body, List<String> registrationTokens) throws FirebaseMessagingException;
    String sendNotificationToTopic(String title, String body, String topic) throws FirebaseMessagingException;
    void subscribeToTopic(String topic, List<String> registrationTokens ) throws FirebaseMessagingException;
    void deleteSubscription(String topic, List<String> registrationTokens) throws FirebaseMessagingException;
}