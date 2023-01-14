package pro.sky.socksapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SocksInitializeException extends RuntimeException {
    public SocksInitializeException() {
        super();
    }

    public SocksInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocksInitializeException(String message) {
        super(message);
    }

    public SocksInitializeException(Throwable cause) {
        super(cause);
    }

}
