package systems.opalia.commons.core.vfs.exception;

import java.io.IOException;


public class DataCorruptionException
        extends IOException {

    public DataCorruptionException(String message) {

        super(message);
    }

    public DataCorruptionException(String message, Throwable cause) {

        super(message, cause);
    }

    public DataCorruptionException(Throwable cause) {

        super(cause);
    }
}
