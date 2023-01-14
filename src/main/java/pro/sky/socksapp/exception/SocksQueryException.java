package pro.sky.socksapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SocksQueryException extends RuntimeException {
    public SocksQueryException() {
        super();
    }

    public SocksQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocksQueryException(String message) {
        super(message);
    }

    public SocksQueryException(Throwable cause) {
        super(cause);
    }

}
