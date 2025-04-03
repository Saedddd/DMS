package com.example.dms.config;

import com.example.dms.model.*;
import com.example.dms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final DocumentRepository documentRepository;
    private final AttachmentRepository attachmentRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            initRoles();
        }
        
        if (statusRepository.count() == 0) {
            initStatuses();
        }
        
        if (userRepository.count() == 0) {
            initUsers();
        }
        
        if (documentRepository.count() == 0) {
            initDocuments();
        }
        
        if (attachmentRepository.count() == 0) {
            initAttachments();
        }
        
        if (commentRepository.count() == 0) {
            initComments();
        }
        
        if (notificationRepository.count() == 0) {
            initNotifications();
        }
    }

    private void initRoles() {
        log.info("Initializing roles...");
        List<Role> roles = List.of(
                Role.builder().name("ADMIN").build(),
                Role.builder().name("MANAGER").build(),
                Role.builder().name("USER").build(),
                Role.builder().name("VIEWER").build(),
                Role.builder().name("EDITOR").build()
        );
        roleRepository.saveAll(roles);
        log.info("Created {} roles", roles.size());
    }

    private void initStatuses() {
        log.info("Initializing statuses...");
        List<Status> statuses = List.of(
                Status.builder().name("Draft").isFinal(false).orderIndex(1).build(),
                Status.builder().name("Under Review").isFinal(false).orderIndex(2).build(),
                Status.builder().name("Approved").isFinal(false).orderIndex(3).build(),
                Status.builder().name("Published").isFinal(false).orderIndex(4).build(),
                Status.builder().name("Archived").isFinal(true).orderIndex(5).build()
        );
        statusRepository.saveAll(statuses);
        log.info("Created {} statuses", statuses.size());
    }

    private void initUsers() {
        log.info("Initializing users...");
        List<Role> roles = roleRepository.findAll();
        
        List<User> users = List.of(
                User.builder()
                        .fullName("John Doe")
                        .email("john.doe@example.com")
                        .dateOfBirth(new Date())
                        .role(roles.get(0))
                        .build(),
                User.builder()
                        .fullName("Jane Smith")
                        .email("jane.smith@example.com")
                        .dateOfBirth(new Date())
                        .role(roles.get(1))
                        .build(),
                User.builder()
                        .fullName("Bob Johnson")
                        .email("bob.johnson@example.com")
                        .dateOfBirth(new Date())
                        .role(roles.get(2))
                        .build(),
                User.builder()
                        .fullName("Alice Williams")
                        .email("alice.williams@example.com")
                        .dateOfBirth(new Date())
                        .role(roles.get(3))
                        .build(),
                User.builder()
                        .fullName("Charlie Brown")
                        .email("charlie.brown@example.com")
                        .dateOfBirth(new Date())
                        .role(roles.get(4))
                        .build()
        );
        userRepository.saveAll(users);
        log.info("Created {} users", users.size());
    }

    private void initDocuments() {
        log.info("Initializing documents...");
        List<User> users = userRepository.findAll();
        List<Status> statuses = statusRepository.findAll();
        
        List<Document> documents = List.of(
                Document.builder()
                        .title("Annual Report 2023")
                        .description("Financial report for fiscal year 2023")
                        .status(statuses.get(0))
                        .owner(users.get(0))
                        .build(),
                Document.builder()
                        .title("Project Proposal")
                        .description("New software development project proposal")
                        .status(statuses.get(1))
                        .owner(users.get(1))
                        .build(),
                Document.builder()
                        .title("Employee Handbook")
                        .description("Company policies and procedures")
                        .status(statuses.get(2))
                        .owner(users.get(2))
                        .build(),
                Document.builder()
                        .title("Marketing Strategy")
                        .description("Strategy for Q3 2023")
                        .status(statuses.get(3))
                        .owner(users.get(3))
                        .build(),
                Document.builder()
                        .title("Technical Documentation")
                        .description("System architecture documentation")
                        .status(statuses.get(4))
                        .owner(users.get(4))
                        .build()
        );
        documentRepository.saveAll(documents);
        log.info("Created {} documents", documents.size());
    }

    private void initAttachments() {
        log.info("Initializing attachments...");
        List<Document> documents = documentRepository.findAll();
        List<User> users = userRepository.findAll();
        
        Instant now = Instant.now();
        
        List<Attachment> attachments = List.of(
                Attachment.builder()
                        .fileName("financial-data.xlsx")
                        .filePath("/storage/financial-data.xlsx")
                        .document(documents.get(0))
                        .uploadedBy(users.get(0))
                        .uploadedAt(now.minus(5, ChronoUnit.DAYS))
                        .build(),
                Attachment.builder()
                        .fileName("project-timeline.pdf")
                        .filePath("/storage/project-timeline.pdf")
                        .document(documents.get(1))
                        .uploadedBy(users.get(1))
                        .uploadedAt(now.minus(4, ChronoUnit.DAYS))
                        .build(),
                Attachment.builder()
                        .fileName("handbook-draft.docx")
                        .filePath("/storage/handbook-draft.docx")
                        .document(documents.get(2))
                        .uploadedBy(users.get(2))
                        .uploadedAt(now.minus(3, ChronoUnit.DAYS))
                        .build(),
                Attachment.builder()
                        .fileName("marketing-slides.pptx")
                        .filePath("/storage/marketing-slides.pptx")
                        .document(documents.get(3))
                        .uploadedBy(users.get(3))
                        .uploadedAt(now.minus(2, ChronoUnit.DAYS))
                        .build(),
                Attachment.builder()
                        .fileName("system-diagram.png")
                        .filePath("/storage/system-diagram.png")
                        .document(documents.get(4))
                        .uploadedBy(users.get(4))
                        .uploadedAt(now.minus(1, ChronoUnit.DAYS))
                        .build()
        );
        attachmentRepository.saveAll(attachments);
        log.info("Created {} attachments", attachments.size());
    }

    private void initComments() {
        log.info("Initializing comments...");
        List<Document> documents = documentRepository.findAll();
        List<User> users = userRepository.findAll();
        
        Instant now = Instant.now();
        
        List<Comment> comments = List.of(
                Comment.builder()
                        .text("Please review the financial section on page 10.")
                        .document(documents.get(0))
                        .author(users.get(1))
                        .createdAt(now.minus(5, ChronoUnit.DAYS))
                        .build(),
                Comment.builder()
                        .text("The budget allocation needs more details.")
                        .document(documents.get(1))
                        .author(users.get(0))
                        .createdAt(now.minus(4, ChronoUnit.DAYS))
                        .build(),
                Comment.builder()
                        .text("We should update the vacation policy section.")
                        .document(documents.get(2))
                        .author(users.get(3))
                        .createdAt(now.minus(3, ChronoUnit.DAYS))
                        .build(),
                Comment.builder()
                        .text("Great strategy! Let's implement this ASAP.")
                        .document(documents.get(3))
                        .author(users.get(2))
                        .createdAt(now.minus(2, ChronoUnit.DAYS))
                        .build(),
                Comment.builder()
                        .text("The API documentation is missing.")
                        .document(documents.get(4))
                        .author(users.get(4))
                        .createdAt(now.minus(1, ChronoUnit.DAYS))
                        .build()
        );
        commentRepository.saveAll(comments);
        log.info("Created {} comments", comments.size());
    }
    
    private void initNotifications() {
        log.info("Initializing notifications...");
        List<Document> documents = documentRepository.findAll();
        List<User> users = userRepository.findAll();
        
        Instant now = Instant.now();
        
        List<Notification> notifications = List.of(
                Notification.builder()
                        .message("A new comment was added to Annual Report 2023")
                        .isRead(false)
                        .user(users.get(0))
                        .document(documents.get(0))
                        .createdAt(now.minus(5, ChronoUnit.DAYS))
                        .build(),
                Notification.builder()
                        .message("Project Proposal has been moved to Under Review")
                        .isRead(true)
                        .user(users.get(1))
                        .document(documents.get(1))
                        .createdAt(now.minus(4, ChronoUnit.DAYS))
                        .build(),
                Notification.builder()
                        .message("You were mentioned in a comment on Employee Handbook")
                        .isRead(false)
                        .user(users.get(2))
                        .document(documents.get(2))
                        .createdAt(now.minus(3, ChronoUnit.DAYS))
                        .build(),
                Notification.builder()
                        .message("Marketing Strategy document was approved")
                        .isRead(false)
                        .user(users.get(3))
                        .document(documents.get(3))
                        .createdAt(now.minus(2, ChronoUnit.DAYS))
                        .build(),
                Notification.builder()
                        .message("Technical Documentation has a new attachment")
                        .isRead(true)
                        .user(users.get(4))
                        .document(documents.get(4))
                        .createdAt(now.minus(1, ChronoUnit.DAYS))
                        .build()
        );
        notificationRepository.saveAll(notifications);
        log.info("Created {} notifications", notifications.size());
    }
} 