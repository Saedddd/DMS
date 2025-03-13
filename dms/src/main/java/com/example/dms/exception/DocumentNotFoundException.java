public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(UUID id) {
        super("Document not found with id: " + id);
    }
} 