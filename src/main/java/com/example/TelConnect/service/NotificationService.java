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
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository=notificationRepository;
    }

    //Create notification record when email is pushed to customer
    public void createNotification(Long customerId, String message) {

        Notification notification= new Notification();
        notification.setCustomerId(customerId);
        notification.setNotificationTimestamp(LocalDateTime.now());
        notification.setMessage(message);

        notificationRepository.save(notification);
    }

    //Get all notifications pushed to customer
    public List<String> getCustomerNotifications(Long customerId) {
        List<Notification> notifications = notificationRepository.findByCustomerId(customerId);

        return notifications.stream()
                .map(notification ->
                        "Notification: " + notification.getMessage() + " | Timestamp: " + notification.getNotificationTimestamp()
                )
                .collect(Collectors.toList());
    }

}