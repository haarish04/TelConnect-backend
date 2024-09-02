package com.example.TelConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TelConnect.model.Notification;
import com.example.TelConnect.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification createNotification(Long customerId, String message) {

        Notification notification= new Notification();
        notification.setCustomerId(customerId);
        notification.setNotificationTimestamp(LocalDateTime.now());
        notification.setMessage(message);

        return notificationRepository.save(notification);
    }

    public List<String> getCustomerNotifications(Long customerId) {
        List<Notification> notifications = notificationRepository.findByCustomerId(customerId);

        return notifications.stream()
                .map(notification ->
                        "Notification: " + notification.getMessage() + " | Timestamp: " + notification.getNotificationTimestamp()
                )
                .collect(Collectors.toList());
    }


}