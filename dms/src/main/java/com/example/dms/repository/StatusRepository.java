package com.example.dms.repository;

import com.example.dms.model.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
}

