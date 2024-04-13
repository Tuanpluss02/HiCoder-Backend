package com.stormx.hicoder.controllers;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.stormx.hicoder.controllers.helpers.GeneralMessageRepresentation;
import com.stormx.hicoder.controllers.helpers.MulticastMessageRepresentation;
import com.stormx.hicoder.services.FCMService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publish")
@Tag(name = "Firebase Publisher Controller", description = "Include method to publish message to Firebase")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FirebasePublisherController {

    private final FCMService fcm;

    @PostMapping("/topics/{topic}")
    public ResponseEntity<String> postToTopic(@RequestBody GeneralMessageRepresentation message, @PathVariable("topic") String topic) {
        try {
            String fcmMessageId = fcm.sendNotificationToTopic(message.getTittle(), message.getData(), topic);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(fcmMessageId);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/clients/{registrationToken}")
    public ResponseEntity<String> postToClient(@RequestBody GeneralMessageRepresentation message, @PathVariable("registrationToken") String registrationToken) {
        try {
            String fcmMessageId = fcm.sendNotificationToDevice(message.getTittle(), message.getData(),registrationToken);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(fcmMessageId);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<List<String>> postToClients(@RequestBody MulticastMessageRepresentation message) {
        try {
            List<String> fcmMessageIds = fcm.sendNotificationToManyDevice(message.getTitle(), message.getBody(), message.getRegistrationTokens());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(fcmMessageIds);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(e.getMessage()));
        }
    }

    @PostMapping("/subscriptions/{topic}")
    public ResponseEntity<Void> createSubscription(@PathVariable("topic") String topic, @RequestBody List<String> registrationTokens) {
        try {
            fcm.subscribeToTopic(topic, registrationTokens);
            return ResponseEntity.ok().build();
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/subscriptions/{topic}/{registrationToken}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String topic, @PathVariable String registrationToken) {
        try {
            fcm.deleteSubscription(topic, Collections.singletonList(registrationToken));
            return ResponseEntity.ok().build();
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}