package com.example.dms.repository;

import com.example.dms.model.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface StatusRepository extends JpaRepository<Status, UUID> {
}

