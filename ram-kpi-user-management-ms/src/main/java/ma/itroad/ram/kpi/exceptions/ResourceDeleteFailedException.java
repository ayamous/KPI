package ma.itroad.ram.kpi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceDeleteFailedException extends RuntimeException {
    public ResourceDeleteFailedException(String exception) {
        super(exception);
    }
}