package com.stormx.hicoder.services.implement;

import com.google.firebase.messaging.*;
import com.stormx.hicoder.services.FCMService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FCMServiceImpl implements FCMService {
    @Override
    public String sendNotificationToDevice(String registrationToken, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .putData(title, body)
                .setToken(registrationToken)
                .build();
        return FirebaseMessaging.getInstance().send(message);
    }

    @Override
    public List<String> sendNotificationToManyDevice(String title, String body, List<String> registrationTokens) throws FirebaseMessagingException {
        MulticastMessage message = MulticastMessage.builder()
                .putData(title, body)
                 .addAllTokens(registrationTokens)
                .build();
        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        List<String> failedTokens = new ArrayList<>();
        if (response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    failedTokens.add(registrationTokens.get(i));
                }
            }
    }
        return failedTokens;
    }

    @Override
    public String sendNotificationToTopic(String topic, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .putData(title, body)
                .setTopic(topic)
                .build();
        return FirebaseMessaging.getInstance().send(message);
    }

    @Override
    public void subscribeToTopic(String topic, @NotNull List<String> registrationTokens) {
        for (String registrationToken : registrationTokens) {
            FirebaseMessaging.getInstance().subscribeToTopicAsync(List.of(registrationToken), topic);
        }
    }

    @Override
    public void deleteSubscription(String topic, @NotNull List<String> registrationTokens) {
        for (String registrationToken : registrationTokens) {
            FirebaseMessaging.getInstance().unsubscribeFromTopicAsync(List.of(registrationToken), topic);
        }
    }
}
