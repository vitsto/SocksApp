package pro.sky.socksapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SocksNotFoundException extends RuntimeException {
    public SocksNotFoundException() {
        super();
    }

    public SocksNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocksNotFoundException(String message) {
        super(message);
    }

    public SocksNotFoundException(Throwable cause) {
        super(cause);
    }

}
