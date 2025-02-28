package com.example.dms.controller;

import com.example.dms.model.Status;
import com.example.dms.service.StatusService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/statuses")
public class StatusController {
    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public List<Status> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public Status getStatusById(@PathVariable UUID id) {
        return statusService.getStatusById(id);
    }

    @PostMapping
    public Status createStatus(@RequestBody Status status) {
        return statusService.createStatus(status);
    }

    @PutMapping("/{id}")
    public Status updateStatus(@PathVariable UUID id, @RequestBody Status status) {
        return statusService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable UUID id) {
        statusService.deleteStatus(id);
    }
}

