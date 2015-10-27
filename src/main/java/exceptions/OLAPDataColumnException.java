package exceptions;

/**
 * Created by lechip on 27/10/15.
 */
public class OLAPDataColumnException extends Exception {
    public static final String COLUMN_ALREADY_EXISTS = "Column already exists";

    public OLAPDataColumnException(String errorString, String columnId) {
        super(errorString +": " + columnId);
    }
}
