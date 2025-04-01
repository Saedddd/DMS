    package com.example.dms.service;

    import com.example.dms.model.Attachment;
    import com.example.dms.repository.AttachmentRepository;
    import org.springframework.stereotype.Service;
    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    @Service
    public class AttachmentService {
        private final AttachmentRepository attachmentRepository;

        public AttachmentService(AttachmentRepository attachmentRepository) {
            this.attachmentRepository = attachmentRepository;
        }

        public List<Attachment> getAttachmentsByDocumentId(UUID documentId) {
            return attachmentRepository.findByDocumentId(documentId);
        }

        public Attachment uploadAttachment(Attachment attachment) {
            return attachmentRepository.save(attachment);
        }

        public void deleteAttachment(UUID id) {
            attachmentRepository.deleteById(id);
        }
    }

