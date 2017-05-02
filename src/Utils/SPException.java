package Utils;

/**
 * This class wraps any exception of the underlying specifications such as
 * SQL exceptions.
 * Created by Didac on 30/04/2017.
 */
public class SPException extends RuntimeException {

    /**
     * Constructs the exception with the given message
     * @param message The details of the exception
     */
    public SPException(String message){
        super(message);
    }

    /**
     * Constructs the exception with the given cause
     * @param cause The root cause of the exception
     */
    public SPException(Throwable cause){
        super(cause);
    }

    /**
     * Constructs the exception with the given message and cause
     * @param message
     * @param cause
     */
    public SPException(String message, Throwable cause){
        super(message, cause);
    }
}
