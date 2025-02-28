package com.example.dms.service;

import com.example.dms.model.Notification;
import com.example.dms.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void markNotificationAsRead(UUID id) {
        notificationRepository.findById(id).map(notification -> {
            notification.setRead(true);
            return notificationRepository.save(notification);
        }).orElseThrow(() -> new RuntimeException("Уведомление не найдено"));
    }
}
