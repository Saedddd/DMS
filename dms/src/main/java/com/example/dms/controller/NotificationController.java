package com.example.dms.controller;

import com.example.dms.model.Notification;
import com.example.dms.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable UUID userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{id}/read")
    public void markNotificationAsRead(@PathVariable UUID id) {
        notificationService.markNotificationAsRead(id);
    }
}
