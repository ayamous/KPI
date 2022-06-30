package ma.itroad.ram.kpi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String exception) {
        super(exception);
    }
}