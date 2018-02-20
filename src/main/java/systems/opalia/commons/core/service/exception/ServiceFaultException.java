package systems.opalia.commons.core.service.exception;


public class ServiceFaultException
        extends Exception {

    public ServiceFaultException(String message) {

        super(message);
    }

    public ServiceFaultException(String message, Throwable cause) {

        super(message, cause);
    }

    public ServiceFaultException(Throwable cause) {

        super(cause);
    }
}
