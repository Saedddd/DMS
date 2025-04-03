package com.example.dms.service;

import com.example.dms.exception.ResourceNotFoundException;
import com.example.dms.repository.StatusRepository;
import com.example.dms.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Status getStatusById(UUID id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + id));
    }

    @Transactional
    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    @Transactional
    public Status updateStatus(UUID id, Status updatedStatus) {
        return statusRepository.findById(id).map(status -> {
            status.setName(updatedStatus.getName());
            status.setFinal(updatedStatus.isFinal());
            status.setOrderIndex(updatedStatus.getOrderIndex());
            return statusRepository.save(status);
        }).orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + id));
    }

    @Transactional
    public void deleteStatus(UUID id) {
        statusRepository.deleteById(id);
    }
}

