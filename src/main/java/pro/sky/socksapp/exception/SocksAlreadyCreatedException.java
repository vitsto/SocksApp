package pro.sky.socksapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SocksAlreadyCreatedException extends RuntimeException {
    public SocksAlreadyCreatedException() {
        super();
    }

    public SocksAlreadyCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocksAlreadyCreatedException(String message) {
        super(message);
    }

    public SocksAlreadyCreatedException(Throwable cause) {
        super(cause);
    }

}
