package systems.opalia.commons.core.scripting.exception;


public class ScriptException
        extends Exception {

    public ScriptException(String message) {

        super(message);
    }

    public ScriptException(String message, Throwable cause) {

        super(message, cause);
    }

    public ScriptException(Throwable cause) {

        super(cause);
    }
}
