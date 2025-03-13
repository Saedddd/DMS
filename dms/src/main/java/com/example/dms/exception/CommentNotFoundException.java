public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(UUID id) {
        super("Comment not found with id: " + id);
    }
} 