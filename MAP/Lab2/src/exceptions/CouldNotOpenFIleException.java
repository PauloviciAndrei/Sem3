package exceptions;

public class CouldNotOpenFIleException extends RuntimeException {
    public CouldNotOpenFIleException(String message) {
        super(message);
    }
}
