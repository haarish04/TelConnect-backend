package com.example.TelConnect.service;

import com.example.TelConnect.model.Notification;
import com.example.TelConnect.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification() {
        Long customerId = 12345L;
        String message = "Your plan has been activated.";

        // Mocking the save operation
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        notificationService.createNotification(customerId, message);

        // Verifying that save method is called once
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetCustomerNotifications() {
        Long customerId = 12345L;
        List<Notification> notifications = new ArrayList<>();
        Notification notification1 = new Notification();
        notification1.setCustomerId(customerId);
        notification1.setMessage("Your plan has been activated.");
        notification1.setNotificationTimestamp(LocalDateTime.of(2024, 9, 1, 10, 0));

        Notification notification2 = new Notification();
        notification2.setCustomerId(customerId);
        notification2.setMessage("Your payment is due.");
        notification2.setNotificationTimestamp(LocalDateTime.of(2024, 9, 2, 15, 0));

        notifications.add(notification1);
        notifications.add(notification2);

        // Mocking the findByCustomerId operation
        when(notificationRepository.findByCustomerId(customerId)).thenReturn(notifications);

        List<String> result = notificationService.getCustomerNotifications(customerId);

        assertEquals(2, result.size());
        assertEquals("Notification: Your plan has been activated. | Timestamp: 2024-09-01T10:00", result.get(0));
        assertEquals("Notification: Your payment is due. | Timestamp: 2024-09-02T15:00", result.get(1));
    }
}
