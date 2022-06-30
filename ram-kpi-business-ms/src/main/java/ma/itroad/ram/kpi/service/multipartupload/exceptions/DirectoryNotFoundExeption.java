package ma.itroad.ram.kpi.service.multipartupload.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DirectoryNotFoundExeption extends RuntimeException {
    public DirectoryNotFoundExeption(String s) {
        super(s);
    }
}
